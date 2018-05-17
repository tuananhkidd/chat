package com.kidd.chat.common.adapter.view_pager_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kidd.chat.R;
import com.kidd.chat.views.main.account.AccountProgressFragment;
import com.kidd.chat.views.main.friends.FriendsProgressFragment;
import com.kidd.chat.views.main.home.HomeProgressFragment;


public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
    public static final int HOME_FRAGMENT_POSITION = 0;
    public static final int FRIENDS_FRAGMENT_POSITION = 1;
    public static final int PROFILE_FRAGMENT_POSITION = 2;

    private Fragment[] fragments;

    public HomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new Fragment[3];

        fragments[HOME_FRAGMENT_POSITION] = new HomeProgressFragment();
        fragments[FRIENDS_FRAGMENT_POSITION] = new FriendsProgressFragment();
        fragments[PROFILE_FRAGMENT_POSITION] = new AccountProgressFragment();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    public static int getMenuIDByPosition(int position) {
        switch (position) {
            case HOME_FRAGMENT_POSITION:{
                return R.id.navigation_home;
            }

            case FRIENDS_FRAGMENT_POSITION:{
                return R.id.navigation_friends;
            }

            case PROFILE_FRAGMENT_POSITION:{
                return R.id.navigation_account;
            }

            default:{
                return R.id.navigation_home;
            }
        }
    }
}
