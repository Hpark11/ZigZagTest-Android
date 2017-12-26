package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import android.support.annotation.NonNull;

import com.zigzagtest.croquiscom.zigzagtest.service.FilterService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;

/**
 * Created by croquiscom on 2017. 12. 20..
 */

public final class Shop{
    public String name;
    public String url;
    public String[] style;
    public int[] age = new int[7];
    public int score;
    private int matches = 0;

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

    public void setNumberOfMatches(int matches) {
        this.matches = matches;
    }

    public int getNumberOfMatches() {
        return matches;
    }

    public String getRepresentativeAgesData() {
        if (age.length != FilterService.AGES.length) {
            return null;
        }

        String result = "";
        String holder = "";

        for(int i = 0; i < age.length; i++) {
            String ageGroup = FilterService.AGES[i].split(" ")[0];

            if(age[i] == 1) {
                if (!holder.equals(ageGroup)) {
                    if (result.length() != 0) {
                        result += " ";
                    }
                    result += ageGroup;
                    holder = ageGroup;
                }
            }
        }

        return result;
    }

    public String getFirstStyle() {
        return style.length > 0 ? style[0] : "";
    }

    public String getSecondStyle() {
        return style.length > 1 ? style[1] : "";
    }

}
