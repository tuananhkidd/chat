package com.kidd.chat.views.chat;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kidd.chat.GlideApp;
import com.kidd.chat.R;
import com.kidd.chat.common.Constants;
import com.kidd.chat.common.adapter.recycler_view_adapter.ChatMessageAdapter;
import com.kidd.chat.common.custom_view.ClearableEditText;
import com.kidd.chat.common.recycler_view_adapter.EndlessLoadingRecyclerViewAdapter;
import com.kidd.chat.common.recycler_view_adapter.RecyclerViewAdapter;
import com.kidd.chat.common.utils.UserAuth;
import com.kidd.chat.models.Message;
import com.kidd.chat.models.PageList;
import com.kidd.chat.models.User;
import com.kidd.chat.models.UserMessage;
import com.kidd.chat.presenters.chat.ChatPresenter;
import com.kidd.chat.presenters.chat.ChatPresenterImpl;
import com.kidd.chat.services.FriendStateOnlineEvent;
import com.kidd.chat.views.base.activity.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by TranThanhTung on 20/02/2018.
 */

public class ChatActivity extends BaseActivity<ChatPresenter> implements ChatView, View.OnClickListener,
        RecyclerViewAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener{
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.img_online)
    CircleImageView imgOnline;

    @BindView(R.id.rc_messages)
    RecyclerView rcMessages;
    @BindView(R.id.edt_message)
    ClearableEditText edtMessage;
    @BindView(R.id.btn_emoji)
    ImageButton btnEmoji;
    @BindView(R.id.btn_send)
    ImageButton btnSend;
    @BindView(R.id.ln_input)
    RelativeLayout rlChat;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    ChatMessageAdapter messageAdapter;
    private String roomID;
    private User own, userFriend;
    private List<UserMessage> userMessages;
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        roomID = getIntent().getStringExtra(Constants.KEY_ROOM_ID);
        own = new User();
        userFriend = (User) getIntent().getSerializableExtra(Constants.KEY_USER_FRIEND);

        initToolBar();

    }
    private void initToolBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            initToolBarUserFriend(userFriend);
        }
    }
    private void initToolBarUserFriend(User userFriend){
        GlideApp.with(this)
                .load(userFriend.getAvatarUrl())
                .placeholder(R.drawable.avatar_placeholder)
                .into(imgAvatar);
        txtName.setText(userFriend.getFirstName());

        txtEmail.setText(userFriend.isOnline() ? getString(R.string.toolbal_state_online) : getString(R.string.toolbal_state_offline));
        imgOnline.setVisibility(userFriend.isOnline() ? View.VISIBLE : View.GONE);
    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        getPresenter().registerOnMessageAddedListener();
        userMessages= new ArrayList<>();
        btnSend.setOnClickListener(this);
        btnEmoji.setOnClickListener(this);
        rlChat.setOnClickListener(this);
        edtMessage.setOnClickListener(this);
        messageAdapter = new ChatMessageAdapter(getApplicationContext(), own, userFriend);
        messageAdapter.addOnItemClickListener(this);
        messageAdapter.setLoadingMoreListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcMessages.setLayoutManager(linearLayoutManager);
        rcMessages.addItemDecoration(new DividerItemDecoration(getApplicationContext(), linearLayoutManager.getOrientation()));
        rcMessages.setHasFixedSize(true);
        rcMessages.setAdapter(messageAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting_chat, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
            }
            break;

            case R.id.menu_setting: {

            }
            break;

            default: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().unregisterOnMessageAddedListener();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFriendStateOnlineEvent(FriendStateOnlineEvent friendStateOnlineEvent) {
        if(friendStateOnlineEvent!=null){
            if(friendStateOnlineEvent.getUser()!=null && friendStateOnlineEvent.getUser().getEmail().equals(userFriend.getEmail())) {
                initToolBarUserFriend(friendStateOnlineEvent.getUser());
            }else if(friendStateOnlineEvent.getUser()!=null && friendStateOnlineEvent.getUser().getEmail().equals(UserAuth.getUserID(this))){
                if(!friendStateOnlineEvent.getUser().isOnline()){
                    getPresenter().unregisterOnMessageAddedListener();
                }else{
                    getPresenter().registerOnMessageAddedListener();
                }
            }
        }
    }
    @Override
    protected ChatPresenter initPresenter() {
        return new ChatPresenterImpl(this, this, roomID);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send: {
                getPresenter().validateSendingMessage(edtMessage.getText());
//                Message objectMessage = new Message(UserAuth.getUserID(this), edtMessage.getText());
//                //  objectMessage.setCreatedDate(new Date());
//
//                FirebaseFirestore.getInstance().collection(Constants.COUPLE_ROOMS_COLLECTION)
//                        .document(roomID).collection(Constants.MESSAGES_COLLECTIONS)
//                        .add(objectMessage)
//                        //    .set(messageMap, SetOptions.merge())
//                        //             .addOnSuccessListener(success -> listener.onRequestSuccess())
//                        .addOnFailureListener(this,error -> Log.i("erro","listener"));
            }
            break;

            case R.id.btn_emoji: {

            }
            break;
            case R.id.edt_message:{
                Toast.makeText(this, "123456", Toast.LENGTH_SHORT).show();
           //     goToSeenByState();
            }
            break;
            default: {
                break;
            }
        }
    }
//    private void goToSeenByState() {
//        for (UserMessage userMessage: userMessages) {
//            if(!userMessage.getMessage().getOwner().equals((UserAuth.getUserID(this)))){
//                userMessage.getMessage().getSeenBy().add(UserAuth.getUserID(this));
//                FirebaseFirestore.getInstance().collection(Constants.USERS_COLLECTION)
//                        .document(userMessage.getId())
//                        .update(Message.SEEN_BY, userMessage.getMessage().getSeenBy())
//                        .addOnFailureListener(e -> Log.i("seenBy", e.toString()));
//            }
//        }
//
//    }
    @Override
    public void addMessage(UserMessage userMessag) {
        if (userMessag != null) {
            messageAdapter.addModel(userMessag.getMessage(), true);
        }
        userMessages.add(userMessag);
        edtMessage.setText(null);
    }

    @Override
    public void modifiedMessage(UserMessage userMessage) {
        for (int i = 0; i < userMessages.size(); i++) {
            if(userMessages.get(i).getId().equals(userMessage.getId())){
                messageAdapter.updateModel(i, userMessage.getMessage());
            }
        }
    }


    @Override
    public void onMessageSeen() {

    }

    @Override
    public void showLoadMoreProgress() {
        messageAdapter.showLoadingItem(true);
    }

    @Override
    public void hideLoadMoreProgress() {
        messageAdapter.hideLoadingItem();
    }

    @Override
    public void enableLoadMore(boolean enable) {
        messageAdapter.enableLoadingMore(enable);
    }

    @Override
    public void enableRefreshing(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
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
    public void refreshMessages(PageList<Message> messagePageList) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {

    }
}
