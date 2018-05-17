package com.kidd.chat.views.main.friends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import com.kidd.chat.R;
import com.kidd.chat.common.Constants;
import com.kidd.chat.common.adapter.recycler_view_adapter.ListFriendsAdapter;
import com.kidd.chat.common.recycler_view_adapter.EndlessLoadingRecyclerViewAdapter;
import com.kidd.chat.common.recycler_view_adapter.RecyclerViewAdapter;
import com.kidd.chat.common.utils.UserAuth;
import com.kidd.chat.models.User;
import com.kidd.chat.presenters.main.friends.FriendsPresenter;
import com.kidd.chat.presenters.main.friends.FriendsPresenterImpl;
import com.kidd.chat.views.base.fragment.BaseFragment;
import com.kidd.chat.views.chat.ChatActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TranThanhTung on 19/02/2018.
 */

public class FriendsFragment extends BaseFragment<FriendsPresenter> implements FriendsView, SwipeRefreshLayout.OnRefreshListener
        , EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener
        , RecyclerViewAdapter.OnItemClickListener {
    @BindView(R.id.rc_friends)
    RecyclerView rcFriends;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private ListFriendsAdapter listFriendsAdapter;

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().checkFriendsOnline();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_friends;
    }

    @Override
    protected void initVariables(Bundle saveInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
    }
    @Override
    protected void initData(Bundle saveInstanceState) {
        Context context = getActivity();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorPrimary, R.color.colorPrimaryDark);

        listFriendsAdapter = new ListFriendsAdapter(context);
        listFriendsAdapter.addOnItemClickListener(this);
        listFriendsAdapter.setLoadingMoreListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rcFriends.addItemDecoration(new DividerItemDecoration(context, linearLayoutManager.getOrientation()));
        rcFriends.setLayoutManager(linearLayoutManager);
        rcFriends.setAdapter(listFriendsAdapter);

        Bundle args = getArguments();
        if (args == null) {
            return;
        }

        List<User> users = args.getParcelableArrayList(Constants.KEY_USERS);
        if (users == null) {
            getPresenter().refreshFriends();
        } else {
            String ten= UserAuth.getUserID(context);
            for (int i=0; i< users.size(); i++) {
                if(users.get(i).getEmail().equals(UserAuth.getUserID(context))){
                    users.remove(i);
                }
            }

            listFriendsAdapter.addModels(users, false);
        }
    }

    @Override
    protected FriendsPresenter initPresenter() {
        return new FriendsPresenterImpl(getActivity(), this);
    }

    @Override
    public void onRefresh() {
        getPresenter().refreshFriends();
    }

    @Override
    public void onLoadMore() {
        getPresenter().loadMoreFriends();
    }

    @Override
    public void showRefreshingProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshingProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadingMoreProgress() {
        listFriendsAdapter.showLoadingItem(true);
    }

    @Override
    public void hideLoadingMoreProgress() {
        listFriendsAdapter.hideLoadingItem();
    }

    @Override
    public void enableLoadingMore(boolean enable) {
        listFriendsAdapter.enableLoadingMore(enable);
    }

    @Override
    public void enableRefreshing(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }

    @Override
    public void refreshUsers(List<User> users) {
        listFriendsAdapter.refresh(users);
    }

    @Override
    public void addMoreUsers(List<User> users) {
        listFriendsAdapter.addModels(users, false);
    }

    @Override
    public void getRoomID(String id, User user) {
        if(id!=null) {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra(Constants.KEY_USER_FRIEND, (Serializable) user);
            intent.putExtra(Constants.KEY_ROOM_ID, id);
            startActivity(intent);
        } else {
            getPresenter().addRoomID(user);
        }
    }

    @Override
    public void addRoomID(String id, User user) {
        if(id!=null) {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra(Constants.KEY_USER_FRIEND, (Serializable) user);
            intent.putExtra(Constants.KEY_ROOM_ID, id);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Loi xay ra", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void getStateFriends(User user) {
        for (int i = 0; i < listFriendsAdapter.getItemCount(); i++) {
            if(listFriendsAdapter.getItem(i, User.class).getEmail().equals(user.getEmail())){
                listFriendsAdapter.updateModel(i, user);
            }
        }
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {


//        Toast.makeText(getActivity(), "hoho",Toast.LENGTH_LONG).show();
//        Intent intent= new Intent(getActivity(), ChatActivity.class);
//        intent.putExtra(Constants.KEY_USER_FRIEND, listFriendsAdapter.getItem(position, User.class));
//        startActivity(intent);
        getPresenter().getRoomID( listFriendsAdapter.getItem(position, User.class));
    }

}
