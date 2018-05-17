package com.kidd.chat.presenters.main.friends;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kidd.chat.common.Constants;
import com.kidd.chat.common.utils.UserAuth;
import com.kidd.chat.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FriendsInteractorImpl implements FriendsInteractor {
    private Context context;

    public FriendsInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void onViewDestroy() {
        this.context = null;
    }

    @Override
    public void getStateFriendOnline(OnGetFriendsStateCompleteListener listener) {
        FirebaseFirestore.getInstance().collection(Constants.USERS_COLLECTION)
                .addSnapshotListener((documentSnapshots, e) -> {
                    if (e != null) {
                        Log.i("hoho", e.toString());
                        return;
                    }
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                        switch (documentChange.getType()) {
                            case ADDED: {

                                Log.i("ABC", "getStateFriendOnline: ");
                                break;
                            }
                            case MODIFIED: {
                                listener.onGetFriendState(documentChange.getDocument().toObject(User.class));
                                break;
                            }
                            default: {
                                Log.i("ABC", "getStateFriendOnline: ");
                                break;
                            }
                        }
                    }
                });
    }

    @Override
    public void getFriends(int pageIndex, int pageSize, OnGetFriendsCompleteListener listener) {
        FirebaseFirestore.getInstance().collection(Constants.USERS_COLLECTION)
                .orderBy(User.FULL_NAME)
                .orderBy(User.EMAIL)
                .startAt(pageIndex * pageSize)
                .limit(pageSize)
                .get()
                .addOnSuccessListener(documentSnapshots -> {
                    List<User> users = documentSnapshots.toObjects(User.class);
                    listener.onGetFriendsSuccess(users);
                })
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
    }

    @Override
    public void getRoomFriendID(User user, OnGetIDroomCompleteListener listener) {

        FirebaseFirestore.getInstance().collection(Constants.ROOM_ID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        String roomID = null;
                        for (DocumentSnapshot documentChange : documentSnapshots) {
//                            switch (documentChange.getType()) {
//                                case ADDED: {
                            Log.i("CCC", documentChange.getId());
                            if (checkRoomID(documentChange.getString("user1"), documentChange.getString("user2"), user.getEmail())) {
                                roomID = documentChange.getId();
                                Log.i("roomID", documentChange.getId());
                            }
                        }
                        listener.onGetIDRoomSuccess(roomID);

//                                break;
//                                case MODIFIED: {
//
//                                }
//                                break;
//
//                                default: {
//                                    break;
//                                }
//                            }
                        //    }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onError(e.toString());
                    }
                });
    }

    @Override
    public void addRoomFriendID(User user, OnGetIDroomCompleteListener listener) {
        Map<String, Object> room = new HashMap<>();
        room.put("user1", UserAuth.getUserID(context));
        room.put("user2", user.getEmail());
        FirebaseFirestore.getInstance().collection(Constants.ROOM_ID)
                .add(room)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        listener.onGetIDRoomSuccess(documentReference.getId());
                        Log.i("roomIDADD", documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onError(e.toString());
                    }
                });
    }

    public boolean checkRoomID(String user1, String user2, String emailFriend) {
        if ((UserAuth.getUserID(context).equals(user1) && emailFriend.equals(user2)) || (UserAuth.getUserID(context).equals(user2) && emailFriend.equals(user1))) {
            return true;
        }
        return false;
    }
}
