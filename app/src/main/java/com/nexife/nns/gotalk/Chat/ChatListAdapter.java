package com.nexife.nns.gotalk.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.nexife.nns.gotalk.ChatActivity;
import com.nexife.nns.gotalk.R;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {

    ArrayList<ChatObject> chatList;
    public ChatListAdapter(ArrayList<ChatObject> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        ChatListViewHolder chatListViewHolder = new ChatListViewHolder(layoutView);
        return chatListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatListViewHolder chatListViewHolder, final int position) {
        chatListViewHolder.mTitle.setText(chatList.get(position).getChatId());

        chatListViewHolder.mChatListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("chatID", chatList.get(chatListViewHolder.getAdapterPosition()).getChatId());
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle;
        public LinearLayout mChatListItem;
        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.titleTxtView);
            mChatListItem = itemView.findViewById(R.id.chatListItem);
        }
    }
}
