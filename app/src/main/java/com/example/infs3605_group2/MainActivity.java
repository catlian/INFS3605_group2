package com.example.infs3605_group2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.infs3605_group2.Models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/userInfo/username2");

        User user2 = new User();
        user2.setBalance(200);
        myRef.setValue(user2);

    }
}
