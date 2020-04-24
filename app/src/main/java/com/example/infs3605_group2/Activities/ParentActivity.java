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
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

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
        String id = "";
        NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            id = "dollaroo_channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(id, "DollarooChannel", importance);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
                .setSmallIcon(R.drawable.kangarootwo)
                .setContentTitle("Chore Completed")
                .setContentText(LoginActivity1.currentUser.getLinkedAccount() + " has marked a chore as done." +
                        "Check to see if it is!")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationManager.notify(1, builder.build());
        Toast.makeText(this, LoginActivity1.currentUser.getLinkedAccount() + " has marked a chore as done." +
                        "Check to see if it is!",
                Toast.LENGTH_LONG).show();
    }
}
