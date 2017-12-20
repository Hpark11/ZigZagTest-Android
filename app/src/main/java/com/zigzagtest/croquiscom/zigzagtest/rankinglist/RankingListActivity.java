package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.zigzagtest.croquiscom.zigzagtest.R;
import com.zigzagtest.croquiscom.zigzagtest.databinding.ActivityRankingListBinding;
import com.zigzagtest.croquiscom.zigzagtest.service.APIService;

import java.util.ArrayList;

public class RankingListActivity extends AppCompatActivity {
    private static final String TAG = RankingListActivity.class.getSimpleName();
    private ActivityRankingListBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_ranking_list);
        initViews();
    }

    private void initViews() {
        String week = APIService.getWeek(this);
        ArrayList<Shop> shopArrayList = APIService.getShopList(this);

        int a = 2;

        ShopListAdapter shopListAdapter = new ShopListAdapter(this, shopArrayList);
        b.mRankingListView.setAdapter(shopListAdapter);
        b.mWeekTextView.setText(week);

        //b.mRankingListView.setAdapter();
    }
















}
