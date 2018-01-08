package com.zigzagtest.croquiscom.zigzagtest.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.zigzagtest.croquiscom.zigzagtest.R;
import com.zigzagtest.croquiscom.zigzagtest.rankinglist.Shop;
import com.zigzagtest.croquiscom.zigzagtest.rankinglist.ShopStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final public class FilterService {
    public static final String[] AGES = {
            "10대",
            "20대 초반",
            "20대 중반",
            "20대 후반",
            "30대 초반",
            "30대 중반",
            "30대 후반"
    };

    public static final Map<String, ShopStyle> STYLES;

    static {
        STYLES = new HashMap<>();
        STYLES.put("페미닌", new ShopStyle(R.color.green_fern, R.color.dark_gray));
        STYLES.put("모던시크", new ShopStyle(R.color.green_mountain, R.color.dark_gray));
        STYLES.put("심플베이직", new ShopStyle(R.color.blue_picton, R.color.blue_whale));
        STYLES.put("러블리", new ShopStyle(R.color.blue_mariner, R.color.week_text));
        STYLES.put("유니크", new ShopStyle(R.color.violet_wisteria, R.color.violet_gem));
        STYLES.put("미시스타일", new ShopStyle(R.color.blue_mariner, R.color.blue_whale));
        STYLES.put("캠퍼스룩", new ShopStyle(R.color.yellow_energy, R.color.red_well));
        STYLES.put("빈티지", new ShopStyle(R.color.orange_neon, R.color.orange_sun));
        STYLES.put("섹시글램", new ShopStyle(R.color.red_terra, R.color.red_well));
        STYLES.put("스쿨룩", new ShopStyle(R.color.yellow_turbo, R.color.red_valencia));
        STYLES.put("로맨틱", new ShopStyle(R.color.gray_almond, R.color.dark_gray));
        STYLES.put("오피스룩", new ShopStyle(R.color.gray_smoke, R.color.light_gray));
        STYLES.put("럭셔리", new ShopStyle(R.color.green_fern, R.color.blue_denim));
        STYLES.put("헐리웃스타일", new ShopStyle(R.color.blue_picton, R.color.violet_gem));
    }

    private SharedPreferences sharedPreferences;

    public FilterService(Context context) {
        sharedPreferences = context.getSharedPreferences("filter", Context.MODE_PRIVATE);
    }

    public int[] getFilterByAges() {
        int[] result = new int[FilterService.AGES.length];
        String ageVal = sharedPreferences.getString("ages", "0000000");

        for (int i = 0; i < AGES.length; i++) {
            result[i] = Character.getNumericValue(ageVal.charAt(i));
        }
        return result;
    }

    public Set<String> getFilterByStyles() {
        return sharedPreferences.getStringSet("styles", new HashSet<String>());
    }

    public void setFilter(final int[] ages, final Set<String> styles) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();

        StringBuilder ageVal = new StringBuilder();
        for (int n : ages) {
            ageVal.append(String.valueOf(n));
        }
        editor.putString("ages", ageVal.toString());
        editor.putStringSet("styles", styles);
        editor.apply();
    }

    private ArrayList<Shop> filteredByAges(ArrayList<Shop> shops) {
        ArrayList<Shop> result = new ArrayList<>();
        final int[] conditions = getFilterByAges();

        for (Shop shop : shops) {
            final int[] ages = shop.getAges();
            for (int i = 0; i < ages.length; i++) {
                if (conditions[i] == 1 && ages[i] == 1) {
                    result.add(shop);
                    break;
                }
            }
        }
        return result;
    }

    private ArrayList<Shop> filteredByStyles(ArrayList<Shop> shops) {
        ArrayList<Shop> result = new ArrayList<>();
        final Set<String> conditions = getFilterByStyles();

        for (Shop shop : shops) {
            for (String style : shop.getStyles()) {
                if (conditions.contains(style)) {
                    result.add(shop);
                    break;
                }
            }
        }
        return result;
    }

    private boolean containsAgeCondition() {
        final int[] with = getFilterByAges();
        for (int element : with) {
            if (element == 1) return true;
        }
        return false;
    }

    public ArrayList<Shop> getFilteredShops(ArrayList<Shop> shops) {
        ArrayList<Shop> result = shops;
        final Set<String> styles = getFilterByStyles();

        if (styles.size() != 0) {
            result = filteredByStyles(result);
        }

        if (containsAgeCondition()) {
            result = filteredByAges(result);
        }

        Collections.sort(result, new Comparator<Shop>() {
            @Override
            public int compare(Shop lhs, Shop rhs) {
                int matchesLeft = lhs.getMatches(styles);
                int matchesRight = rhs.getMatches(styles);

                if (matchesLeft == matchesRight) {
                    if (lhs.getScore() > rhs.getScore()) {
                        return -1;
                    } else if (lhs.getScore() == rhs.getScore()) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else {
                    return matchesLeft > matchesRight ? -1 : 1;
                }
            }
        });
        return result;
    }
}
