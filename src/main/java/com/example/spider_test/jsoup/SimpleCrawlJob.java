package com.example.spider_test.jsoup;

import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 最简单的一个爬虫任务
 * <p>
 * Created by yihui on 2017/6/27.
 */
@Getter
@Setter
public class SimpleCrawlJob   {

    /**
     * 配置项信息
     */
    private CrawlMeta crawlMeta;


    /**
     * 存储爬取的结果
     */
    private CrawlResult crawlResult;


    /**
     * 执行抓取网页
     */
    public    void doFetchPage(long startTime,String u) throws Exception {

        URL url = new URL(u);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader in = null;

        StringBuilder result = new StringBuilder();

        try {
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            connection.setRequestProperty("contentType", "utf-8");
            //connection.setRequestProperty("Accept-Charset", "GBK");
            // 建立实际的连接
            connection.connect();


//                      Map<String, List<String>> map = connection.getHeaderFields();
//            //遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "GBK"));
            String line;
            while ((line = in.readLine()) != null) {
                line = new String(line.getBytes());
                result.append(line);
            }
        } finally {        // 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }


        doParse(startTime,result.toString());
    }

    private void doParse(long startTime,String html) throws Exception {
        Document doc = Jsoup.parse(html);

        Map<String, String> m = new HashMap<>();
        Elements elements = doc.select("div[class=mPage]");
        String nextLinkHref = null;
        String preLinkHref = null;
        for (Element element : elements.select("a")) {
            if (element.text().equals("下一节"))
                nextLinkHref = "https://ffxs.me" + element.attr("href");
            if (element.text().equals("上一节"))
                preLinkHref = "https://ffxs.me" + element.attr("href");
        }
        for (Element element : doc.select("h1")) {
            m.put("title", element.text());
        }
        m.put("nextLinkHref", nextLinkHref);
        for (Element element : doc.select("div[class=content]")) {
            m.put("content", element.html().replace("<br>", ""));
        }
        System.out.println("正在下载-->" + m.get("title"));
        FileWriter fw = null;
        BufferedWriter bw=null;
        try {
            fw = new FileWriter("C:\\Users\\tanlx\\Desktop\\神鹰帝国.txt", true);
              bw = new BufferedWriter(fw);
        //    bw.append(m.get("title").toString()+"\r\n");
            bw.append(m.get("content")+"\r\n ");// 往已有的文件上添加字符串
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            bw.close();
            fw.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("已耗时约" + ((endTime - startTime) / 1000) + "秒！");
        // list.add(doc.select("a[href]").text());
    //    SimpleCrawlJob.write(m);
//        this.crawlResult = new CrawlResult();
//        this.crawlResult.setHtmlDoc(doc);
//        this.crawlResult.setUrl(crawlMeta.getUrl());
        String next =m.get("nextLinkHref");
        while (next != null) {
             doFetchPage( startTime,next);

        }

    }


//

    public static void write(Map map) {
        System.out.println("正在下载-->" + map.get("title"));
        FileWriter fw = null;
        try {
            fw = new FileWriter("C:\\Users\\tanlx\\Desktop\\a.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(map.get("title").toString()+"\r\n");
            bw.write(map.get("content")+"\r\n ");// 往已有的文件上添加字符串
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws Exception {
        String url = "https://ffxs.me/book/2-4753-1213.html";
        Set<String> selectRule = new TreeSet<>();
        selectRule.add("li"); // 博客标题
        selectRule.add("a[class=title]"); // 博客标题
        selectRule.add("div[class=content]"); // 博客标题
        CrawlMeta crawlMeta = new CrawlMeta();
        crawlMeta.setUrl(url); // 设置爬取的网址
        crawlMeta.setSelectorRules(selectRule); // 设置抓去的内容
        SimpleCrawlJob s=new SimpleCrawlJob();


     //   SimpleCrawlJob job = new SimpleCrawlJob();
     //   job.setCrawlMeta(crawlMeta);

       // CrawlResult result = job.getCrawlResult();
        long startTime, endTime;
        System.out.println("【爬虫开始】请耐心等待一大波数据到你碗里来...");
        startTime = System.currentTimeMillis();
        s.doFetchPage(startTime,url);
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】耗时约" + ((endTime - startTime) / 1000) + "秒！");
    }
}