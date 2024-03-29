package com.example.spider_test.jsoup;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * @author tanlx
 * @description 存储结构
 * @date 2019/10/17 10:36
 */
@ToString
public class CrawlMeta {

    /**
     * 待爬去的网址
     */
    @Getter
    @Setter
    private String url;


    /**
     * 获取指定内容的规则, 因为一个网页中，你可能获取多个不同的内容， 所以放在集合中
     */
    @Setter
    private Set<String> selectorRules;


    // 这么做的目的就是为了防止NPE, 也就是说支持不指定选择规则
    public Set<String> getSelectorRules() {
        return selectorRules != null ? selectorRules : new HashSet<>();
    }

}
