package com.kidd.chat.presenters.chat;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.kidd.chat.common.Constants;
import com.kidd.chat.common.utils.UserAuth;
import com.kidd.chat.models.Message;
import com.kidd.chat.models.UserMessage;
import com.kidd.chat.presenters.OnRequestCompleteListener;

/**
 * Created by TranThanhTung on 20/02/2018.
 */

public class ChatInteractorImpl implements ChatInteractor {
    private Context context;
    private String roomID;
    private ListenerRegistration messageChangeListenerRegistration;
    int idx=0;
    public ChatInteractorImpl(Context context, String roomID) {
        this.context = context;
        this.roomID = roomID;
    }

    @Override
        public void onViewDestroy() {
            context = null;
        }

        @Override
        public void sendMessage(String message, OnRequestCompleteListener listener) {
            Message objectMessage = new Message(UserAuth.getUserID(context), message);
            //  objectMessage.setCreatedDate(new Date());

            FirebaseFirestore.getInstance().collection(Constants.COUPLE_ROOMS_COLLECTION)
                    .document(roomID).collection(Constants.MESSAGES_COLLECTIONS)
                    .add(objectMessage)
                    //    .set(messageMap, SetOptions.merge())
   //             .addOnSuccessListener(success -> listener.onRequestSuccess())
                .addOnFailureListener(error -> listener.onRequestError(error.getMessage()));
    }

    @Override
    public void registerOnMessageChangedListener(OnMessageChangedListener listener) {

        messageChangeListenerRegistration = FirebaseFirestore.getInstance().collection(Constants.COUPLE_ROOMS_COLLECTION)
                .document(roomID)
                .collection(Constants.MESSAGES_COLLECTIONS)
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .limit(50)
                .addSnapshotListener((documentSnapshots, e) -> {
                    if (e != null) {
                        Log.i("hoho", e.toString());
                        return;
                    }

                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {

                        switch (documentChange.getType()) {
                            case ADDED:{
                                idx++;
                                UserMessage userMessage=new UserMessage(documentChange.getDocument().getId(),documentChange.getDocument().toObject(Message.class));
                                if(!userMessage.getMessage().getOwner().equals((UserAuth.getUserID(context))) && !userMessage.getMessage().getSeenBy().contains(UserAuth.getUserID(context))){
                                    userMessage.getMessage().getSeenBy().add(UserAuth.getUserID(context));
                                    FirebaseFirestore.getInstance().collection(Constants.COUPLE_ROOMS_COLLECTION)
                                            .document(roomID)
                                            .collection(Constants.MESSAGES_COLLECTIONS)
                                            .document(userMessage.getId())
                                            .update(Message.SEEN_BY, userMessage.getMessage().getSeenBy())
                                            .addOnFailureListener(e1 -> Log.i("seenBy", e1.toString()));
                                }
                                if(idx==documentSnapshots.size() && userMessage.getMessage().getSeenBy().size()>0) {
                                    userMessage.getMessage().setExpanded(true);
                                }
                                listener.onMessageAdded(userMessage);

                            }
                            break;

                            case MODIFIED:{
                                UserMessage userMessage=new UserMessage(documentChange.getDocument().getId(),documentChange.getDocument().toObject(Message.class));
                                listener.onMessageModified(userMessage);
                            }
                            break;

                            default:{
                                break;
                            }
                        }
                    }
                });
    }
    public void unregisterOnMessageChangedListener() {
        messageChangeListenerRegistration.remove();
    }
}
