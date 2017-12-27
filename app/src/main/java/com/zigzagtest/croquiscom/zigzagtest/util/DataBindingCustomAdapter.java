package com.zigzagtest.croquiscom.zigzagtest.util;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zigzagtest.croquiscom.zigzagtest.R;

final public class DataBindingCustomAdapter {
    @BindingAdapter("shopUrl")
    public static void downloadImage(ImageView imageView, final String shopUrl) {
        if (shopUrl != null) {
            final String replacedStr = shopUrl.replaceAll("(http://www.|www.|http://)([\\w-]+)([.\\w/]+)", "$2");
            final String imageUrl = "https://cf.shop.s.zigzag.kr/images/" + replacedStr + ".jpg";
            Picasso.with(imageView.getContext()).load(imageUrl).into(imageView);
        }
    }

    @BindingAdapter("isSection")
    public static void resetItemCell(FrameLayout frameLayout, final Boolean isSection) {
        final String headerTag = frameLayout.getContext()
                .getResources().getString(R.string.string_tag_section_header);

        for (int i = 0; i < frameLayout.getChildCount(); i++) {
            View layout = frameLayout.getChildAt(i);
            if (layout.getTag().equals(headerTag)) {
                layout.setVisibility(isSection ? View.VISIBLE : View.GONE);
            } else {
                layout.setVisibility(isSection ? View.GONE : View.VISIBLE);
            }
        }
    }
}
