package com.zigzagtest.croquiscom.zigzagtest.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.zigzagtest.croquiscom.zigzagtest.App;
import com.zigzagtest.croquiscom.zigzagtest.rankinglist.Shop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

final public class APIService {
    private static final String TAG = APIService.class.getSimpleName();
    private static final String path = "shopList.json";

    public static String getWeek(Context context) {
        String result = "";
        String data = request(context);
        try {
            JSONObject jsonObject = new JSONObject(data);
            result = jsonObject.getString("week");
        } catch (JSONException e) {
            if (App.DEBUG) Log.e(TAG, "Error when Parsing JSON in getWeek()");
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
        } catch (JSONException e) {
            if (App.DEBUG) Log.e(TAG, "Error when Parsing JSON in getShopList()");
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
        } catch (Exception e) {
            if (App.DEBUG) Log.e(TAG, "Error when reading data in getWeek()");
        }
        return null;
    }
}
