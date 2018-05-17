package com.kidd.chat.views.main.friends;

import android.support.v4.app.Fragment;

import com.kidd.chat.presenters.base_progress_fragment.BaseProgressFragmentPresenter;
import com.kidd.chat.presenters.main.friends.FriendsProgressPresenter;
import com.kidd.chat.views.base.fragment.BaseProgressFragment;


/**
 * Created by TranThanhTung on 20/02/2018.
 */

public class FriendsProgressFragment extends BaseProgressFragment {
    @Override
    protected Fragment initPrimaryFragment() {
        return new FriendsFragment();
    }

    @Override
    protected BaseProgressFragmentPresenter initPresenter() {
        return new FriendsProgressPresenter();
    }
}
