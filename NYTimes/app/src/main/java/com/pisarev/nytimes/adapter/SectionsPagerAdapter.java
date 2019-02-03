package com.pisarev.nytimes.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pisarev.nytimes.Const;
import com.pisarev.nytimes.fragment.MyFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super( fm );
    }

    @Override
    public Fragment getItem(int position) {
        return MyFragment.newInstance( position );
    }

    @Override
    public int getCount() {
        return Const.SECTION.length;
    }

}