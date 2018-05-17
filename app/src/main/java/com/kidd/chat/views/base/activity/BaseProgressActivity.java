package com.kidd.chat.views.base.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.kidd.chat.R;
import com.kidd.chat.presenters.BasePresenter;
import com.kidd.chat.views.base.LoadingFragment;


public abstract class BaseProgressActivity<T extends BasePresenter> extends AppCompatActivity {
    private T presenter;

    protected abstract void initVariables(Bundle savedInstanceState);

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract T initPresenter();

    protected T getPresenter() {
        return presenter;
    }

    protected abstract void onRetry();

    private LoadingFragment loadingFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_layout);
        loadingFragment = new LoadingFragment();
        loadingFragment.setOnRetryListener(loadingFragment -> {
            onRetry();
        });
        showFragment(loadingFragment);

        initVariables(savedInstanceState);
        initData(savedInstanceState);

        this.presenter = initPresenter();
    }

    protected void showFragment(Fragment fragment) {
        this.showFragment(fragment, null);
    }

    protected void showFragment(Fragment fragment, Bundle args) {
        if (args != null) {
            fragment.setArguments(args);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_root, fragment)
                .commit();
    }

    protected void showProgress() {
        loadingFragment.showProgress();
    }

    protected void hideProgress() {
        loadingFragment.hideProgress();
    }

    protected void showError() {
        loadingFragment.showError();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onViewDestroy();
        }
    }
}
