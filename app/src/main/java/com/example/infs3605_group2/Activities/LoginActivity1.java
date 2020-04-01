package com.example.infs3605_group2.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.infs3605_group2.Models.User;
import com.example.infs3605_group2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity1 extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login;
    private FirebaseAuth auth;
    private Button register;

    public static User currentUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        username = (EditText) findViewById(R.id.username_text);
        password = (EditText) findViewById(R.id.password_text);
        login = (Button) findViewById(R.id.button_login);
        register = findViewById(R.id.button_register);
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_text = username.getText().toString();
                String password_text = password.getText().toString();
                loginTask (username_text, password_text);
            }
        });

       /* register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

    private void loginTask (final String username, String password) {
        auth.signInWithEmailAndPassword(username + "@gmail.com", password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    setCurrentUserInfo(username);
                    Toast.makeText(LoginActivity1.this, "Login Success", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity1.this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void setCurrentUserInfo(final String username){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("userInfo").child(username);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                currentUser.setUsername(username);
                if (currentUser.getUserType().equals("parent")){
                    Intent intent = new Intent(LoginActivity1.this, ParentActivity.class);
                    startActivity (intent);
//                    finish();
                }
                else{
                    Intent intent = new Intent(LoginActivity1.this, ChildActivity.class);
                    startActivity (intent);
//                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
