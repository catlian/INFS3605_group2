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
    private EditText balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.parent_username_register);
        password = (EditText) findViewById(R.id.parent_Password_register);
        register = (Button) findViewById(R.id.button_register);
        balance = (EditText)findViewById(R.id.balance_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_text = username.getText().toString();
                String pass_text = password.getText().toString();
                String balance_text = balance.getText().toString();
                if (TextUtils.isEmpty(user_text) || TextUtils.isEmpty(pass_text)|| TextUtils.isEmpty(balance_text)){
                    Toast.makeText(RegisterActivity.this, "Missing Value", Toast.LENGTH_SHORT).show();
                }else if (pass_text.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password needs to be at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Please create your child's account", Toast.LENGTH_SHORT).show();
                    Intent register2 = new Intent(RegisterActivity.this, RegisterActivity2.class);
                    register2.putExtra("parent_username", user_text);
                    register2.putExtra("parent_password", pass_text);
                    register2.putExtra("parent_balance", balance_text);
                    startActivity (register2);
                    finish();
                }
            }
        });
    }
}
