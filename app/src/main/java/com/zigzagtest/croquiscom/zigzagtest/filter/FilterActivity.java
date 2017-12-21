package com.zigzagtest.croquiscom.zigzagtest.filter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.zigzagtest.croquiscom.zigzagtest.R;
import com.zigzagtest.croquiscom.zigzagtest.databinding.ActivityFilterBinding;

/**
 * Created by croquiscom on 2017. 12. 20..
 */

public class FilterActivity extends AppCompatActivity {
    private static final String TAG = FilterActivity.class.getSimpleName();
    private ActivityFilterBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.initializeFilter:
                initializeFilter();
                break;
            case R.id.closeFilter:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeFilter() {

    }


}
