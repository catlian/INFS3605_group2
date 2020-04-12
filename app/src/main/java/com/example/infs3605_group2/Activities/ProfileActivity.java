package com.example.infs3605_group2.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infs3605_group2.Models.User;
import com.example.infs3605_group2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private static final int IMAGE_REQUEST = 2;
    private Button upload;
    private Uri imageUri;
    private ImageView savePic;
    public static User currentUser;
    private TextView txtview;
    private DatabaseReference pictureRef;
    private String pic1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        upload = findViewById(R.id.button_setPic);
        savePic = findViewById(R.id.imageView_profilePic);
        txtview = findViewById(R.id.textView_setPic);
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        pictureRef = database1.getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("profileImage");
        pictureRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pic1 = dataSnapshot.getValue(String.class);
                Picasso.get().load(pic1).into(savePic);
                if (pic1.equals("https://firebasestorage.googleapis.com/v0/b/infs3605-test-c0bd9.appspot.com/o/uploads%2Findex.png?alt=media&token=18987ce5-e0a1-4a10-b249-aad283bdf8e0")){
                    txtview.setText("Want to change your profile icon?");
                    upload.setText("Change Picture");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
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
                            FirebaseDatabase.getInstance().getReference().child("userInfo").child(LoginActivity1.currentUser.getUsername()).child("profileImage").setValue(urL);
                            Log.d("downloadUri", urL);
                            pd.dismiss();
                            Toast.makeText(ProfileActivity.this, "image upload successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProfileActivity.this, ChildActivity.class);
                            startActivity (intent);
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
