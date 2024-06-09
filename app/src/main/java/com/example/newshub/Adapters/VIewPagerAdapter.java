package com.example.newshub.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.newshub.Fragments.AllFragment;
import com.example.newshub.Fragments.LikedFragment;

public class VIewPagerAdapter extends FragmentPagerAdapter {
    public VIewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public VIewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){return new AllFragment();}
        else if(position==1){return new LikedFragment();}
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0) return "All";
        else if(position==1) return "Favorite";
        return null;
    }
}
