package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zigzagtest.croquiscom.zigzagtest.R;
import com.zigzagtest.croquiscom.zigzagtest.databinding.ActivityRankingListBinding;

public class RankingListActivity extends AppCompatActivity {
    ActivityRankingListBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_ranking_list);
        initViews();
    }



    private void initViews() {




        //ShopListAdapter shopListAdapter = new ShopListAdapter(this, );

        //b.mRankingListView.setAdapter();
    }
















}
