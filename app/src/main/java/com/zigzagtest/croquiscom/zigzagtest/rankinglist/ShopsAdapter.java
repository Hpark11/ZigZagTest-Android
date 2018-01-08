package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zigzagtest.croquiscom.zigzagtest.databinding.ItemSectionHeaderBinding;
import com.zigzagtest.croquiscom.zigzagtest.databinding.ItemShopBinding;
import com.zigzagtest.croquiscom.zigzagtest.service.APIService;
import com.zigzagtest.croquiscom.zigzagtest.service.FilterService;

import java.util.ArrayList;

final public class ShopsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String mWeek;
    private ArrayList<Shop> mOriginalShopItems;
    private ArrayList<Shop> mShopItems;
    private FilterService mFilterService;

    ShopsAdapter(Context context) {
        mFilterService = new FilterService(context);
        mWeek = APIService.getWeek(context);
        mOriginalShopItems = APIService.getShopList(context);
        resetShopList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case 0:
                return new HeaderViewHolder(ItemSectionHeaderBinding.inflate(layoutInflater, parent, false));
            default:
                return new ItemViewHolder(ItemShopBinding.inflate(layoutInflater, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ((HeaderViewHolder) holder).bindSectionHeader();
                break;
            default:
                ((ItemViewHolder) holder).bindItem(mShopItems.get(position - 1), position);
                break;
        }
    }

    void resetShopList() {
        mShopItems = mFilterService.getFilteredShops(mOriginalShopItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return mShopItems.size() + 1;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemShopBinding mBinding;

        private ItemViewHolder(ItemShopBinding itemShopBinding) {
            super(itemShopBinding.getRoot());
            mBinding = itemShopBinding;
        }

        void bindItem(Shop shop, int rank) {
            mBinding.setShop(shop);
            mBinding.setRank(String.valueOf(rank));
            mBinding.executePendingBindings();
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final ItemSectionHeaderBinding mBinding;

        private HeaderViewHolder(ItemSectionHeaderBinding headerBinding) {
            super(headerBinding.getRoot());
            mBinding = headerBinding;
        }

        void bindSectionHeader() {
            mBinding.setWeek(mWeek);
            mBinding.executePendingBindings();
        }
    }
}

