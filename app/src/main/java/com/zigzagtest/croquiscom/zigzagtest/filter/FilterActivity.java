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
        mAgeConditions = mFilterService.getFilterByAges();
        mStyleConditions = mFilterService.getFilterByStyles();
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

    @OnClick({R.id.ageBox10s, R.id.ageBoxEarly20s, R.id.ageBoxMid20s, R.id.ageBoxLate20s, R.id.ageBoxEarly30s, R.id.ageBoxMid30s})
    public void onCheckAgeFilter(CheckBox checkBox) {
        final int index = Integer.parseInt((String) checkBox.getTag());
        mAgeConditions[index] = checkBox.isChecked() ? 1 : 0;
    }

    @OnClick({R.id.styleBoxFamine, R.id.styleBoxModernCynical, R.id.styleBoxSimpleBasic, R.id.styleBoxLovely, R.id.styleBoxUnique,
            R.id.styleBoxMissi, R.id.styleBoxCampusLook, R.id.styleBoxVintage, R.id.styleBoxSexyGlam, R.id.styleBoxSchoolLook,
            R.id.styleBoxRomantic, R.id.styleBoxOfficeLook, R.id.styleBoxLuxury, R.id.styleBoxHollywood})
    public void onCheckStyleFilter(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            mStyleConditions.add(String.valueOf(checkBox.getText()));
        } else {
            mStyleConditions.remove(String.valueOf(checkBox.getText()));
        }
    }
}
