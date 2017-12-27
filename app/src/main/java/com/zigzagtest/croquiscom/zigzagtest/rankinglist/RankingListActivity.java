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
import com.zigzagtest.croquiscom.zigzagtest.service.APIService;
import com.zigzagtest.croquiscom.zigzagtest.service.FilterService;

import java.util.ArrayList;

public class RankingListActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_FILTERING_DONE = 1000;

    private ArrayList<Shop> mShopItems;
    private ShopsAdapter mShopsAdapter;
    private FilterService mFilterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityRankingListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_ranking_list);
        mFilterService = new FilterService(this);

        final String week = APIService.getWeek(this);
        mShopItems = APIService.getShopList(this);

        mShopsAdapter = new ShopsAdapter(mFilterService.getFilteredShops(mShopItems), week);
        binding.shopsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.shopsRecyclerView.setAdapter(mShopsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rankinglist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.openFilter) {
            Intent intent = new Intent(this, FilterActivity.class);
            startActivityForResult(intent, REQUEST_CODE_FILTERING_DONE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FILTERING_DONE && resultCode == RESULT_OK) {
            mShopsAdapter.resetShopList(mFilterService.getFilteredShops(mShopItems));
        }
    }
}
