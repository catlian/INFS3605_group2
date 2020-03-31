package com.example.infs3605_group2.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605_group2.Activities.LoginActivity1;
import com.example.infs3605_group2.Models.Chore;
import com.example.infs3605_group2.Models.Transaction;
import com.example.infs3605_group2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParentChoreAdapter extends RecyclerView.Adapter<ParentChoreAdapter.ChoreViewHolder>{
    private List<Chore> choresToAdapt;

//    public void setData(List<Chore> choresToAdapt) {
//        this.choresToAdapt = choresToAdapt;
//    }
    public ParentChoreAdapter(List<Chore> choresToAdapt) {
        this.choresToAdapt = choresToAdapt;
    }

    @NonNull
    @Override
    public ChoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.parent_chore_layout, parent, false);

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
        public TextView txtChore;
        public TextView txtAmount;
        public LinearLayout linearLayout;

        // This constructor is used in onCreateViewHolder
        public ChoreViewHolder(View v) {
            super(v);  // runs the constructor for the ViewHolder superclass
            view = v;
            txtChore = v.findViewById(R.id.txtChore);
            txtAmount = v.findViewById(R.id.txtAmount);
            linearLayout = v.findViewById(R.id.linearLayout);

//            bookmarkImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(isBookmarked) {
//                        bookmarkImageView.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
//                    } else {
//                        bookmarkImageView.setImageResource(R.drawable.ic_bookmark_black_24dp);
//                    }
//                    isBookmarked = !isBookmarked;
//                }
//            });

        }

        // See comment in onBindViewHolder
        public void bind(final Chore chore) {
            txtChore.setText(chore.getDescription());
            txtAmount.setText(String.valueOf(chore.getAmount()));
            if(chore.getIsDone().equals("false")){
                linearLayout.setBackgroundColor(Color.GRAY);
            }
            else{
                linearLayout.setBackgroundColor(Color.GREEN);
                view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        createDialog(chore);
                    }
                });
            }
        }
        private void createDialog(final Chore chore){
            AppCompatActivity activity = (AppCompatActivity)view.getContext();
            LayoutInflater factory = LayoutInflater.from(activity);
            final View transactionDialogView = factory.inflate(R.layout.parent_transaction_dialog, null);
            final AlertDialog transactionDialog = new AlertDialog.Builder(activity).create();
            transactionDialog.setView(transactionDialogView);
            TextView txtDesc = transactionDialogView.findViewById(R.id.txtDescription);
            txtDesc.setText(chore.getDescription());
            TextView txtAmount = transactionDialogView.findViewById(R.id.txtAmount);
            txtAmount.setText(String.valueOf(chore.getAmount()));

            transactionDialogView.findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference notDoneRef = database.getReference().child("chores").
                            child(LoginActivity1.currentUser.getLinkedAccount()).child(chore.getKey());
                    notDoneRef.removeValue();

                    DatabaseReference transactionRef = database.getReference().child("transactions").
                            child(LoginActivity1.currentUser.getLinkedAccount());

                    Transaction transaction = new Transaction();
                    transaction.setDescription(chore.getDescription());
                    transaction.setEvent("+$" + chore.getAmount());
                    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy");
                    transaction.setDate(dateTimeFormat.format(LocalDateTime.now()));
                    transaction.setTime(-1 * new Date().getTime());
                    transactionRef.push().setValue(transaction);

                    transactionDialog.dismiss();
                }
            });
            transactionDialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    transactionDialog.dismiss();
                }
            });

            transactionDialog.show();
        }
    }
}

