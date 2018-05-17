package com.kidd.chat.views.main.home;

import android.support.v4.app.Fragment;

import com.kidd.chat.presenters.base_progress_fragment.BaseProgressFragmentPresenter;
import com.kidd.chat.presenters.main.home.HomeProgressFragmentPresenter;
import com.kidd.chat.views.base.fragment.BaseProgressFragment;


/**
 * Created by TranThanhTung on 19/02/2018.
 */

public class HomeProgressFragment extends BaseProgressFragment {
    @Override
    protected Fragment initPrimaryFragment() {
        return new HomeFragment();
    }

    @Override
    protected BaseProgressFragmentPresenter initPresenter() {
        return new HomeProgressFragmentPresenter();
    }
}
