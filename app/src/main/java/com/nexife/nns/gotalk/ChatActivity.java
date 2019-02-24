package com.nexife.nns.gotalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nexife.nns.gotalk.Chat.ChatObject;
import com.nexife.nns.gotalk.Chat.MessageAdapter;
import com.nexife.nns.gotalk.Chat.MessageObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mChatView;
    private RecyclerView.Adapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;

    ArrayList<MessageObject> chatActivityList;
    String chatID;
    private String TAG = "ChatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatID = getIntent().getExtras().getString("chatID");

        Button mSend = findViewById(R.id.send_btn);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        chatActivityList = new ArrayList<>();
        initializeRecyclerView();
    }

    private void sendMessage() {
        EditText mMessage = findViewById(R.id.message_edit_text);

        if (!mMessage.getText().toString().isEmpty()) {
            DatabaseReference newMessageDB = FirebaseDatabase.getInstance().getReference().child("chat").child(chatID).push();

            Map newMessageMap = new HashMap<>();
            newMessageMap.put("text", mMessage.getText().toString());
            newMessageMap.put("creator", FirebaseAuth.getInstance().getUid());

            Log.d(TAG, newMessageMap.toString());
            newMessageDB.updateChildren(newMessageMap);
        }

        mMessage.setText(null);
    }

    private void initializeRecyclerView() {
        mChatView = findViewById(R.id.chat_activity_list);
        mChatView.setNestedScrollingEnabled(false);
        mChatView.setHasFixedSize(false);
        mChatLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mChatView.setLayoutManager(mChatLayoutManager);

        mChatAdapter = new MessageAdapter(chatActivityList);
        mChatView.setAdapter(mChatAdapter);
    }
}
