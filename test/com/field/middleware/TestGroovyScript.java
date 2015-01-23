package com.field.middleware;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springside.modules.test.selenium.Selenium2;

/**
 * Created by bqw on 14-9-3.
 */
public class TestGroovyScript {
    public static void  main(String[] args) throws Exception {
        Binding binding = new Binding();
        binding.setVariable("rootUrl","");
        binding.setVariable("loginUri","");

        GroovyScriptEngine engine = new GroovyScriptEngine("");

        //加载初始化脚本
        engine.run("/Users/bqw/work/code/localhost/middleware/test/init.groovy", binding);

        //vpn 模拟登录页面
        final Selenium2 selenium = new Selenium2(new HtmlUnitDriver(),binding.getVariable("rootUrl")+"");
        selenium.open(binding.getVariable("loginUri")+"");

        //加载模拟登录脚本
        binding.setProperty("explorer", selenium);
        engine.run("/Users/bqw/work/code/localhost/middleware/test/login.groovy", binding);

        //列表数据
        selenium.open("/seeyon/main.do?method=morePending4App&app=Coll");

        //详细信息主页面--暂不做处理
        selenium.open("/seeyon/collaboration.do?method=detail&from=Pending&affairId=-6206229059386915995&from=Pending");

        //详细信息表单数据--主要处理页面
        selenium.open("/seeyon/collaboration.do?method=getContent&summaryId=-6531677427463339876&affairId=-6206229059386915995&from=Pending&isQuote=&type=&lenPotent=");

        selenium.getDriver().quit();
    }
}
