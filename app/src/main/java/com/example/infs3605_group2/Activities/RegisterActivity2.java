package com.example.infs3605_group2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.infs3605_group2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity2 extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button register;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        username = (EditText) findViewById(R.id.child_username_register);
        password = (EditText) findViewById(R.id.child_password_register);
        register = (Button) findViewById(R.id.button_register);
        final String parent_username = getIntent().getExtras().getString("parent_username");
        final String parent_password = getIntent().getExtras().getString("parent_password");
        final double balance_text = Double.parseDouble(getIntent().getExtras().getString("parent_balance"));
        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_text = username.getText().toString();
                String pass_text = password.getText().toString();
                if (TextUtils.isEmpty(user_text) || TextUtils.isEmpty(pass_text)){
                    Toast.makeText(RegisterActivity2.this, "Missing Value", Toast.LENGTH_SHORT).show();
                }else if (pass_text.length() < 6) {
                    Toast.makeText(RegisterActivity2.this, "Password needs to be at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else {
                    HashMap<String, Object> child_map = new HashMap<>();
                    HashMap<String, Object> parent_map = new HashMap<>();
                    child_map.put("password", pass_text);
                    child_map.put("linkedAccount", parent_username);
                    child_map.put("userType", "child");
                    child_map.put("balance", 0);
                    child_map.put("savingsGoal", "0");
                    parent_map.put("password", parent_password);
                    parent_map.put("linkedAccount", user_text);
                    parent_map.put("userType", "parent");
                    parent_map.put("balance", balance_text);
                    parent_map.put("savingsGoal", "0");
                    FirebaseDatabase.getInstance().getReference().child("userInfo").child(user_text).updateChildren(child_map);
                    FirebaseDatabase.getInstance().getReference().child("userInfo").child(parent_username).updateChildren(parent_map);
                    registerUser(user_text , pass_text);
                    registerUser(parent_username, parent_password);
                    Toast.makeText(RegisterActivity2.this, "Accounts Created! Please login :)", Toast.LENGTH_SHORT).show();
                     startActivity (new Intent(RegisterActivity2.this, LoginActivity1.class));
                     finish();
                }
            }
        });
    }
    private void registerUser (String username, String password){
        auth.createUserWithEmailAndPassword(username + "@gmail.com", password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity2.this, "Registration Success", Toast.LENGTH_SHORT).show();
                   // startActivity (new Intent(RegisterActivity2.this, LoginActivity1.class));
                   // finish();
                }
                else {
                    Toast.makeText(RegisterActivity2.this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
