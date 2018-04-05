package com.example.alvinlam.drawer.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.fragment.StockAlertFragment;
import com.example.alvinlam.drawer.fragment.StockAnalysisFragment;
import com.example.alvinlam.drawer.fragment.StockChartFragment;
import com.example.alvinlam.drawer.fragment.StockDetailFragment;

/**
 * Created by Alvin Lam on 1/14/2018.
 */

public class StockDetailAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public StockDetailAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                StockDetailFragment mDetailFragment = new StockDetailFragment();
                return mDetailFragment;
            case 1:
                StockAnalysisFragment mAnalysisFragment = new StockAnalysisFragment();
                return mAnalysisFragment;
            case 2:
                StockChartFragment mChartFragment = new StockChartFragment();
                return mChartFragment;
            case 3:
                StockAlertFragment mAlertFragment = new StockAlertFragment();
                return mAlertFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.stock_detail);
            case 1:
                return mContext.getString(R.string.stock_analysis);
            case 2:
                return mContext.getString(R.string.stock_chart);
            case 3:
                return mContext.getString(R.string.stock_alert);
        }
        return null;
    }
}
