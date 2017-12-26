package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zigzagtest.croquiscom.zigzagtest.R;
import com.zigzagtest.croquiscom.zigzagtest.extension.RoundedImageDrawable;
import com.zigzagtest.croquiscom.zigzagtest.databinding.ItemShopBinding;
import com.zigzagtest.croquiscom.zigzagtest.service.APIService;
import com.zigzagtest.croquiscom.zigzagtest.service.FilterService;
import com.zigzagtest.croquiscom.zigzagtest.util.ImageCache;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by croquiscom on 2017. 12. 20..
 */

public final class ShopListAdapter extends BaseAdapter {
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
            shopViewHolder.verifyTaskStateAndInturrupt();
        }

        shopViewHolder.setDataIntoUIControls(mShopArrayList.get(position), position + 1);
        return convertView;
    }

    public void resetShopList(ArrayList<Shop> shops) {
        mShopArrayList = shops;
        notifyDataSetChanged();
    }

    private class ShopViewHolder {
        ItemShopBinding b;
        Thread task;

        public ShopViewHolder(View itemView) {
            b = DataBindingUtil.bind(itemView);
        }

        public void verifyTaskStateAndInturrupt() {
            if (task != null && task.isAlive()) {
                task.interrupt();
                task = null;
            }
        }

        public void generateNewImageDataTask(final String stringUrl) {
            task = new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = null;
                    try {
                        bitmap = APIService.getBitmapFromUrl(stringUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (bitmap != null) {
                            final Bitmap finalBitmap = bitmap;
                            ((AppCompatActivity)mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    b.mShopImageView.setImageDrawable(new RoundedImageDrawable(finalBitmap));
                                    mImageCache.addBitmapToMemoryCache(stringUrl, finalBitmap);
                                }
                            });
                        }
                    }
                }
            });
            task.start();
        }

        public void setDataIntoUIControls(final Shop shop, int rank) {
            b.mRankNumberTextView.setText(String.valueOf(rank));
            b.mShopNameTextView.setText(shop.name);
            setTextViewInterior(b.mStyleTextView1, shop.style.length > 0 ? shop.style[0] : "");
            setTextViewInterior(b.mStyleTextView2, shop.style.length > 1 ? shop.style[1] : "");
            setTextViewInterior(b.mAgeTextView, FilterService.getRepresentativeAgesData(shop.age));

            final String replacedStr = shop.url.replaceAll("(http://www.|www.|http://)([\\w-]+)([.\\w/]+)", "$2");
            final String stringUrl = "https://cf.shop.s.zigzag.kr/images/" + replacedStr + ".jpg";
            final Bitmap imageBitmap = mImageCache.getBitmapFromMemCache(stringUrl);

            if(imageBitmap != null) {
                b.mShopImageView.setImageDrawable(new RoundedImageDrawable(imageBitmap));
            } else {
                generateNewImageDataTask(stringUrl);
            }
        }

        private void setTextViewInterior(TextView textView, final String text) {
            textView.setText(text);
            if(textView.getText().equals("")) {
                textView.setVisibility(View.INVISIBLE);
            }

            Pair<Integer, Integer> p = FilterService.STYLES.get(text);
            GradientDrawable drawable = (GradientDrawable) textView.getBackground();

            if (p == null) {
                drawable.setStroke(1, Color.DKGRAY);
                drawable.setColor(Color.TRANSPARENT);
                textView.setTextColor(Color.DKGRAY);
            } else {
                drawable.setStroke(1, mContext.getResources().getColor(p.second));
                drawable.setColor(mContext.getResources().getColor(p.first));
                textView.setTextColor(mContext.getResources().getColor(p.second));
            }
        }
    }
}
