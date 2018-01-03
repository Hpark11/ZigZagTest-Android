package com.zigzagtest.croquiscom.zigzagtest.util;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zigzagtest.croquiscom.zigzagtest.R;
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

    @BindingAdapter("ageFilterMarked")
    public static void markAgeFilterCondition(CheckBox checkBox, final int[] ageFilter) {
        final int index = Integer.parseInt((String) checkBox.getTag());
        checkBox.setChecked(ageFilter[index] == 1);
    }

    @BindingAdapter("styleFilterMarked")
    public static void markStyleFilterCondition(CheckBox checkBox, final Set styleFilter) {
        final String item = String.valueOf(checkBox.getText());
        checkBox.setChecked(styleFilter.contains(item));
    }

    @BindingAdapter("interior")
    public static void setTextViewInterior(TextView textView, final String text) {
        textView.setText(text);
        final ShopStyle colors = FilterService.STYLES.get(text);
        final GradientDrawable drawable = (GradientDrawable) textView.getBackground();
        final Resources resources = textView.getContext().getResources();

        if (colors != null) {
            drawable.setStroke(1, resources.getColor(colors.getKeyColor()));
            drawable.setColor(resources.getColor(colors.getBackgroundColor()));
            textView.setTextColor(resources.getColor(colors.getKeyColor()));
        }
        if (textView.getId() == R.id.ageTextView) {
            drawable.setStroke(1, Color.DKGRAY);
            drawable.setColor(Color.TRANSPARENT);
            textView.setTextColor(Color.DKGRAY);
        }
    }

}
