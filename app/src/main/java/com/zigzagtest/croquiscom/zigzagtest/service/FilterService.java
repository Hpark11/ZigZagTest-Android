package com.zigzagtest.croquiscom.zigzagtest.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by croquiscom on 2017. 12. 20..
 */

final public class FilterService {
    private static final String TAG = FilterService.class.getSimpleName();

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String[] AGES = {
            "10대",
            "20대 초반",
            "20대 중반",
            "20대 후반",
            "30대 초반",
            "30대 중반",
            "30대 후반"
    };

    public FilterService(Context context) {
        sharedPreferences = context.getSharedPreferences("filter", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Filter
    public int[] getFilterByAges() {
        int[] result = new int[FilterService.AGES.length];
        int ageVal = sharedPreferences.getInt("ages", 0);
        for(int i = AGES.length - 1; i >= 0; i--) {
            result[i] = ageVal & 1;
            ageVal >>= 1;
        }
        return result;
    }

    public Set<String> getFilterByStyles() {
        return sharedPreferences.getStringSet("styles", new TreeSet<String>());
    }

    public void setFilter(final int[] ages, final Set<String> styles) {
        int ageVal = 0;
        for(int i = 0; i < ages.length; i++) {
            ageVal <<= 1;
            ageVal += ages[i];
        }

        editor.putInt("ages", ageVal);
        editor.putStringSet("styles", styles);
        editor.commit();
    }

    static public String getRepresentativeAgesData(final int[] ages) {
        String result = "";
        String holder = "";

        if(ages.length == AGES.length) {
            for(int i = 0; i < ages.length; i++) {
                String ageGroup = AGES[i].split(" ")[0];

                if(ages[i] == 1) {
                    if (!holder.equals(ageGroup)) {
                        if (result.length() != 0) {
                            result += " ";
                        }
                        result += ageGroup;
                        holder = ageGroup;
                    }
                }
            }
        } else {
            Log.e(TAG, "Different Ages Data ");
        }

        return result;
    }
}
