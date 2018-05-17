package com.kidd.chat.views.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kidd.chat.presenters.BasePresenter;


public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    private T presenter;

    protected abstract int getLayoutResource();

    protected abstract void initVariables(Bundle saveInstanceState, View rootView);

    protected abstract void initData(Bundle saveInstanceState);

    protected abstract T initPresenter();

    public T getPresenter() {
        return presenter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResource(), container, false);
        initVariables(savedInstanceState, rootView);
        initData(savedInstanceState);

        presenter = initPresenter();
        return rootView;
    }
}

