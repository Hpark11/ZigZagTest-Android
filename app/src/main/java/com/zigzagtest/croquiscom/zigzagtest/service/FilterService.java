package com.zigzagtest.croquiscom.zigzagtest.service;

import android.util.Log;

/**
 * Created by croquiscom on 2017. 12. 20..
 */

final public class FilterService {
    private static final String TAG = FilterService.class.getSimpleName();

    public static final String[] AGES = {
        "10대",
        "20대 초반",
        "20대 중반",
        "20대 후반",
        "30대 초반",
        "30대 중반",
        "30대 후반"
    };

    public static String getRepresentativeAgesData(final int[] ages) {
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
