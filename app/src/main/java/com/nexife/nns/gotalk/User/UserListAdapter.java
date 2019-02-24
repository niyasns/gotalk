package com.nexife.nns.gotalk.User;

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

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

    ArrayList<UserObject> userList;
    public UserListAdapter(ArrayList<UserObject> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        UserListViewHolder userListViewHolder = new UserListViewHolder(layoutView);
        return userListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder userListViewHolder, final int position) {
        userListViewHolder.mName.setText(userList.get(position).getName());
        userListViewHolder.mPhone.setText(userList.get(position).getPhone());
        userListViewHolder.mUserListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();

                FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid())
                        .child("chat").child(key).setValue(true);
                FirebaseDatabase.getInstance().getReference().child("user").child(userList.get(position).getUid())
                        .child("chat").child(key).setValue(true);

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserListViewHolder extends RecyclerView.ViewHolder {

        public TextView mName, mPhone;
        public LinearLayout mUserListItem;
        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.nameTxtView);
            mPhone = itemView.findViewById(R.id.phoneTxtView);
            mUserListItem = itemView.findViewById(R.id.userListItem);
        }
    }
}
