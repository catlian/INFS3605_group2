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
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.infs3605_group2.Fragments.ChildChores;
import com.example.infs3605_group2.Fragments.ChildLanding;
import com.example.infs3605_group2.Fragments.ChildLearningMain;
import com.example.infs3605_group2.Fragments.ChildLog;
import com.example.infs3605_group2.Fragments.ParentSavings;
import com.example.infs3605_group2.Fragments.QuizSettingFragment;
import com.example.infs3605_group2.Fragments.YoutubeFragment;
import com.example.infs3605_group2.Fragments.YoutubeRecycler;
import com.example.infs3605_group2.Fragments.YoutubeVideoAdapter;
import com.example.infs3605_group2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChildActivity extends AppCompatActivity  {

    private Button btnLanding, btnLog, btnChore, btnSavings, btnVideos;
    private static ChildActivity mInstance;


    public static void onFragmentInteraction(String string) {
        Bundle bundle = new Bundle();
        bundle.putString("VIDEO_ID", string);
        YoutubeFragment youtubeFragment = new YoutubeFragment();
        youtubeFragment.setArguments(bundle);

        swapFragment(youtubeFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
//        Bundle bundle = getIntent().getExtras();
//        String message = bundle.getString("userType");
        mInstance = this;
        final ChildLanding landingFrag = new ChildLanding();

        swapFragment(landingFrag);
        addNotification("login");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference choresRef = database.getReference().child("chores").
                child(LoginActivity1.currentUser.getUsername());
        choresRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addNotification("chore");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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

        DatabaseReference transactionRef = database.getReference().child("transactions").
                child(LoginActivity1.currentUser.getUsername());
        transactionRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addNotification("transaction");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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

        //setting navigation items destination paths when clicked
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.homeButton:
                        swapFragment(landingFrag);
                        break;
                    case R.id.saveButton:
                        ParentSavings savings = new ParentSavings();
                        swapFragment(savings);
                        break;
                    case R.id.choresButton:
                        ChildChores choreFragment = new ChildChores();
                        swapFragment(choreFragment);
                        break;
                    case R.id.gamesButton:
                        QuizSettingFragment quizSettingFragment = new QuizSettingFragment();
                        swapFragment(quizSettingFragment);
                        break;
                    case R.id.learnButton:
                        ChildLearningMain childLearningMain = new ChildLearningMain();
                        swapFragment(childLearningMain);
                        break;
                }
                return true;
            }
        });
    }
    private static void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = mInstance.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_slot, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }
    //https://developer.android.com/training/notify-user/build-notification
    public void addNotification(String type) {
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
        if(type.equals("chore")){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
                    .setSmallIcon(R.drawable.kangarootwo)
                    .setContentTitle("New Chore")
                    .setContentText(LoginActivity1.currentUser.getLinkedAccount() + " has added a new chore!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            notificationManager.notify(1 /* ID of notification */, builder.build());
        }
        if(type.equals("login")){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
                    .setSmallIcon(R.drawable.kangarootwo)
                    .setContentTitle("You have just logged in")
                    .setContentText("Hi " + LoginActivity1.currentUser.getUsername() + ", please be careful of any strangers around you when using this app, stay safe!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            notificationManager.notify(2 /* ID of notification */, builder.build());
        }
        else if(type.equals("transaction")){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
                    .setSmallIcon(R.drawable.kangarootwo)
                    .setContentTitle("Balance Change")
                    .setContentText("Your balance has changed!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            notificationManager.notify(3 /* ID of notification */, builder.build());
        }

    }
}
