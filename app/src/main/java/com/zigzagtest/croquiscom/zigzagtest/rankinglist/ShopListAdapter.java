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
import com.zigzagtest.croquiscom.zigzagtest.extension.CircularImageMaskDrawable;
import com.zigzagtest.croquiscom.zigzagtest.databinding.ItemShopBinding;
import com.zigzagtest.croquiscom.zigzagtest.service.APIService;
import com.zigzagtest.croquiscom.zigzagtest.service.FilterService;
import com.zigzagtest.croquiscom.zigzagtest.util.ImageCache;

import java.util.ArrayList;

public final class ShopListAdapter extends BaseAdapter {
    private ArrayList<Shop> mShopArrayList;
    private Context mContext;
    private LayoutInflater mInflater;
    private ImageCache mImageCache;

    ShopListAdapter(Context context, ArrayList<Shop> shops, ImageCache imageCache) {
        mContext = context;
        mShopArrayList = shops;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            convertView = mInflater.inflate(R.layout.item_shop, viewGroup, false);
            shopViewHolder = new ShopViewHolder(convertView);
            convertView.setTag(shopViewHolder);
        } else {
            shopViewHolder = (ShopViewHolder) convertView.getTag();
            shopViewHolder.b.mShopImageView.setImageBitmap(null);
            shopViewHolder.verifyTaskStateAndInterrupt();
        }

        shopViewHolder.setDataIntoUIControls(mShopArrayList.get(position), position + 1);
        return convertView;
    }

    public void resetShopList(ArrayList<Shop> shops) {
        mShopArrayList = shops;
        notifyDataSetChanged();
        // scroll reset
    }

    private class ShopViewHolder {
        private ItemShopBinding b;
        private Thread task;    // naming

        ShopViewHolder(View itemView) {
            b = DataBindingUtil.bind(itemView);
        }

        void verifyTaskStateAndInterrupt() {
            if (task != null && task.isAlive()) {
                task.interrupt();
                task = null;
            }
        }

        public void generateNewImageDataTask(final String stringUrl) {
            task = new Thread(new Runnable() {  // AsyncTask, Handler
                @Override
                public void run() {
                    Bitmap bitmap = null;
                    try {
                        bitmap = APIService.getBitmapFromUrl(stringUrl);
                    } catch (Exception e) {
                        e.printStackTrace();    // 디버그 용 제거
                    } finally {
                        if (bitmap != null) {
                            final Bitmap finalBitmap = bitmap;
                            ((AppCompatActivity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    b.mShopImageView.setImageDrawable(new CircularImageMaskDrawable(finalBitmap));   // 원하지 않는 이미지 나올 수 있음
                                    mImageCache.addBitmapToMemoryCache(stringUrl, finalBitmap); // 순서 조정
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

            //setTextViewInterior(b.mAgeTextView, shop.style.length > 0 ? shop.style[0] : "");
            String text = shop.style.length > 0 ? shop.style[0] : "";

//            b.mAgeTextView.setText(text);
//            if (b.mAgeTextView.getText().equals("")) {
//                b.mAgeTextView.setVisibility(View.INVISIBLE);
//            }
//
//            Pair<Integer, Integer> p = FilterService.STYLES.get(text);  // pair -> sturucture 좀 더 명확하게
//            GradientDrawable drawable = (GradientDrawable) b.mAgeTextView.getBackground();
//
//            drawable.setStroke(1, p == null ? Color.DKGRAY : mContext.getResources().getColor(p.second));
//            drawable.setColor(p == null ? Color.TRANSPARENT : mContext.getResources().getColor(p.first));
//            b.mAgeTextView.setTextColor(p == null ? Color.DKGRAY : mContext.getResources().getColor(p.second));

            // setTextViewInterior(b.mStyleTextView2, shop.style.length > 1 ? shop.style[1] : "");
            // b.mAgeTextView.setText(FilterService.getRepresentativeAgesData(shop.age));
            // setTextViewInterior(b.mAgeTextView, FilterService.getRepresentativeAgesData(shop.age)); // in shop

            b.mStyleTextView1.setText(shop.style.length > 0 ? shop.style[0] : "");
            b.mStyleTextView2.setText(shop.style.length > 1 ? shop.style[1] : "");
            b.mAgeTextView.setText(shop.getRepresentativeAgesData());

            final String replacedStr = shop.url.replaceAll("(http://www.|www.|http://)([\\w-]+)([.\\w/]+)", "$2");
            final String stringUrl = "https://cf.shop.s.zigzag.kr/images/" + replacedStr + ".jpg";
            final Bitmap imageBitmap = mImageCache.getBitmapFromMemCache(stringUrl);

            if (imageBitmap != null) {
                b.mShopImageView.setImageDrawable(new CircularImageMaskDrawable(imageBitmap));
            } else {
                generateNewImageDataTask(stringUrl);
            }
        }

        private void setTextViewInterior(TextView textView, final String text) {
            textView.setText(text);
            if (textView.getText().equals("")) {
                textView.setVisibility(View.INVISIBLE);
            }

            Pair<Integer, Integer> p = FilterService.STYLES.get(text);  // pair -> sturucture 좀 더 명확하게
            GradientDrawable drawable = (GradientDrawable) textView.getBackground();

            drawable.setStroke(1, p == null ? Color.DKGRAY : mContext.getResources().getColor(p.second));
            drawable.setColor(p == null ? Color.TRANSPARENT : mContext.getResources().getColor(p.first));
            textView.setTextColor(p == null ? Color.DKGRAY : mContext.getResources().getColor(p.second));
        }
    }
}
