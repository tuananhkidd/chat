package com.kidd.chat.views.base.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kidd.chat.presenters.BasePresenter;


public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    private T presenter;

    protected abstract int getLayoutResources();

    protected abstract void initVariables(Bundle savedInstanceState);

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract T initPresenter();

    protected T getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResources());
        initVariables(savedInstanceState);
        this.presenter = initPresenter();
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null) {
            presenter.onViewDestroy();
        }
    }
}
