package com.field.middleware;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springside.modules.test.selenium.Selenium2;
import org.springside.modules.utils.Encodes;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by bqw on 14-9-5.
 */
public class TestCache {
    public static void main(String[] args) throws ExecutionException {

        Cache<String, Selenium2> CACHE_DRIVER= CacheBuilder.newBuilder().expireAfterAccess(60*10, TimeUnit.SECONDS).maximumSize(10).build();

        Selenium2 selenium2 = CACHE_DRIVER.get("abc", new Callable<Selenium2>() {
            @Override
            public Selenium2 call() throws Exception {


                MiddleHtmlUnitDriver middleHtmlUnitDriver = new MiddleHtmlUnitDriver();
                middleHtmlUnitDriver.getWebClient().getOptions().setCssEnabled(false);
                middleHtmlUnitDriver.getWebClient().getOptions().setJavaScriptEnabled(false);

                Selenium2 selenium = new Selenium2(middleHtmlUnitDriver, "www.baidu.com");

                System.out.println(selenium);

                return selenium;
            }
        });


        System.out.println(selenium2);





    }
}

class CacheObje{

}