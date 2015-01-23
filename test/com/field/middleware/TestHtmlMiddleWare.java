package com.field.middleware;

import org.springside.modules.mapper.JsonMapper;
import org.springside.modules.utils.Encodes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bqw on 14-10-11.
 */
public class TestHtmlMiddleWare {
    public static void main(String[] args){
//        System.out.println(((char)-62) + "" + ((char)-96) );
        Map<String, Object> requestParams = new HashMap<String, Object>();

        requestParams.put("nodeId", "3");
        requestParams.put("pageNumber", 1);
        requestParams.put("pageSize", 5);
        requestParams.put("method", "count");

        requestParams.put("username", "mobiletest01");
        requestParams.put("password", "123456");
        requestParams.put("userId", "1");
        requestParams.put("script", "1");
        requestParams.put("scriptId", "1");


        HtmlForJsoupMiddleWare htmlMiddleWare = HtmlForJsoupMiddleWare.getInstance("/Users/bqw/work/code/git-code/field.dsgrp.cn/field-project/web/webapp/middleware");

        try {
            ScriptRunResult scriptRunResult = htmlMiddleWare.render("backlog-template-for-jsoup.groovy",requestParams);

            System.out.println("console:" + scriptRunResult.getConsole());

            System.out.println(scriptRunResult.getExceptionStackTrace());

            System.out.println("result =" + new JsonMapper().toJson(scriptRunResult.getRunResult()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
