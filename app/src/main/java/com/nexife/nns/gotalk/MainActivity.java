package com.nexife.nns.gotalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    private EditText mPhoneNumber, Code;
    private Button mSendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        mPhoneNumber = findViewById(R.id.phoneNumber);
        Code = findViewById(R.id.code);
        mSendBtn = findViewById(R.id.sendBtn);
    }
}
