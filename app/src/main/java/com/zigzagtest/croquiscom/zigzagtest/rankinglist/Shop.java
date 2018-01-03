package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import com.zigzagtest.croquiscom.zigzagtest.service.FilterService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;


public final class Shop {
    private static final String TAG = Shop.class.getSimpleName();

    private String mName;
    private String mUrl;
    private String[] mStyles;
    private int[] mAges = new int[7];
    private int mScore;

    public Shop(String name, String url, String styles, int[] ages, int score) {
        this.mName = name;
        this.mUrl = url;
        this.mStyles = styles.split(",");
        this.mAges = ages;
        this.mScore = score;
    }

    public Shop(JSONObject jsonObject) {
        try {
            this.mName = jsonObject.getString("n");
            this.mUrl = jsonObject.getString("u");
            this.mStyles = jsonObject.getString("S").split(",");

            JSONArray ageJsonArray = jsonObject.getJSONArray("A");
            for (int i = 0; i < mAges.length && i < ageJsonArray.length(); i++) {
                mAges[i] = ageJsonArray.getInt(i);
            }

            this.mScore = jsonObject.getInt("0");
        } catch (JSONException ignored) {}
    }

    public int getNumberOfMatches() {
        Set<String> styles = FilterService.getStyleFilter();
        int count = 0;
        for (String s : mStyles) {
            count = styles.contains(s) ? count + 1 : count;
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
        StringBuilder res = new StringBuilder();
        String[] groups = new String[3];
        groups[0] = mAges[0] == 1 ? "10대" : "";

        for (int i = 1; i < mAges.length; i++) {
            if (mAges[i] == 1) {
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
