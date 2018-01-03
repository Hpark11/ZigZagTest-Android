package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import android.util.Log;

import com.zigzagtest.croquiscom.zigzagtest.App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;


public final class Shop {
    private static final String TAG = Shop.class.getSimpleName();

    private String name;
    private String url;
    private String[] styles;
    private int[] ages = new int[7];
    private int score;
    private int matches = 0;

    public Shop(String name, String url, String styles, int[] age, int score) {
        this.name = name;
        this.url = url;
        this.styles = styles.split(",");
        this.ages = age;
        this.score = score;
    }

    public Shop(JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("n");
            this.url = jsonObject.getString("u");
            this.styles = jsonObject.getString("S").split(",");

            JSONArray ageJsonArray = jsonObject.getJSONArray("A");
            for (int i = 0; i < ages.length && i < ageJsonArray.length(); i++) {
                ages[i] = ageJsonArray.getInt(i);
            }

            this.score = jsonObject.getInt("0");
        } catch (JSONException e) {
            if (App.DEBUG) Log.e(TAG, "Error when Parsing JSON in constructor()");
        }
    }

    public void setNumberOfMatches(int matches) {
        this.matches = matches;
    }

    public int getNumberOfMatches() {
        return matches;
    }

    public String getFirstStyle() {
        return styles.length > 0 ? styles[0] : "";
    }

    public String getSecondStyle() {
        return styles.length > 1 ? styles[1] : "";
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getScore() {
        return score;
    }

    public int[] getAges() {
        return ages;
    }

    public String[] getStyles() {
        return styles;
    }

    public String getRepresentativeAgesData() {
        StringBuilder res = new StringBuilder();
        String[] groups = new String[3];
        groups[0] = ages[0] == 1 ? "10대" : "";

        for (int i = 1; i < ages.length; i++) {
            if (ages[i] == 1) {
                if (i <= 3) {
                    groups[1] = "20대";
                } else {
                    groups[2] = "30대";
                }
            }
        }
        for (String s : groups) {
            if (s != null) {
                if (res.length() != 0) {
                    res.append(" ");
                }
                res.append(s);
            }
        }
        return res.toString();
    }
}
