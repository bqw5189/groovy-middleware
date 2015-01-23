package com.field.middleware;

import groovy.servlet.GroovyServlet;
import groovy.servlet.TemplateServlet;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springside.modules.utils.Encodes;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

/**
 * Created by bqw on 14-9-5.
 */
public class TestMiddleWare {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
   //     testSubmit();
    //    testListBackloged();
//        testList();
//        testDetail();
//        testBacklogedDetail();
        testBatchApproval();
        System.out.println((System.currentTimeMillis() - start) / 1000);

//        testDetail();
    }
    private static void testBatchApproval(){
        //seeyon/savills.do?method=listBatchApproval&condition=&textfield=&textfield1=&isQuote=&flag=&bizConfigId=&tempIds=&type=&menuId=
        //String removeUrl = Encodes.encodeHex("/seeyon/collaboration.do?method=listDone&condition=&textfield=&textfield1=&isQuote=&flag=&bizConfigId=&tempIds=&type=&menuId=".getBytes());
        String removeUrl = Encodes.encodeHex("/seeyon/savills.do?method=listBatchApproval&condition=&textfield=&textfield1=&isQuote=&flag=&bizConfigId=&tempIds=&type=&menuId=".getBytes());

        String uri = "optype=data&script=list-batch-approval&templet=list&vpn_user_name=OAMobileTest@savills.com.cn&vpn_password=0A@testcn&oa_username=shcashier1&oa_password=123456&remove_url=" + removeUrl;
        //String uri = "optype=data&script=list-backloged&templet=list&vpn_user_name=OAMobileTest@savills.com.cn&vpn_password=0A@testcn&oa_username=mobiletest01&oa_password=123456&remove_url=" + removeUrl;
        MiddleWare middleWare = new MiddleWare(uri);

        try {

            System.out.println("render data =" + middleWare.render());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void testListBackloged() {

        String removeUrl = Encodes.encodeHex("/seeyon/collaboration.do?method=listDone&condition=&textfield=&textfield1=&isQuote=&flag=&bizConfigId=&tempIds=&type=&menuId=".getBytes());

        String uri = "optype=data&script=list-backloged&templet=list&vpn_user_name=OAMobileTest@savills.com.cn&vpn_password=0A@testcn&oa_username=mobiletest01&oa_password=123456&remove_url=" + removeUrl;
        MiddleWare middleWare = new MiddleWare(uri);

        try {

            System.out.println("render data =" + middleWare.render());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testList() {
        String removeUrl = Encodes.encodeHex("/seeyon/main.do?method=morePending4App&app=Coll".getBytes());

        String uri = "optype=data&script=list&templet=list&vpn_user_name=OAMobileTest@savills.com.cn&vpn_password=0A@testcn&oa_username=mobiletest01&oa_password=123456&remove_url=" + removeUrl;
        MiddleWare middleWare = new MiddleWare(uri);

        try {

            System.out.println("render data =" + middleWare.render());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testBacklogedDetail() {
        String removeUrl = Encodes.encodeHex("/seeyon/collaboration.do?method=detail&from=Done&affairId=-4771771517053157175".getBytes());
        String uri = "optype=render&script=detail&templet=list&vpn_user_name=OAMobileTest@savills.com.cn&vpn_password=0A@testcn&oa_username=mobiletest01&oa_password=123456&remove_url=" + removeUrl;

        MiddleWare middleWare = new MiddleWare(uri);

        try {
            System.out.println("render data =" + middleWare.render());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testDetail() {
        String removeUrl = "2f736565796f6e2f636f6c6c61626f726174696f6e2e646f3f6d6574686f643d64657461696c2666726f6d3d50656e64696e672661666661697249643d333230323934363133333031303639363130382666726f6d3d50656e64696e67";//Encodes.encodeHex("/seeyon/collaboration.do?method=getContent&summaryId=-6531677427463339876&affairId=-6206229059386915995&from=Pending&isQuote=&type=&lenPotent=".getBytes());
        String uri = "optype=render&script=detail&templet=list&vpn_user_name=OAMobileTest@savills.com.cn&vpn_password=0A@testcn&oa_username=mobiletest01&oa_password=123456&remove_url=" + removeUrl;

        MiddleWare middleWare = new MiddleWare(uri);

        try {

            System.out.println("render data =" + middleWare.render().getClass());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testSubmit() {
        String removeUrl = Encodes.encodeHex("/seeyon/collaboration.do?method=detail&from=Pending&affairId=-1550465581719009393&from=Pending".getBytes());

        String uri = null;
        try {
            uri = "optype=submit&script=submit&templet=list&vpn_user_name=OAMobileTest@savills.com.cn&vpn_password=0A@testcn&oa_username=mobiletest01&oa_password=123456&remove_url=" + removeUrl+"&comment=" + URLEncoder.encode("批量驳回", Charset.defaultCharset().name()) + "&result=" + "3";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        MiddleWare middleWare = new MiddleWare(uri);

        try {

            System.out.println("render data =" + middleWare.render());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
