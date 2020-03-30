package com.example.infs3605_group2.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.infs3605_group2.Models.User;
import com.example.infs3605_group2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SavingsActivity extends AppCompatActivity {
    private static final int IMAGE_REQUEST = 2;
    private EditText name;
    private EditText goal;
    private Button upload;
    private Button create;
    private Uri imageUri;
    private ImageView savePic;
    private String username;
    public static User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        upload = findViewById(R.id.button_upload);
        create = findViewById(R.id.button_create);
        name = findViewById(R.id.editText_name);
        goal = findViewById(R.id.editText_goal);
        savePic = findViewById(R.id.imageView_savings);
       // savePic.setVisibility(View.GONE);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("userInfo").child(username);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                currentUser.setUsername(username);
                  Picasso.get().load(currentUser.getSavingsGoalPic()).into(savePic);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_text = name.getText().toString();
                String goal_text = goal.getText().toString();
                if (TextUtils.isEmpty(name_text) || TextUtils.isEmpty(goal_text)) {
                    Toast.makeText(SavingsActivity.this, "Missing Value", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SavingsActivity.this, "Savings goal created, now get saving!", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference().child("userInfo").child(username).child("savingsName").setValue(name_text);
                    FirebaseDatabase.getInstance().getReference().child("userInfo").child(username).child("savingsGoal").setValue(goal_text);
                }
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });
    }
    private void openImage(){
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            uploadImage();
        }
    }

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("uploading...");
        pd.show();
        if (imageUri != null){
            final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads").child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String urL = uri.toString();
                        Picasso.get().load(urL).into(savePic);
                        FirebaseDatabase.getInstance().getReference().child("userInfo").child(username).child("savingsGoalPic").setValue(urL);
                        Log.d("downloadUri", urL);
                        pd.dismiss();
                        Toast.makeText(SavingsActivity.this, "image upload successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        }
    }
    private String getFileExtension (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap =  MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }
}
