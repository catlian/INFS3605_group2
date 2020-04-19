package com.example.infs3605_group2.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.infs3605_group2.Fragments.ChildChores;
import com.example.infs3605_group2.Fragments.ChildLog;
import com.example.infs3605_group2.Fragments.ParentLanding;
import com.example.infs3605_group2.Fragments.ParentOtherTransactions;
import com.example.infs3605_group2.Fragments.ParentLog;
import com.example.infs3605_group2.Fragments.ParentSavings;
import com.example.infs3605_group2.Fragments.ParentTransactionMain;
import com.example.infs3605_group2.Fragments.YoutubeRecycler;
import com.example.infs3605_group2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentActivity extends AppCompatActivity {


//    private Button button;
    private Button btnlog;
    private Button btnChores;
    private Button btnSavings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        Bundle bundle = getIntent().getExtras();
//        String message = bundle.getString("userType");
//        Toast.makeText(ParentActivity.this, message, Toast.LENGTH_SHORT).show();

        ParentLanding landingFrag = new ParentLanding();
        swapFragment(landingFrag);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.parentNavBar);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.parentHomeButton:
                       ParentLanding landing = new ParentLanding();
                       swapFragment(landing);

                        /* ParentTransactionMain landingFrag = new ParentTransactionMain();
                        swapFragment(landingFrag);*/
                        break;
                    case R.id.parentSaveButton:
                        ParentSavings savings = new ParentSavings();
                        swapFragment(savings);
                        break;
                    case R.id.parentChoresButton:
                        ParentTransactionMain choreFragment = new ParentTransactionMain();
                        swapFragment(choreFragment);
                        break;
                }
                return true;
            }
        });


//        button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParentOtherTransactions landingFrag = new ParentOtherTransactions();
//                swapFragment(landingFrag);
//            }
//        });

        /*
        btnlog = findViewById(R.id.button2);
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentLog log = new ParentLog();
                swapFragment(log);
            }
        });
        btnlog = findViewById(R.id.btnChores);
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentTransactionMain parentTransactionMain = new ParentTransactionMain();
                swapFragment(parentTransactionMain);
            }
        });

        btnSavings = findViewById(R.id.btnSavingsGoal);
        btnSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentSavings savings = new ParentSavings();
                swapFragment(savings);
            }
        });*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference choresRef = database.getReference().child("chores").
                child(LoginActivity1.currentUser.getLinkedAccount());
        choresRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addNotification();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_slot, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    //https://developer.android.com/training/notify-user/build-notification
    public void addNotification() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        String id = "";
        NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            id = "dollaroo_channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(id, "DollarooChannel", importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
                .setSmallIcon(R.drawable.kangarootwo)
                .setContentTitle("Chore Completed")
                .setContentText(LoginActivity1.currentUser.getLinkedAccount() + " has marked a chore as done." +
                        "Check to see if it is!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(1 /* ID of notification */, builder.build());
    }
}
