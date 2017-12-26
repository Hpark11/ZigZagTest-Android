package com.zigzagtest.croquiscom.zigzagtest.filter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;

import com.zigzagtest.croquiscom.zigzagtest.R;
import com.zigzagtest.croquiscom.zigzagtest.databinding.ActivityFilterBinding;
import com.zigzagtest.croquiscom.zigzagtest.service.FilterService;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by croquiscom on 2017. 12. 20..
 */

public class FilterActivity extends AppCompatActivity {
    private static final String TAG = FilterActivity.class.getSimpleName();
    private ActivityFilterBinding b;
    private final String STYLE_FILTER_TAG = "1000";

    private FilterService mFilterService;
    private int[] mAgeConditions = new int[FilterService.AGES.length];
    private Set<String> mStyleConditions = new TreeSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_filter);

        mFilterService = new FilterService(this);
        mAgeConditions = mFilterService.getFilterByAges();
        mStyleConditions = mFilterService.getFilterByStyles();

        loadPreviousFilterItemSetting();
    }

    private void loadPreviousFilterItemSetting() {
        for(int i = 0; i < b.mAgeFilterTableLayout.getChildCount(); i++ ) {
            TableRow row = (TableRow) b.mAgeFilterTableLayout.getChildAt(i);
            for(int j = 0; j < row.getChildCount(); j++) {
                ToggleButton button = (ToggleButton)row.getChildAt(j);
                if(button.getVisibility() == View.VISIBLE && mAgeConditions[Integer.parseInt((String)button.getTag())] == 1) {
                    button.setChecked(true);
                    onCheckFilterCondition(button);
                }
            }
        }

        for(int i = 0; i < b.mStyleFilterTableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) b.mStyleFilterTableLayout.getChildAt(i);
            for(int j = 0; j < row.getChildCount(); j++) {
                ToggleButton button = (ToggleButton)row.getChildAt(j);
                if(button.getVisibility() == View.VISIBLE && mStyleConditions.contains(button.getTextOn())) {
                    button.setChecked(true);
                    onCheckFilterCondition(button);
                }
            }
        }
    }

    private void initializeFilter() {
        mAgeConditions = new int[FilterService.AGES.length];
        mStyleConditions = new TreeSet<>();

        uncheckAllItems(b.mAgeFilterTableLayout);
        uncheckAllItems(b.mStyleFilterTableLayout);
    }

    private void uncheckAllItems(TableLayout tableLayout) {
        for(int i = 0; i < tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            for(int j = 0; j < row.getChildCount(); j++) {
                final ToggleButton child = (ToggleButton)row.getChildAt(j);
                child.setChecked(false);
                onCheckFilterCondition(child);
            }
        }
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

    public void onConfirmFilter(View view) {
        mFilterService.setFilter(mAgeConditions, mStyleConditions);
        setResult(RESULT_OK, new Intent());
        finish();
    }

    public void onCheckFilterCondition(View view) {
        ToggleButton button = (ToggleButton)view;

        if(button.isChecked()) {
            button.setTextColor(getResources().getColor(R.color.colorWhite));
            if(button.getTag().equals(STYLE_FILTER_TAG)) {
                mStyleConditions.add(String.valueOf(button.getTextOn()));
            } else {
                mAgeConditions[Integer.parseInt((String)button.getTag())] = 1;
            }
        } else {
            if(button.getTag().equals(STYLE_FILTER_TAG)) {
                button.setTextColor(getResources().getColor(R.color.colorFilterStyles));
                mStyleConditions.remove(String.valueOf(button.getTextOn()));
            } else {
                button.setTextColor(getResources().getColor(R.color.colorFilterAges));
                mAgeConditions[Integer.parseInt((String)button.getTag())] = 0;
            }
        }
    }
}
