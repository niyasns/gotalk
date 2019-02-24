package com.nexife.nns.gotalk.Chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.nexife.nns.gotalk.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    ArrayList<MessageObject> messageList;
    public MessageAdapter(ArrayList<MessageObject> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        MessageViewHolder messageListViewHolder = new MessageViewHolder(layoutView);
        return messageListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageListViewHolder, final int position) {
        messageListViewHolder.mMessage.setText(messageList.get(position).getMessageText());
        messageListViewHolder.mSender.setText(messageList.get(position).getSenderId());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView mMessage, mSender;

        //        public LinearLayout mMessageItem;
        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mMessage = itemView.findViewById(R.id.messageTxtView);
            mSender = itemView.findViewById(R.id.senderTxtView);
            //mMessageItem = itemView.findViewById(R.id.MessageItem);
        }
    }
}
