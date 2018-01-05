package com.zigzagtest.croquiscom.zigzagtest.rankinglist;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.zigzagtest.croquiscom.zigzagtest.R;
import com.zigzagtest.croquiscom.zigzagtest.databinding.ActivityRankingListBinding;
import com.zigzagtest.croquiscom.zigzagtest.filter.FilterActivity;

public class RankingListActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_FILTERING_DONE = 1000;
    private ActivityRankingListBinding mBinding;
    private ShopsAdapter mShopsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_ranking_list);
        mShopsAdapter = new ShopsAdapter(this);
        mBinding.shopsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.shopsRecyclerView.setAdapter(mShopsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rankinglist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.openFilter) {
            startActivityForResult(new Intent(this, FilterActivity.class), REQUEST_CODE_FILTERING_DONE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FILTERING_DONE && resultCode == RESULT_OK) {
            mShopsAdapter.resetShopList();
            mBinding.shopsRecyclerView.scrollToPosition(0);
        }
    }
}
