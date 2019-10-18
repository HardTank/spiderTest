package com.example.spider_test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author tanlx
 * @description 测试
 * @date 2019/10/16 15:26
 */

    public class Test implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来

        page.putField("title", page.getHtml().xpath("//h1/text()").toString());
        page.putField("content", page.getHtml().xpath("//div[@class='content']/br/text()").toString());
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        }


        // 部分三：从页面发现后续的url地址来抓取

        File filepath = new File("C:\\Users\\tanlx\\Desktop\\a.txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter("C:\\Users\\tanlx\\Desktop\\a.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(page.getResultItems().get("content").toString()+"\r\n");
            bw.write("abc\r\n ");// 往已有的文件上添加字符串
            bw.write("def\r\n ");
            bw.write("hijk ");
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }




        System.out.println(page.getResultItems().toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.out.println("【爬虫开始】请耐心等待一大波数据到你碗里来...");
            Spider.create(new Test()).addUrl("https://ffxs.me/book/2-4753-3.html").addPipeline(new ConsolePipeline()).thread(5).run();
        System.out.println("【爬虫结束】共抓取" );
        }

}
