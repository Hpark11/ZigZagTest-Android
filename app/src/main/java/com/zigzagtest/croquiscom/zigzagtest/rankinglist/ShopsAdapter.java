package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zigzagtest.croquiscom.zigzagtest.BR;
import com.zigzagtest.croquiscom.zigzagtest.databinding.ItemShopBinding;

import java.util.ArrayList;

/**
 * Created by croquiscom on 2017. 12. 26..
 */

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Shop> mShopItems;

    public ShopsAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemShopBinding itemShopBinding = ItemShopBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemShopBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemShopBinding mBinding;

        private ViewHolder(ItemShopBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Shop shop) {
//            mBinding.setShop();
            mBinding.executePendingBindings();
        }
    }
}
