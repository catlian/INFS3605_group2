package com.example.infs3605_group2.Fragments;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605_group2.Activities.ChildActivity;
import com.example.infs3605_group2.Activities.LoginActivity1;
import com.example.infs3605_group2.Models.Chore;
import com.example.infs3605_group2.Models.Transaction;
import com.example.infs3605_group2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ChildNewChoreAdapter extends RecyclerView.Adapter<ChildNewChoreAdapter.ChoreViewHolder>{
    private List<Chore> choresToAdapt;

//    public void setData(List<Chore> choresToAdapt) {
//        this.choresToAdapt = choresToAdapt;
//    }
    public ChildNewChoreAdapter(List<Chore> choresToAdapt) {
        this.choresToAdapt = choresToAdapt;
    }

    @NonNull
    @Override
    public ChoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.child_newchore_layout, parent, false);

        ChoreViewHolder choreViewHolder = new ChoreViewHolder(view);

        return choreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChoreViewHolder holder, int position) {
        final Chore choreAtPosition = choresToAdapt.get(position);
        holder.bind(choreAtPosition);
    }

    @Override
    public int getItemCount() {
        return choresToAdapt.size();
    }


    public static class ChoreViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView txtDescription;
        public ImageView icon;
        public TextView txtAmount;
        public Button button;
        public LinearLayout linearLayout;
        public String iconNum;
        public String wash = "https://firebasestorage.googleapis.com/v0/b/infs3605-test-c0bd9.appspot.com/o/icons%2F17.png?alt=media&token=584c33fe-b090-4493-9e2e-444b523d01ac";
        public String bed ="https://firebasestorage.googleapis.com/v0/b/infs3605-test-c0bd9.appspot.com/o/icons%2F19.png?alt=media&token=f2269ec3-a3a7-4bb9-803b-9fc82ec7c2ef";
        public String cleaning = "https://firebasestorage.googleapis.com/v0/b/infs3605-test-c0bd9.appspot.com/o/icons%2F18.png?alt=media&token=f852be88-61f0-4617-805e-f914d83e871e";
        public String garden = "https://firebasestorage.googleapis.com/v0/b/infs3605-test-c0bd9.appspot.com/o/icons%2F25.png?alt=media&token=21438391-fe91-45b8-b68e-45b65405d2e8";
        public String study = "https://firebasestorage.googleapis.com/v0/b/infs3605-test-c0bd9.appspot.com/o/icons%2F16.png?alt=media&token=bea5896f-7ad6-4763-9310-d03b930e70ae";
        public String trash = "https://firebasestorage.googleapis.com/v0/b/infs3605-test-c0bd9.appspot.com/o/icons%2F23.png?alt=media&token=447d80e4-f230-4372-b8fe-63ffab49f732";
        // This constructor is used in onCreateViewHolder
        public ChoreViewHolder(View v) {
            super(v);  // runs the constructor for the ViewHolder superclass
            view = v;
            txtDescription = v.findViewById(R.id.txtDescription);
            txtAmount = v.findViewById(R.id.txtAmount);
            icon = v.findViewById(R.id.imageView);
            button = v.findViewById(R.id.button);
            linearLayout = v.findViewById(R.id.linearLayout);
        }

        // See comment in onBindViewHolder
        public void bind(final Chore chore) {
            txtDescription.setText(chore.getDescription());
            txtAmount.setText("$" + chore.getAmount() + "0");
            //set icon
            iconNum = chore.getIcon();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference notDoneRef = database.getReference("/chores/" +
                            LoginActivity1.currentUser.getUsername() + "/" + chore.getKey());
                    notDoneRef.setValue(chore.toMap());
                }
            });
            switch (iconNum) {
                case "Cleaning":
                    Picasso.get().load(cleaning).into(icon);
                    break;
                case "Making Bed":
                    Picasso.get().load(bed).into(icon);
                    break;
                case "Washing":
                    Picasso.get().load(wash).into(icon);
                    break;
                case "Gardening":
                    Picasso.get().load(garden).into(icon);
                    break;
                case "Studying":
                    Picasso.get().load(study).into(icon);
                    break;
                case "Taking Trash":
                    Picasso.get().load(trash).into(icon);
                    break;
            }

        }
    }

}

