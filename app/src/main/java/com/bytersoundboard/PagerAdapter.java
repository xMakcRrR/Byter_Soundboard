package com.bytersoundboard;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private int numOfTubs;

    public PagerAdapter(FragmentManager fm, int numOfTubs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numOfTubs = numOfTubs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ByterButtonsPage();
            case 1:
                return new FavoriteButtonsPage();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTubs;
    }
}
