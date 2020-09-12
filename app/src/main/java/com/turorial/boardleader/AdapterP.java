package com.turorial.boardleader;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class AdapterP extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public AdapterP(@NonNull FragmentManager fm, int mNumOfTabs) {
        super(fm, mNumOfTabs);
        this.mNumOfTabs = mNumOfTabs ;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new LearningSqill();
            case 1: return new IqSkill();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
