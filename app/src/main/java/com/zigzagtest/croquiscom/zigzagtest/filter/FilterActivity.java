package com.zigzagtest.croquiscom.zigzagtest.filter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zigzagtest.croquiscom.zigzagtest.R;
import com.zigzagtest.croquiscom.zigzagtest.databinding.ActivityFilterBinding;

/**
 * Created by croquiscom on 2017. 12. 20..
 */

public class FilterActivity extends AppCompatActivity {
    ActivityFilterBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_filter);
    }



}
