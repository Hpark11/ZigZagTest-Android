package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

/**
 * Created by croquiscom on 2017. 12. 20..
 */

public class Shop {
    public String name;
    public String url;
    public String[] style;
    public int[] age;
    public int score;

    public Shop(String name, String url, String style, int[] age, int score) {
        this.name = name;
        this.url = url;
        this.style = style.split(",");
        this.age = age;
        this.score = score;
    }
}
