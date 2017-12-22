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
import com.zigzagtest.croquiscom.zigzagtest.util.ImageCache;

import java.util.ArrayList;

public class RankingListActivity extends AppCompatActivity {
    private static final String TAG = RankingListActivity.class.getSimpleName();
    private static final int REQUEST_CODE_FILTERING_DONE = 1000;

    private ActivityRankingListBinding b;
    private ImageCache mImageCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_ranking_list);
        mImageCache = new ImageCache();
        initViews();
    }

    private void initViews() {
        final String week = APIService.getWeek(this);
        final ArrayList<Shop> shopArrayList = APIService.getShopList(this);

        ShopListAdapter shopListAdapter = new ShopListAdapter(this, shopArrayList, mImageCache);
        b.mRankingListView.setAdapter(shopListAdapter);
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

        }
    }
}
