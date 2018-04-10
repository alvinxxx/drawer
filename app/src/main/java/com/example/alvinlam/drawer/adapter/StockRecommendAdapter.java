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
import com.example.alvinlam.drawer.fragment.StockRecommendFragment;
import com.example.alvinlam.drawer.fragment.StockRecommendThreeFragment;

/**
 * Created by Alvin Lam on 1/14/2018.
 */

public class StockRecommendAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public StockRecommendAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                StockRecommendFragment mRecommendFragment = new StockRecommendFragment();
                return mRecommendFragment;
            case 1:
                StockRecommendThreeFragment mRecommendThreeFragment = new StockRecommendThreeFragment();
                return mRecommendThreeFragment;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.recommend_std);
            case 1:
                return mContext.getString(R.string.recommend_std_three);
        }
        return null;
    }
}
