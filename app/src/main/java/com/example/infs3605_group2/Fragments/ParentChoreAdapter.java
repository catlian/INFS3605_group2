package com.example.infs3605_group2.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605_group2.Models.Chore;
import com.example.infs3605_group2.R;

import java.util.ArrayList;
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
            }

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Context context = view.getContext();
//
//                    Intent intent = new Intent(context, BookDetailActivity.class);
//                    intent.putExtra("isbn", book.getIsbn());
//                    context.startActivity(intent);
//                }
//            });

//            shareImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//
//                    intent.putExtra(Intent.EXTRA_TEXT, book.getTitle());
//                    intent.setType("text/plain");
//                    context.startActivity(intent);
//                }
//            });

//            String imageUrl = book.getBookImage();
//            Glide.with(view.getContext()).load(imageUrl).into(coverImageView);
        }
    }
}

