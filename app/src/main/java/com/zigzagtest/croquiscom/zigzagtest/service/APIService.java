package com.zigzagtest.croquiscom.zigzagtest.service;

import android.content.Context;
import com.zigzagtest.croquiscom.zigzagtest.rankinglist.Shop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

final public class APIService {
    private static final String path = "shopList.json";

    public static String getWeek(Context context) {
        String result = "";
        String data = request(context);
        try {
            JSONObject jsonObject = new JSONObject(data);
            result = jsonObject.getString("week");
        } catch (JSONException ignored) {
        }
        return result;
    }

    public static ArrayList<Shop> getShopList(Context context) {
        ArrayList<Shop> shopArrayList = new ArrayList<>();
        String data = request(context);
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                Shop shop = new Shop(item);
                shopArrayList.add(shop);
            }
        } catch (JSONException ignored) {
        }
        return shopArrayList;
    }

    private static String request(Context context) {
        try {
            InputStream is = context.getAssets().open(path);
            byte buffer[] = new byte[is.available()];
            final int result = is.read(buffer);
            is.close();
            if (result != -1) {
                return new String(buffer);
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
