package com.zigzagtest.croquiscom.zigzagtest.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zigzagtest.croquiscom.zigzagtest.rankinglist.Shop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by croquiscom on 2017. 12. 20..
 */
final public class APIService {
    private static final String TAG = APIService.class.getSimpleName();
    private static final String path = "shopList.json";

    public static Bitmap getBitmapFromUrl(String stringUrl) {
        Bitmap bitmap = null;
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(stringUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) connection.disconnect();
        }
        return bitmap;
    }

    public static String getWeek(Context context) {
        String result = "";
        String data = request(context);
        try {
            JSONObject jsonObject = new JSONObject(data);
            result = jsonObject.getString("week");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<Shop> getShopList(Context context) {
        ArrayList<Shop> shopArrayList = new ArrayList<>();
        String data = request(context);
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("list");

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                Shop shop = new Shop(item);
                shopArrayList.add(shop);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return shopArrayList;
    }

    private static String request(Context context) {
        byte buffer[] = new byte[0];
        try {
            InputStream is = context.getAssets().open(path);
            buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(buffer);
    }
}
