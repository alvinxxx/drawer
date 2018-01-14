package com.example.alvinlam.drawer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.alvinlam.drawer.fragment.StockChartFragment;
import com.example.alvinlam.drawer.fragment.StockDetailFragment;

/**
 * Created by Alvin Lam on 1/14/2018.
 */

public class StockDetailAdapter extends FragmentPagerAdapter {

    public StockDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                StockDetailFragment mDetailFragment = new StockDetailFragment();
                return mDetailFragment;
            case 1:
                StockChartFragment mChartFragment = new StockChartFragment();
                return mChartFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
