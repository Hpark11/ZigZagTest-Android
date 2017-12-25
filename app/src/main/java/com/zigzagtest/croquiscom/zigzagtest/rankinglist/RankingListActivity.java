package com.zigzagtest.croquiscom.zigzagtest.rankinglist;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableRow;

import com.zigzagtest.croquiscom.zigzagtest.R;
import com.zigzagtest.croquiscom.zigzagtest.databinding.ActivityRankingListBinding;
import com.zigzagtest.croquiscom.zigzagtest.filter.FilterActivity;
import com.zigzagtest.croquiscom.zigzagtest.service.APIService;
import com.zigzagtest.croquiscom.zigzagtest.service.FilterService;
import com.zigzagtest.croquiscom.zigzagtest.util.ImageCache;

import java.util.ArrayList;

public class RankingListActivity extends AppCompatActivity {
    private static final String TAG = RankingListActivity.class.getSimpleName();
    private static final int REQUEST_CODE_FILTERING_DONE = 1000;
    private ArrayList<Shop> mShopArrayList;

    private ActivityRankingListBinding b;
    private ImageCache mImageCache;
    private FilterService mFilterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_ranking_list);
        mImageCache = new ImageCache();
        mFilterService = new FilterService(this);
        initViews();
    }

    private void initViews() {
        final String week = APIService.getWeek(this);
        mShopArrayList = APIService.getShopList(this);

        ArrayList<Shop> shops = mFilterService.setStyleMatchesToEachItems(mShopArrayList);
        shops = mFilterService.getFilteredShops(shops);

        b.mRankingListView.setAdapter(new ShopListAdapter(this, shops, mImageCache));
        b.mWeekTextView.setText(week);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rankinglist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.openFilter) {
            Intent intent = new Intent(this, FilterActivity.class);
            startActivityForResult(intent, REQUEST_CODE_FILTERING_DONE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_FILTERING_DONE && resultCode == RESULT_OK) {
            ShopListAdapter adapter = (ShopListAdapter)b.mRankingListView.getAdapter();

            ArrayList<Shop> shops = mFilterService.setStyleMatchesToEachItems(mShopArrayList);
            shops = mFilterService.getFilteredShops(shops);
            adapter.resetShopList(shops);
        }
    }
}
