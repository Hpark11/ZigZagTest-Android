package com.zigzagtest.croquiscom.zigzagtest.util;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by croquiscom on 2017. 12. 26..
 */

public class DataBindingCustomAdapter {

    @BindingAdapter("srcUrl")
    public static void downloadImage(ImageView imageView, final String srcUrl) {
        final String replacedStr = srcUrl.replaceAll("(http://www.|www.|http://)([\\w-]+)([.\\w/]+)", "$2");
        final String stringUrl = "https://cf.shop.s.zigzag.kr/images/" + replacedStr + ".jpg";
    }

    //@BindingAdapter("")
    //public static void d




}
