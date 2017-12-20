package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        return 0;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ShopViewHolder shopViewHolder = new ShopViewHolder(view);
        return view;
    }

    private class ShopViewHolder {
        ItemShopBinding b;

        public ShopViewHolder(View itemView) {
            b = DataBindingUtil.bind(itemView);
        }
    }



}
