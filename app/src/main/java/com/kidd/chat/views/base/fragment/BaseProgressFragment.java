package com.kidd.chat.views.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kidd.chat.R;
import com.kidd.chat.presenters.base_progress_fragment.BaseProgressFragmentPresenter;
import com.kidd.chat.views.base.LoadingFragment;


public abstract class BaseProgressFragment extends Fragment implements BaseProgressFragmentPresenter.OnFetchDataProgressListener {
    public final String TAG = getClass().getSimpleName();

    private BaseProgressFragmentPresenter presenter;

    protected abstract Fragment initPrimaryFragment();

    protected abstract BaseProgressFragmentPresenter initPresenter();

    protected BaseProgressFragmentPresenter getPresenter() {
        return presenter;
    }

    private LoadingFragment loadingFragment;
    private Fragment primaryFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        primaryFragment = initPrimaryFragment();
        initLoadingFragment();
        presenter = initPresenter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.blank_layout, container, false);
        showFragment(loadingFragment);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPresenter().fetchData(this);
    }

    private void initLoadingFragment() {
        loadingFragment = new LoadingFragment();
        loadingFragment.setOnRetryListener(loadingFragment -> {
            getPresenter().fetchData(this);
        });
    }

    protected void showFragment(Fragment fragment) {
        this.showFragment(fragment, null);
    }

    protected void showFragment(Fragment fragment, Bundle args) {
        if (args != null) {
            fragment.setArguments(args);
        }
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_root, fragment)
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onViewDestroy();
        }
    }

    @Override
    public void onFetchDataStart() {
        loadingFragment.showProgress();
    }

    @Override
    public void onFetchDataSuccess(Bundle args) {
        loadingFragment.hideProgress();
        showFragment(primaryFragment, args);
    }

    @Override
    public void onFetchDataFailure(String message) {
        Log.i(TAG, "onFetchDataFailure: "+message);
        loadingFragment.showError();
    }
}
