package com.dev.jahid.expensetracker.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dev.jahid.expensetracker.ui.DataFragment;

import java.util.ArrayList;

public class DataViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<DataFragment> dataFragments = new ArrayList<>();

    public DataViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<DataFragment> dataFragments) {
        super(fm);
        this.dataFragments = dataFragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return dataFragments.get(position);
    }

    @Override
    public int getCount() {
        return dataFragments.size();
    }
}
