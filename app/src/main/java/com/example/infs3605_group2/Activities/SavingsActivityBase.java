package com.example.infs3605_group2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infs3605_group2.Models.User;
import com.example.infs3605_group2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SavingsActivityBase extends AppCompatActivity {
    private TextView name;
    private TextView goal;
    private Button edit;

    private ImageView savePic;
    private String username;
    public static User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings_base);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        edit = findViewById(R.id.button_editGoal);
        name = findViewById(R.id.textView_savingsGoalName);
        goal = findViewById(R.id.textView_savingsGoal);
        savePic = findViewById(R.id.savingsGoalPic);
        savePic.setVisibility(View.INVISIBLE);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("userInfo").child(username);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                currentUser.setUsername(username);
                Picasso.get().load(currentUser.getSavingsGoalPic()).into(savePic);
                savePic.setVisibility(View.VISIBLE);
                name.setText(currentUser.getSavingsName());
                goal.setText(currentUser.getSavingsGoal());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavingsActivityBase.this, SavingsActivity.class);
                intent.putExtra("username", username);
                startActivity (intent);
            }
        });
    }
}
