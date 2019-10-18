package com.example.spider_test;

import lombok.Data;

/**
 * @author tanlx
 * @description 实体类
 * @date 2019/10/16 15:53
 */
@Data
public class CsdnBlog {
    private int id;// 编号

    private String title;// 标题

    private String date;// 日期

    private String tags;// 标签

    private String category;// 分类

    private int view;// 阅读人数

    private int comments;// 评论人数

    private int copyright;// 是否原创

}
