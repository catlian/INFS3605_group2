package com.example.infs3605_group2.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605_group2.Models.Transaction;
import com.example.infs3605_group2.R;

import java.util.List;

public class ChildLogAdapter extends RecyclerView.Adapter<ChildLogAdapter.LogViewHolder>{
    private List<Transaction> transactionsToAdapt;

    public ChildLogAdapter(List<Transaction> transactionsToAdapt) {
        this.transactionsToAdapt = transactionsToAdapt;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.child_log_layout, parent, false);

        LogViewHolder logViewHolder = new LogViewHolder(view);

        return logViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        final Transaction transactionAtPosition = transactionsToAdapt.get(position);
        holder.bind(transactionAtPosition);
    }

    @Override
    public int getItemCount() {
        return transactionsToAdapt.size();
    }


    public static class LogViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView txtDate;
        public TextView txtDescription;
        public TextView txtAmount;
        public LinearLayout linearLayout;

        // This constructor is used in onCreateViewHolder
        public LogViewHolder(View v) {
            super(v);  // runs the constructor for the ViewHolder superclass
            view = v;
            txtDate = v.findViewById(R.id.txtDate);
            txtDescription = v.findViewById(R.id.txtDescription);
            txtAmount = v.findViewById(R.id.txtAmount);
            linearLayout = v.findViewById(R.id.linearLayout);
        }
        // See comment in onBindViewHolder
        public void bind (final Transaction transaction){
            txtDate.setText(transaction.getDate());
            txtDescription.setText(transaction.getDescription());
            txtAmount.setText(transaction.getEvent() + "0");
        }
    }
}

