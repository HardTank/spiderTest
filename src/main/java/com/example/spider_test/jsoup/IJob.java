package com.example.spider_test.jsoup;

/**
 * @author tanlx
 * @description 爬取接口
 * @date 2019/10/17 10:39
 */
public interface IJob extends Runnable {

    /**
     * 在job执行之前回调的方法
     */
    void beforeRun();


    /**
     * 在job执行完毕之后回调的方法
     */
    void afterRun();
}