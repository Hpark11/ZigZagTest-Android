package com.zigzagtest.croquiscom.zigzagtest.util;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by croquiscom on 2017. 12. 26..
 */

public class DataBindingCustomAdapter {

    @BindingAdapter("url")
    public static void downloadImage(ImageView imageView, final String url) {
        final String replacedStr = url.replaceAll("(http://www.|www.|http://)([\\w-]+)([.\\w/]+)", "$2");
        final String stringUrl = "https://cf.shop.s.zigzag.kr/images/" + replacedStr + ".jpg";
                
    }
}
