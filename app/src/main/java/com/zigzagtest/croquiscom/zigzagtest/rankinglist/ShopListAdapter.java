package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zigzagtest.croquiscom.zigzagtest.R;
import com.zigzagtest.croquiscom.zigzagtest.databinding.ItemShopBinding;

import java.util.ArrayList;

/**
 * Created by croquiscom on 2017. 12. 20..
 */

public class ShopListAdapter extends BaseAdapter {
    private ArrayList<Shop> mShopArrayList;
    private Context mContext;
    private LayoutInflater mInflator;

    public ShopListAdapter(Context context, ArrayList<Shop> shops) {
        mContext = context;
        mShopArrayList = shops;
        mInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        }

        shopViewHolder.setDataIntoUIControls(mShopArrayList.get(position), position + 1);
        return convertView;
    }

    private class ShopViewHolder {
        ItemShopBinding b;

        public ShopViewHolder(View itemView) {
            b = DataBindingUtil.bind(itemView);
        }

        public void setDataIntoUIControls(final Shop shop, int rank) {
            b.mRankNumberTextView.setText("" + rank);
            b.mShopNameTextView.setText(shop.name);
            b.mStyleTextView1.setText(shop.style.length > 0 ? shop.style[0] : "");
            b.mStyleTextView2.setText(shop.style.length > 1 ? shop.style[1] : "");

            //b.mShopImageView.setDr
        }
    }



}
