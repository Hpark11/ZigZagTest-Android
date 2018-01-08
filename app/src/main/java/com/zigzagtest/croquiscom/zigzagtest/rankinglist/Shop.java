package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class Shop {
    private static final String TAG = Shop.class.getSimpleName();

    private String mName;
    private String mUrl;
    private String[] mStyles;
    private int[] mAges = new int[7];
    private int mScore;

    public Shop(String name, String url, String styles, int[] ages, int score) {
        mName = name;
        mUrl = url;
        mStyles = styles.split(",");
        mAges = ages;
        mScore = score;
    }

    public Shop(JSONObject jsonObject) {
        try {
            mName = jsonObject.getString("n");
            mUrl = jsonObject.getString("u");
            mStyles = jsonObject.getString("S").split(",");

            JSONArray ageJsonArray = jsonObject.getJSONArray("A");
            for (int i = 0; i < mAges.length && i < ageJsonArray.length(); i++) {
                mAges[i] = ageJsonArray.getInt(i);
            }

            mScore = jsonObject.getInt("0");
        } catch (JSONException ignored) {}
    }

    public int getMatches(final Set<String> styles) {
        int count = 0;
        for(String s: styles) {
            if(styles.contains(s)) { count++; }
        }
        return count;
    }

    public String getFirstStyle() {
        return mStyles.length > 0 ? mStyles[0] : "";
    }

    public String getSecondStyle() {
        return mStyles.length > 1 ? mStyles[1] : "";
    }

    public String getName() {
        return mName;
    }

    public String getUrl() {
        return mUrl;
    }

    public int getScore() {
        return mScore;
    }

    public int[] getAges() {
        return mAges;
    }

    public String[] getStyles() {
        return mStyles;
    }

    public String getRepresentativeAgesData() {
        List<String> groups = new ArrayList<>();

        if(mAges[0] == 1) groups.add("10대");
        for (int i = 1; i <= 3; i++) {
            if (mAges[i] == 1) {
                groups.add("20대");
                break;
            }
        }
        for (int i = 4; i < mAges.length; i++) {
            if (mAges[i] == 1) {
                groups.add("30대");
                break;
            }
        }
        return TextUtils.join(" ", groups);
    }
}
