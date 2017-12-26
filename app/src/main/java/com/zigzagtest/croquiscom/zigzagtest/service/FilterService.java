package com.zigzagtest.croquiscom.zigzagtest.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import com.zigzagtest.croquiscom.zigzagtest.R;
import com.zigzagtest.croquiscom.zigzagtest.rankinglist.Shop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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

    public static final Map<String, Pair<Integer, Integer>> STYLES;
    static {
        STYLES = new HashMap<>();
        STYLES.put("페미닌", new Pair<>(R.color.colorGreenFern, R.color.colorDarkGray));
        STYLES.put("모던시크", new Pair<>(R.color.colorGreenMountain, R.color.colorDarkGray));
        STYLES.put("심플베이직", new Pair<>(R.color.colorBluePicton, R.color.colorBlueWhale));
        STYLES.put("러블리", new Pair<>(R.color.colorBlueMariner, R.color.colorWeekText));
        STYLES.put("유니크", new Pair<>(R.color.colorVioletWisteria, R.color.colorVioletGem));
        STYLES.put("미시스타일", new Pair<>(R.color.colorBlueMariner, R.color.colorBlueWhale));
        STYLES.put("캠퍼스룩", new Pair<>(R.color.colorYellowEnergy, R.color.colorRedWell));
        STYLES.put("빈티지", new Pair<>(R.color.colorOrangeNeon, R.color.colorOrangeSun));
        STYLES.put("섹시글램", new Pair<>(R.color.colorRedTerra, R.color.colorRedWell));
        STYLES.put("스쿨룩", new Pair<>(R.color.colorYellowTurbo, R.color.colorRedValencia));
        STYLES.put("로맨틱", new Pair<>(R.color.colorGrayAlmond, R.color.colorDarkGray));
        STYLES.put("오피스룩", new Pair<>(R.color.colorGraySmoke, R.color.colorLightGray));
        STYLES.put("럭셔리", new Pair<>(R.color.colorGreenFern, R.color.colorBlueDenim));
        STYLES.put("헐리웃스타일", new Pair<>(R.color.colorBluePicton, R.color.colorVioletGem));
    }

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

//    private static FilterService _instance;
//
//    public static FilterService getInstance(Context context) {
//        if (_instance == null) {
//            _instance = new FilterService(context);
//        }
//        return _instance;
//    }

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

    public ArrayList<Shop> setStyleMatchesToEachItem(ArrayList<Shop> shops) {
        Set<String> conditionByStyles = getFilterByStyles();
        for (int i = 0; i < shops.size(); i++) {
            int matchCount = 0;
            Shop shop = shops.get(i);
            for (String s: shop.style) {
                if(conditionByStyles.contains(s)) {
                    matchCount++;
                }
            }
            shop.setNumberOfMatches(matchCount);
            shops.set(i, shop);
        }
        return shops;
    }

    private ArrayList<Shop> filteredByAges(ArrayList<Shop> shops, final int[] conditions) {
        ArrayList<Shop> result = new ArrayList<>();
        for (Shop shop: shops) {
            for(int i = 0; i < shop.age.length; i++) {
                if(shop.age[i] == conditions[i] && shop.age[i] == 1) {
                    result.add(shop);
                    break;
                }
            }
        }
        return result;
    }

    private ArrayList<Shop> filteredByStyles(ArrayList<Shop> shops, final Set<String> conditions) {
        ArrayList<Shop> result = new ArrayList<>();
        for (Shop shop: shops) {
            for (String style: shop.style) {
                if(conditions.contains(style)) {
                    result.add(shop);
                    break;
                }
            }
        }
        return result;
    }

    // naming
    private boolean containsAgeCondition(final int[] with) {
        for (int element: with) { if(element == 1) return true; }
        return false;
    }

    public ArrayList<Shop> getFilteredShops(ArrayList<Shop> shops) {
        final int[] conditionByAges = getFilterByAges();
        final Set<String> conditionByStyles = getFilterByStyles();
        ArrayList<Shop> result;

        if(!containsAgeCondition(conditionByAges) && conditionByStyles.size() == 0) {
            return shops;
        } else if (!containsAgeCondition(conditionByAges)) {
            result = filteredByStyles(shops, conditionByStyles);
        } else if (conditionByStyles.size() == 0) {
            result = filteredByAges(shops, conditionByAges);
        } else {
            result = filteredByStyles(filteredByAges(shops, conditionByAges), conditionByStyles);
        }

        Collections.sort(result, new Comparator<Shop>() {
            @Override
            public int compare(Shop lhs, Shop rhs) {
                if (lhs.getNumberOfMatches() == rhs.getNumberOfMatches()) {
                    return lhs.score > rhs.score ? -1 : 1;
                } else {
                    return lhs.getNumberOfMatches() > rhs.getNumberOfMatches() ? -1 : 1;
                }
            }
        });

        return result;
    }

    public static String getRepresentativeAgesData(final int[] ages) {
        if (ages.length != AGES.length) {
            return null;
        }

        String result = "";
        String holder = "";

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

        return result;
    }
}
