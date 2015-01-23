package com.field.middleware;

import com.daosheng.field.service.middleware.ScriptService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import groovy.lang.Binding;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springside.modules.test.selenium.Selenium2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by bqw on 14-9-3.
 */
public class HtmlMiddleWare {
    public static final Cache<String, Selenium2> CACHE_DRIVER = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.SECONDS).maximumSize(50).removalListener(new RemovalListener<String, Selenium2>() {
        @Override
        public void onRemoval(RemovalNotification<String, Selenium2> stringSelenium2RemovalNotification) {
            stringSelenium2RemovalNotification.getValue().getDriver().quit();
        }
    }).build();
    private static final Logger logger = LoggerFactory.getLogger(HtmlMiddleWare.class);
    /**
     * 脚本加载器
     */
    private static GroovyScriptEngine SCRIPT_ENGINE;

    private static HtmlMiddleWare MIDDLEWARE;

    private static ApplicationContext APPLICATION_CONTEXT;

    private HtmlMiddleWare(String scriptPath) {
    }

    public static HtmlMiddleWare getInstance(String scriptPath) {
        return  getInstance(scriptPath, null);
    }

    public static HtmlMiddleWare getInstance(String scriptPath,ApplicationContext webApplicationContext) {
        if (null == MIDDLEWARE) {
            MIDDLEWARE = new HtmlMiddleWare(scriptPath);
        }

        try {
            SCRIPT_ENGINE = new GroovyScriptEngine(scriptPath);
        } catch (IOException e) {
            logger.error("{} -> script root error!", scriptPath);
        }

        if (null != webApplicationContext){
            APPLICATION_CONTEXT = webApplicationContext;
        }

        return MIDDLEWARE;
    }

    public ScriptRunResult render(String scriptName, Map<String, Object> requestParams) {

        final Binding binding = new Binding();

        ScriptRunResult scriptRunResult = new ScriptRunResult();

        binding.setProperty("out", new PrintWriter(scriptRunResult.getConsole()));


        try {

            final Script script = SCRIPT_ENGINE.createScript(scriptName, binding);

            binding.setProperty("logger", LoggerFactory.getLogger("com.field.middleware.script." + scriptName));

            //加载初始化脚本
            script.invokeMethod("init", null);

            if (null != requestParams) {
                for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
                    binding.setProperty(entry.getKey(), entry.getValue());
                }
            }

            if (null != APPLICATION_CONTEXT){
                List importService = (List)binding.getProperty("import_service");

                for (Object serviceName: importService){
                    binding.setProperty(scriptName, APPLICATION_CONTEXT.getBean(serviceName+""));
                }
            }

            Selenium2 selenium = null;

            //缓存key
            String key = binding.getProperty(binding.getProperty("explorerCacheKey") + "") + "";

            logger.debug("key:{}, binding:{}, cacheBinding:{}", key, binding.getVariables());

            selenium = CACHE_DRIVER.get(key, new Callable<Selenium2>() {
                @Override
                public Selenium2 call() throws Exception {

                    logger.debug("htmlunit call");

                    MiddleHtmlUnitDriver middleHtmlUnitDriver = new MiddleHtmlUnitDriver();
                    middleHtmlUnitDriver.getWebClient().getOptions().setCssEnabled(false);
                    middleHtmlUnitDriver.getWebClient().getOptions().setJavaScriptEnabled(false);

                    Selenium2 selenium = new Selenium2(middleHtmlUnitDriver, binding.getVariable("rootUrl") + "");

                    selenium.open(binding.getVariable("loginUri") + "");

                    long start = System.currentTimeMillis();

                    //加载模拟登录脚本
                    binding.setProperty("explorer", selenium);

                    script.invokeMethod("login", null);

                    logger.debug("run login used times(s):{}", (System.currentTimeMillis() - start) / 1000);

                    return selenium;
                }
            });

            //加载模拟登录脚本
            binding.setProperty("explorer", selenium);

            long start = System.currentTimeMillis();

            String method = null;

            if (null == requestParams.get("method")) {
                Map idToListMethod = (Map) binding.getProperty("idToListMethod");

                method = idToListMethod.get(requestParams.get("nodeId")) + "";
            } else {
                method = requestParams.get("method") + "";
            }

            logger.debug("run " + method + " used times(s):{}", (System.currentTimeMillis() - start) / 1000);

            scriptRunResult.setRunResult(script.invokeMethod(method, null));

        } catch (Exception e) {
            for (String message : ExceptionUtils.getRootCauseStackTrace(e)) {
                if (message.indexOf("com.field.middleware.HtmlMiddleWare") > -1) {
                    break;
                }
                scriptRunResult.getExceptionStackTrace().append(message + "\n");
            }
        }

        if (null != APPLICATION_CONTEXT){
            ScriptService scriptService = (ScriptService)APPLICATION_CONTEXT.getBean("scriptService");
            Long scriptId = Long.parseLong(requestParams.get("scriptId") + "");
            if (null != scriptService && null != scriptId){
                com.daosheng.field.entity.middleware.Script script = scriptService.getById(scriptId);

                script.setError(scriptRunResult.getExceptionStackTrace().toString());
                script.setOutput(scriptRunResult.getConsole().toString());
                scriptService.save(script);
            }
        }


        return scriptRunResult;
    }
}
