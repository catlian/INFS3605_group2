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

public class RegisterActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button register;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.username_register);
        password = (EditText) findViewById(R.id.password_register);
        register = (Button) findViewById(R.id.button_register);
        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_text = username.getText().toString();
                String pass_text = password.getText().toString();
                if (TextUtils.isEmpty(user_text) || TextUtils.isEmpty(pass_text)){
                    Toast.makeText(RegisterActivity.this, "Missing Value", Toast.LENGTH_SHORT).show();
                }else if (pass_text.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password needs to be at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(user_text,pass_text);
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
                    startActivity (new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
