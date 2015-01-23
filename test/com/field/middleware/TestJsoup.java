package com.field.middleware;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bqw on 15/1/20.
 */
public class TestJsoup {
    public static void  main(String[] args) throws IOException {
        String url = "https://vpn.savills.com.cn/wa/auth";
        Map<String, String> param = new HashMap<String, String>();
        Map<String, String> cookies = new HashMap<String, String>();

        param.put("Savills China Users", "authmech");
        param.put("login", "do");
        param.put("username", "OAMobileTest@savills.com.cn");
        param.put("password", "0A@testcn");

        Connection.Response response = Jsoup.connect(url).data(param).timeout(5000).execute().method(Connection.Method.POST);

        cookies.putAll(response.cookies());

        System.out.println("vpn 登录成功: cookies:" + param + " body:" + response.body());
        System.out.println("==========================================================");

        response = Jsoup.connect("https://vpn.savills.com.cn/http/China%20OA%20Offline/").cookies(cookies).userAgent("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/7.0; Touch; .NET4.0E; .NET4.0C; Tablet PC 2.0; InfoPath.3; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729)").execute().method(Connection.Method.GET);
        System.out.println("访问OA系统登录页面: cookies:" + param + " body:" + response.body());
        System.out.println("==========================================================");


        cookies.putAll(response.cookies());

        param.put("UserAgentFrom", "pc");
        param.put("login.timezone", "");
        param.put("dogSessionId", "");
        param.put("authorization", "");
        param.put("login.username", "mobiletest01");
        param.put("login.password", "123456");

        response =  Jsoup.connect("https://vpn.savills.com.cn/seeyon/login/proxy").cookies(cookies).data(param).userAgent("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/7.0; Touch; .NET4.0E; .NET4.0C; Tablet PC 2.0; InfoPath.3; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729)").execute().method(Connection.Method.POST);


        System.out.println("OA 登录成功: cookies:" + param + " body:" + response.body());
        System.out.println("==========================================================");
        System.out.println("==========================================================" + response.url());

        cookies.putAll(response.cookies());

        Document document =  Jsoup.connect("https://vpn.savills.com.cn/seeyon/main.do?method=morePending4App&app=Coll").cookies(cookies).userAgent("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/7.0; Touch; .NET4.0E; .NET4.0C; Tablet PC 2.0; InfoPath.3; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729)").get();

        Elements es = document.select("span a");

        es.text();

        System.out.println("访问系统主页面: cookies:" + param + " body:" + es.toString());
        System.out.println("==========================================================");


    }
}
