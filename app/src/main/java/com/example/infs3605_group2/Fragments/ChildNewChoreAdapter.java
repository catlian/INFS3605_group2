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
            txtAmount.setText(String.valueOf(chore.getAmount()));
            //set icon
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference notDoneRef = database.getReference("/chores/" +
                            LoginActivity1.currentUser.getUsername() + "/" + chore.getKey());
                    notDoneRef.setValue(chore.toMap());
                }
            });
        }
    }

}

