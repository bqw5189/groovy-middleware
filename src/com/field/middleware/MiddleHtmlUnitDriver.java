package com.field.middleware;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiddleHtmlUnitDriver extends HtmlUnitDriver {
    /**
     * 解析出url请求的路径，包括页面
     *
     * @param strURL url地址
     * @return url路径
     */
    public static String urlPage(String strURL) {
        String strPage = null;
        String[] arrSplit = null;
        strURL = strURL.trim();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 0) {
            if (arrSplit.length > 1) {
                if (arrSplit[0] != null) {
                    strPage = arrSplit[0];
                }
            }
        }
        return strPage;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String truncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static List<NameValuePair> URLRequest(String URL) {
        List<NameValuePair> listRequest = new ArrayList<NameValuePair>();
        String[] arrSplit = null;
        String strUrlParam = truncateUrlPage(URL);
        if (strUrlParam == null) {
            return listRequest;
        }
        // 每个键值为一组
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            NameValuePair nameValue = null;
            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                try {
                    nameValue = new NameValuePair(arrSplitEqual[0],
                            new String(arrSplitEqual[1].getBytes(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                if (arrSplitEqual[0] != "") {
                    // 只有参数没有值，不加入
                    nameValue = new NameValuePair(arrSplitEqual[0], "");
                }
            }

            listRequest.add(nameValue);
        }
        return listRequest;
    }

    /**
     * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> URLRequestParam(String URL) {
        Map<String, String> result = new HashMap<String, String>();
        List<NameValuePair> listRequest = URLRequest(URL);
        for (NameValuePair nv : listRequest) {
            result.put(nv.getName(), nv.getValue());
        }
        return result;
    }

    public WebClient getWebClient() {
        return super.getWebClient();
    }

    public void post(String url) {
        WebClient client = this.getWebClient();
        try {
            List<NameValuePair> params = URLRequest(url);
            String urlPage = urlPage(url);

            WebRequest wr = new WebRequest(new URL(urlPage));
            wr.setHttpMethod(HttpMethod.POST);
            wr.setEncodingType(FormEncodingType.MULTIPART);
            wr.setRequestParameters(params);
            wr.setCharset("utf-8");
            Page page = client.getPage(wr);
            System.out.println(page.getWebResponse().getContentAsString());
        } catch (FailingHttpStatusCodeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void post(String url, FormEncodingType type) {
        WebClient client = this.getWebClient();
        try {
            List<NameValuePair> params = URLRequest(url);
            String urlPage = urlPage(url);

            WebRequest wr = new WebRequest(new URL(urlPage));
            wr.setHttpMethod(HttpMethod.POST);
            wr.setEncodingType(type);
            wr.setRequestParameters(params);
            wr.setCharset("utf-8");
            Page page = client.getPage(wr);
            System.out.println(page.getWebResponse().getContentAsString());
        } catch (FailingHttpStatusCodeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
