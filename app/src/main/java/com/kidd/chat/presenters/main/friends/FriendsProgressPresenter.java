package com.kidd.chat.presenters.main.friends;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kidd.chat.common.Constants;
import com.kidd.chat.models.User;
import com.kidd.chat.presenters.base_progress_fragment.BaseProgressFragmentPresenter;

import java.util.ArrayList;
import java.util.List;


public class FriendsProgressPresenter extends BaseProgressFragmentPresenter {

    @Override
    public void onViewDestroy() {

    }

    @Override
    public void fetchData(OnFetchDataProgressListener listener) {
        FirebaseFirestore.getInstance().collection(Constants.USERS_COLLECTION)
                .get()
                .addOnSuccessListener(documentSnapshots -> {
                    List<User> users = documentSnapshots.toObjects(User.class);
                    for (int i = 0; i <users.size() ; i++) {
                        Log.i("ABC", "getFriends: "+ users.get(i));
                    }

                    Bundle args = new Bundle();
                    args.putParcelableArrayList(Constants.KEY_USERS, (ArrayList<? extends Parcelable>) users);
                    listener.onFetchDataSuccess(args);
                })
                .addOnFailureListener(e -> listener.onFetchDataFailure(e.getMessage()));
    }
}
