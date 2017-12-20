package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zigzagtest.croquiscom.zigzagtest.R;
import com.zigzagtest.croquiscom.zigzagtest.RoundedImageDrawable;
import com.zigzagtest.croquiscom.zigzagtest.databinding.ItemShopBinding;
import com.zigzagtest.croquiscom.zigzagtest.service.APIService;
import com.zigzagtest.croquiscom.zigzagtest.service.FilterService;
import com.zigzagtest.croquiscom.zigzagtest.util.ImageCache;

import java.util.ArrayList;

/**
 * Created by croquiscom on 2017. 12. 20..
 */

public class ShopListAdapter extends BaseAdapter {
    private ArrayList<Shop> mShopArrayList;
    private Context mContext;
    private LayoutInflater mInflator;
    private ImageCache mImageCache;

    public ShopListAdapter(Context context, ArrayList<Shop> shops, ImageCache imageCache) {
        mContext = context;
        mShopArrayList = shops;
        mInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageCache = imageCache;
    }

    @Override
    public int getCount() {
        return mShopArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ShopViewHolder shopViewHolder;

        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.item_shop, viewGroup, false);
            shopViewHolder = new ShopViewHolder(convertView);
            convertView.setTag(shopViewHolder);
        } else {
            shopViewHolder = (ShopViewHolder) convertView.getTag();
            shopViewHolder.b.mShopImageView.setImageBitmap(null);
            if (shopViewHolder.task.isAlive()) {
                shopViewHolder.task.interrupt();
                shopViewHolder.task = null;
            }
        }

        shopViewHolder.setDataIntoUIControls(mShopArrayList.get(position), position + 1);
        return convertView;
    }

    private class ShopViewHolder {
        ItemShopBinding b;
        Thread task;

        public ShopViewHolder(View itemView) {
            b = DataBindingUtil.bind(itemView);
        }

        public void setDataIntoUIControls(final Shop shop, int rank) {
            b.mRankNumberTextView.setText(String.valueOf(rank));
            b.mShopNameTextView.setText(shop.name);
            b.mStyleTextView1.setText(shop.style.length > 0 ? shop.style[0] : "");
            b.mStyleTextView2.setText(shop.style.length > 1 ? shop.style[1] : "");
            b.mAgeTextView.setText(FilterService.getRepresentativeAgesData(shop.age));

            String replacedStr = shop.url.replaceAll("(http://www.|www.|http://)([\\w-]+)([.\\w/]+)", "$2");
            final String stringUrl = "https://cf.shop.s.zigzag.kr/images/" + replacedStr + ".jpg";

            final Bitmap bitmap = mImageCache.getBitmapFromMemCache(stringUrl);

            task = new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap1 = bitmap;
                    try {

                        if (bitmap1 == null) {
                            bitmap1 = APIService.getBitmapFromUrl(stringUrl);
                        }
                    } catch (Exception e) {

                    } finally {
                        if (bitmap1 != null) {
                            final Bitmap finalBitmap = bitmap1;
                            ((AppCompatActivity)mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    b.mShopImageView.setImageDrawable(new RoundedImageDrawable(finalBitmap));
                                    //mImageCache.addBitmapToMemoryCache(stringUrl, finalBitmap);
                                }
                            });
                        }
                    }
                }
            });

            task.start();
        }
    }



}
