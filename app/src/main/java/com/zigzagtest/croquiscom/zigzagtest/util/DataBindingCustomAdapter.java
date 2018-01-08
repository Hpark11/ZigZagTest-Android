package com.zigzagtest.croquiscom.zigzagtest.util;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zigzagtest.croquiscom.zigzagtest.extension.CircularImageMask;
import com.zigzagtest.croquiscom.zigzagtest.rankinglist.ShopStyle;
import com.zigzagtest.croquiscom.zigzagtest.service.FilterService;

import java.util.Set;

final public class DataBindingCustomAdapter {
    @BindingAdapter("shopUrl")
    public static void downloadImage(ImageView imageView, final String shopUrl) {
        if (shopUrl != null) {
            final String replacedStr = shopUrl.replaceAll("(http://www.|www.|http://)([\\w-]+)([.\\w/]+)", "$2");
            final String imageUrl = "https://cf.shop.s.zigzag.kr/images/" + replacedStr + ".jpg";
            Picasso.with(imageView.getContext()).load(imageUrl).transform(new CircularImageMask()).into(imageView);
        }
    }

    @BindingAdapter("ageFilter")
    public static void bindAgeFilter(CheckBox checkBox, final int[] ageFilter) {
        final int index = Integer.parseInt((String) checkBox.getTag());
        checkBox.setChecked(ageFilter[index] == 1);
    }

    @BindingAdapter("styleFilter")
    public static void bindStyleFilter(CheckBox checkBox, final Set styleFilter) {
        final String item = String.valueOf(checkBox.getText());
        checkBox.setChecked(styleFilter.contains(item));
    }

    @BindingAdapter("interior")
    public static void setTextViewInterior(TextView textView, final String text) {
        if(text.length() == 0) {
            textView.setVisibility(View.GONE);
            return;
        }

        textView.setText(text);
        final ShopStyle colors = FilterService.STYLES.get(text);
        final GradientDrawable drawable = (GradientDrawable) textView.getBackground();
        final Resources resources = textView.getContext().getResources();

        drawable.setStroke(1, resources.getColor(colors.getKeyColor()));
        drawable.setColor(resources.getColor(colors.getBackgroundColor()));
        textView.setTextColor(resources.getColor(colors.getKeyColor()));
    }
}
