package com.gck.simplecontacts;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Pervacio on 06-06-2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ContactsListFragment.newInstance();
            case 1:
                return CallsFragment.newInstance("", "");
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
