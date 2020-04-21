package com.example.infs3605_group2.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605_group2.Models.Chore;
import com.example.infs3605_group2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChildPendingChoreAdapter extends RecyclerView.Adapter<ChildPendingChoreAdapter.ChoreViewHolder>{
    private List<Chore> choresToAdapt;

//    public void setData(List<Chore> choresToAdapt) {
//        this.choresToAdapt = choresToAdapt;
//    }
    public ChildPendingChoreAdapter(List<Chore> choresToAdapt) {
        this.choresToAdapt = choresToAdapt;
    }

    @NonNull
    @Override
    public ChoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.child_pendingchore_layout, parent, false);

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
        public LinearLayout linearLayout;
        public int iconNum;
        public String vacuum ="https://firebasestorage.googleapis.com/v0/b/infs3605-test-c0bd9.appspot.com/o/uploads%2Fvacuuming.png?alt=media&token=366dc917-0ca0-4bed-a28a-6062631013ea";
        public String broom = "https://firebasestorage.googleapis.com/v0/b/infs3605-test-c0bd9.appspot.com/o/uploads%2Fbroomstick.png?alt=media&token=20d78473-fd36-4c94-aef9-c927ab14f74d";
        public String wash = "https://firebasestorage.googleapis.com/v0/b/infs3605-test-c0bd9.appspot.com/o/uploads%2Fdishwashing.png?alt=media&token=acc63636-4bf2-44dc-bd59-689ab3ddc860";
        // This constructor is used in onCreateViewHolder
        public ChoreViewHolder(View v) {
            super(v);  // runs the constructor for the ViewHolder superclass
            view = v;
            txtDescription = v.findViewById(R.id.txtDescription);
            txtAmount = v.findViewById(R.id.txtAmount);
            icon = v.findViewById(R.id.imageView);
            linearLayout = v.findViewById(R.id.linearLayout);

        }

        // See comment in onBindViewHolder
        public void bind(final Chore chore) {
            txtDescription.setText(chore.getDescription());
            txtAmount.setText(String.valueOf(chore.getAmount()));
            //set icon
            iconNum = chore.getIcon();
            switch (iconNum) {
                case 1:
                    Picasso.get().load(vacuum).into(icon);
                    break;
                case 2:
                    Picasso.get().load(broom).into(icon);
                    break;
                case 3:
                    Picasso.get().load(wash).into(icon);
                    break;
            }
        }
    }
}

