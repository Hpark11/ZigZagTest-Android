package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import com.zigzagtest.croquiscom.zigzagtest.R;

import java.util.Set;

final public class ShopStyle {
    private String name;
    private int backgroundColor;
    private int borderColor;

    public String getName() {
        return name;
    }

    public int getBackgroundColor() {
        switch (name) {
            case "페미닌": return R.color.colorGreenFern;
        }

//        STYLES.put("페미닌", new Pair<>(R.color.colorGreenFern, R.color.colorDarkGray));
//        STYLES.put("모던시크", new Pair<>(R.color.colorGreenMountain, R.color.colorDarkGray));
//        STYLES.put("심플베이직", new Pair<>(R.color.colorBluePicton, R.color.colorBlueWhale));
//        STYLES.put("러블리", new Pair<>(R.color.colorBlueMariner, R.color.colorWeekText));
//        STYLES.put("유니크", new Pair<>(R.color.colorVioletWisteria, R.color.colorVioletGem));
//        STYLES.put("미시스타일", new Pair<>(R.color.colorBlueMariner, R.color.colorBlueWhale));
//        STYLES.put("캠퍼스룩", new Pair<>(R.color.colorYellowEnergy, R.color.colorRedWell));
//        STYLES.put("빈티지", new Pair<>(R.color.colorOrangeNeon, R.color.colorOrangeSun));
//        STYLES.put("섹시글램", new Pair<>(R.color.colorRedTerra, R.color.colorRedWell));
//        STYLES.put("스쿨룩", new Pair<>(R.color.colorYellowTurbo, R.color.colorRedValencia));
//        STYLES.put("로맨틱", new Pair<>(R.color.colorGrayAlmond, R.color.colorDarkGray));
//        STYLES.put("오피스룩", new Pair<>(R.color.colorGraySmoke, R.color.colorLightGray));
//        STYLES.put("럭셔리", new Pair<>(R.color.colorGreenFern, R.color.colorBlueDenim));
//        STYLES.put("헐리웃스타일", new Pair<>(R.color.colorBluePicton, R.color.colorVioletGem));

        return backgroundColor;
    }

    public int getBorderColor() {
        return borderColor;
    }

}
