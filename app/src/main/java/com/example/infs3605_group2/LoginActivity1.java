package com.example.infs3605_group2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity1 extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        username = (EditText) findViewById(R.id.username_text);
        password = (EditText) findViewById(R.id.password_text);
        login = (Button) findViewById(R.id.button_login);
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_text = username.getText().toString();
                String password_text = password.getText().toString();
                loginTask (username_text, password_text);
            }
        });

    }

    private void loginTask (String username, String password) {
        auth.signInWithEmailAndPassword(username + "@gmail.com", password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity1.this, "Login Success", Toast.LENGTH_SHORT).show();
                    startActivity (new Intent(LoginActivity1.this, MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity1.this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
