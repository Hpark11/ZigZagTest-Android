package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zigzagtest.croquiscom.zigzagtest.databinding.ItemShopBinding;

import java.util.ArrayList;

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ViewHolder> {
    private String mWeek;
    private ArrayList<Shop> mShopItems;

    public ShopsAdapter(ArrayList<Shop> shops, String week) {
        mShopItems = shops;
        mWeek = week;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemShopBinding itemShopBinding = ItemShopBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemShopBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            holder.bindSectionHeader(mWeek);
        } else {
            holder.bindItem(mShopItems.get(position - 1), position);
        }
    }

    public void resetShopList(ArrayList<Shop> shops) {
        mShopItems = shops;
        notifyDataSetChanged();
        // scroll reset
    }

    @Override
    public int getItemCount() {
        return mShopItems.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemShopBinding mItemBinding;

        private ViewHolder(ItemShopBinding itemShopBinding) {
            super(itemShopBinding.getRoot());
            mItemBinding = itemShopBinding;
        }

        void bindItem(Shop shop, int rank) {
            mItemBinding.setShop(shop);
            mItemBinding.setRank(String.valueOf(rank));
            mItemBinding.setIsSection(false);
            mItemBinding.executePendingBindings();
        }

        void bindSectionHeader(String week) {
            mItemBinding.setWeek(week);
            mItemBinding.setIsSection(true);
            mItemBinding.executePendingBindings();
        }
    }
}
