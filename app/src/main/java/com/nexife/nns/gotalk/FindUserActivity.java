package com.nexife.nns.gotalk;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nexife.nns.gotalk.User.UserListAdapter;
import com.nexife.nns.gotalk.User.UserObject;
import com.nexife.nns.gotalk.Utils.CountryToPhonePrefix;

import java.util.ArrayList;

public class FindUserActivity extends AppCompatActivity {

    private static final String TAG = "FindUserActivity";
    private RecyclerView mUserListView;
    private RecyclerView.Adapter mUserListAdapter;
    private RecyclerView.LayoutManager mUserListLayoutManager;
    ArrayList<UserObject> userList, contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

        userList = new ArrayList<>();
        contactList = new ArrayList<>();

        getContactList();
        initializeRecyclerView();
    }

    private void getContactList() {
        String ISOPrefix = getCountryISO();
        Cursor phones = getContentResolver()
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            phone = phone.replace(" ", "");
            phone = phone.replace("-", "");
            phone = phone.replace("(", "");
            phone = phone.replace(")", "");

            if(!String.valueOf(phone.charAt(0)).equals("+")) {
                phone = ISOPrefix + phone;
            }
            UserObject mContact = new UserObject("", name, phone);
            contactList.add(mContact);
            getUserDetails(mContact);
        }
    }

    private void getUserDetails(UserObject mContact) {
        DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("user");
        Query userQuery = mUserDB.orderByChild("phone").equalTo(mContact.getPhone());
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String phone = "",
                            name = "";
                    for(DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        if(userSnapshot.child("phone").getValue() != null) {
                            phone = userSnapshot.child("phone").getValue().toString();
                        }
                        if(userSnapshot.child("name").getValue() != null) {
                            name = userSnapshot.child("name").getValue().toString();
                        }
                        UserObject mUser = new UserObject(userSnapshot.getKey(), name, phone);
                        if (name.equals(phone)) {
                            for (UserObject mContactIterator : contactList) {
                                if (mContactIterator.getPhone().equals(mUser.getPhone())) {
                                    mUser.setName(mContactIterator.getName());
                                }
                            }
                        }
                        userList.add(mUser);
                        mUserListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getCountryISO() {
        String iso = null;
        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext()
                .getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        if (telephonyManager.getNetworkCountryIso() != null) {
            if(telephonyManager.getNetworkCountryIso().equals("")) {
                iso = telephonyManager.getNetworkCountryIso();
            }
        }
        String locale = getApplicationContext().getResources().getConfiguration().locale.getCountry();
        Log.d(TAG, locale);
        return CountryToPhonePrefix.getPhone(locale);
    }

    private void initializeRecyclerView() {
        mUserListView = findViewById(R.id.userListView);
        mUserListView.setNestedScrollingEnabled(false);
        mUserListView.setHasFixedSize(false);
        mUserListLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mUserListView.setLayoutManager(mUserListLayoutManager);

        mUserListAdapter = new UserListAdapter(userList);
        mUserListView.setAdapter(mUserListAdapter);
    }
}
