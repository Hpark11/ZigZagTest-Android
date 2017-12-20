package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by croquiscom on 2017. 12. 20..
 */

public class Shop {
    public String name;
    public String url;
    public String[] style;
    public int[] age = new int[7];
    public int score;

    public Shop(String name, String url, String style, int[] age, int score) {
        this.name = name;
        this.url = url;
        this.style = style.split(",");
        this.age = age;
        this.score = score;
    }

    public Shop(JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("n");
            this.url = jsonObject.getString("u");
            this.style = jsonObject.getString("S").split(",");

            JSONArray ageJsonArray = jsonObject.getJSONArray("A");
            for(int i = 0; i < age.length && i < ageJsonArray.length(); i++) {
                age[i] = ageJsonArray.getInt(i);
            }

            this.score = jsonObject.getInt("0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
