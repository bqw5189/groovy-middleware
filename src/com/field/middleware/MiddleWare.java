package com.field.middleware;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.test.selenium.Selenium2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by bqw on 14-9-3.
 */
public class MiddleWare {
//    private static final String SCRIPT_ROOT = "D:\\dswork\\fieldproject\\project\\SAVILLS\\middleware\\src\\com\\field\\script";
    private static final String SCRIPT_ROOT = "E:\\work\\web\\webapp\\WEB-INF\\classes";

    private static final Logger logger = LoggerFactory.getLogger(MiddleWare.class);

    /**
     * app 请求的参数信息
     * <p/>
     * //optype=render&script=list&templet=list&otherparams=...
     */
    private String appRequestParams;

    /**
     * 将请求的参数放入map中
     */
    private List<NameValuePair> requestParams = new ArrayList<NameValuePair>();

    /**
     * 脚本变了绑定
     */
    private Binding binding = new Binding();

    /**
     * 脚本加载器
     */
    private GroovyScriptEngine engine;

    public static final Cache<String, Selenium2> CACHE_DRIVER= CacheBuilder.newBuilder().expireAfterAccess(15, TimeUnit.SECONDS).maximumSize(50).removalListener(new RemovalListener<String, Selenium2>() {
        @Override
        public void onRemoval(RemovalNotification<String, Selenium2> stringSelenium2RemovalNotification) {
            stringSelenium2RemovalNotification.getValue().getDriver().quit();
        }
    }).build();


    public MiddleWare(String appRequestParams) {
        this.appRequestParams = appRequestParams;

        requestParams = URLEncodedUtils.parse(appRequestParams, Charset.defaultCharset());

        System.out.println(requestParams);

        try {
            engine = new GroovyScriptEngine(SCRIPT_ROOT);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("{} -> script root error!", SCRIPT_ROOT);
        }

        initBindVar();
    }

    private void initBindVar() {
        for (NameValuePair nv : requestParams) {
            System.out.println(nv.getName() + "=" + nv.getValue());
            binding.setProperty(nv.getName(), nv.getValue());
        }
    }

    public Object render() throws Exception {
        //加载初始化脚本
        engine.run("init.groovy", binding);

        String optype = binding.getVariable("optype") + "";

        Selenium2 selenium = null;

        String key = binding.getProperty("oa_username")+"";


            selenium = CACHE_DRIVER.get(key,new Callable<Selenium2>() {
                @Override
                public Selenium2 call() throws Exception {

                    logger.debug("htmlunit call");

                    MiddleHtmlUnitDriver middleHtmlUnitDriver = new MiddleHtmlUnitDriver();
                    middleHtmlUnitDriver.getWebClient().getOptions().setCssEnabled(false);
                    middleHtmlUnitDriver.getWebClient().getOptions().setJavaScriptEnabled(false);

                    Selenium2 selenium = new Selenium2(middleHtmlUnitDriver, binding.getVariable("rootUrl") + "");

                    selenium.open(binding.getVariable("loginUri") + "");

                    long start = System.currentTimeMillis();

                    binding.setProperty("explorer", selenium);

                    engine.run("login.groovy", binding);

                    logger.debug("run login used times(s):{}", (System.currentTimeMillis()-start)/1000);

                    return selenium;
                }
            });




        //加载模拟登录脚本
        binding.setProperty("explorer", selenium);

        long start = System.currentTimeMillis();

        engine.run(binding.getProperty("script") + ".groovy", binding);

        logger.debug("run "+binding.getProperty("script")+" used times(s):{}", (System.currentTimeMillis()-start)/1000);

        return binding.getProperty("render_data");
    }
}
