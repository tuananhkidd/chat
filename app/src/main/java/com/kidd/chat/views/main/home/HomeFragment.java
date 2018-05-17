package com.kidd.chat.views.main.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.kidd.chat.R;
import com.kidd.chat.presenters.BasePresenter;
import com.kidd.chat.views.base.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TranThanhTung on 19/02/2018.
 */

public class HomeFragment extends BaseFragment implements HomeFragmentView {
    @BindView(R.id.rc_messages)
    RecyclerView rcMessages;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initVariables(Bundle saveInstanceState, View rootView) {
        ButterKnife.bind(this,rootView);

    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
