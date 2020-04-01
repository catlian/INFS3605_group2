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

public class RegisterActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button register;
    private FirebaseAuth auth;
    private EditText linkedAcc;
    private EditText balance;
    private EditText userType;
    private EditText profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.username_register);
        password = (EditText) findViewById(R.id.password_register);
        register = (Button) findViewById(R.id.button_register);
        linkedAcc = (EditText) findViewById(R.id.linkedAccount_register);
        balance = (EditText) findViewById(R.id.balance_register);
        userType = (EditText) findViewById(R.id.userType_register);

        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_text = username.getText().toString();
                String pass_text = password.getText().toString();
                String linkedAcc_text = linkedAcc.getText().toString();
                double balance_text = Double.parseDouble(balance.getText().toString());
                String userType_text = userType.getText().toString();

                if (TextUtils.isEmpty(user_text) || TextUtils.isEmpty(pass_text)){
                    Toast.makeText(RegisterActivity.this, "Missing Value", Toast.LENGTH_SHORT).show();
                }else if (pass_text.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password needs to be at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("password", pass_text);
                    map.put("linkedAccount", linkedAcc_text);
                    map.put("userType", userType_text);
                    map.put("balance", balance_text);
                    map.put("savingsGoal", 0);
                    //FirebaseDatabase.getInstance().getReference().child("userInfo").child(user_text);
                    FirebaseDatabase.getInstance().getReference().child("userInfo").child(user_text).updateChildren(map);
                    registerUser(user_text , pass_text);
                }
            }
        });
    }
    private void registerUser (String username, String password){
        auth.createUserWithEmailAndPassword(username + "@gmail.com", password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                    startActivity (new Intent(RegisterActivity.this, LoginActivity1.class));
                    finish();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
