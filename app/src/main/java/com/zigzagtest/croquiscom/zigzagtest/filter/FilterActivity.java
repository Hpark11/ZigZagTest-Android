package com.zigzagtest.croquiscom.zigzagtest.filter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.CheckBox;

import com.zigzagtest.croquiscom.zigzagtest.R;
import com.zigzagtest.croquiscom.zigzagtest.databinding.ActivityFilterBinding;
import com.zigzagtest.croquiscom.zigzagtest.service.FilterService;

import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity {
    private ActivityFilterBinding mBinding;
    private FilterService mFilterService;
    private int[] mAgeConditions = new int[FilterService.AGES.length];
    private Set<String> mStyleConditions = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        ButterKnife.bind(this);

        mFilterService = new FilterService(this);
        mAgeConditions = FilterService.getAgeFilter();
        mStyleConditions = FilterService.getStyleFilter();
        refreshButtons();
    }

    private void initializeFilter() {
        mAgeConditions = new int[FilterService.AGES.length];
        mStyleConditions = new HashSet<>();
        refreshButtons();
    }

    private void refreshButtons() {
        mBinding.setAgeFilter(mAgeConditions);
        mBinding.setStyleFilter(mStyleConditions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
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
        return true;
    }

    @OnClick(R.id.confirmButton)
    public void onConfirmFilter(View view) {
        mFilterService.setFilter(mAgeConditions, mStyleConditions);
        setResult(RESULT_OK, new Intent());
        finish();
    }

    @OnClick({R.id.ageBox1, R.id.ageBox2, R.id.ageBox3, R.id.ageBox4, R.id.ageBox5, R.id.ageBox6})
    public void onCheckAgeFilter(CheckBox checkBox) {
        final int index = Integer.parseInt((String) checkBox.getTag());
        mAgeConditions[index] = checkBox.isChecked() ? 1 : 0;
    }

    @OnClick({R.id.styleBox1, R.id.styleBox2, R.id.styleBox3, R.id.styleBox4, R.id.styleBox5,
            R.id.styleBox6, R.id.styleBox7, R.id.styleBox8, R.id.styleBox9, R.id.styleBox10,
            R.id.styleBox11, R.id.styleBox12, R.id.styleBox13, R.id.styleBox14})
    public void onCheckStyleFilter(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            mStyleConditions.add(String.valueOf(checkBox.getText()));
        } else {
            mStyleConditions.remove(String.valueOf(checkBox.getText()));
        }
    }
}
