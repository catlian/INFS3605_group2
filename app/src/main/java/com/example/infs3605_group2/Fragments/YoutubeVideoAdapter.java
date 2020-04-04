package com.example.infs3605_group2.Fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605_group2.Activities.ChildActivity;
import com.example.infs3605_group2.Models.Constants;
import com.example.infs3605_group2.Models.YoutubeModel;
import com.example.infs3605_group2.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

public class YoutubeVideoAdapter extends RecyclerView.Adapter<YoutubeVideoAdapter.YoutubeViewHolder> {
    private static final String TAG = YoutubeVideoAdapter.class.getSimpleName();
    private static Context context;
    private ArrayList<YoutubeModel> YoutubeModelArrayList;


    public YoutubeVideoAdapter(Context context, ArrayList<YoutubeModel> YoutubeModelArrayList) {
        this.context = context;
        this.YoutubeModelArrayList = YoutubeModelArrayList;
    }

    @NonNull
    @Override
    public YoutubeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                youtube_thumbnail_layout, parent, false);
        YoutubeViewHolder youtubeViewHolder = new YoutubeViewHolder(view);
        return youtubeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeViewHolder holder, final int position) {
        final YoutubeModel youtubeModel = YoutubeModelArrayList.get(position);
        holder.bind(youtubeModel);
    }

    @Override
    public int getItemCount() {
        return YoutubeModelArrayList != null ? YoutubeModelArrayList.size() : 0;
    }

    public static class YoutubeViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public YouTubeThumbnailView videoThumbnailImageView;
        public TextView videoTitle, videoDuration, videoChannel;

        public YoutubeViewHolder(View view) {
            super(view);
            this.view = view;
            videoThumbnailImageView = view.findViewById(R.id.video_thumbnail_image_view);
            videoTitle = view.findViewById(R.id.video_title_label);
            videoDuration = view.findViewById(R.id.video_duration_label);
            videoChannel = view.findViewById(R.id.video_channel);
        }
        public void bind(final YoutubeModel youtubeModel) {
            videoThumbnailImageView.initialize(Constants.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    //when initialization is sucess, set the video id to thumbnail to load
                    youTubeThumbnailLoader.setVideo(youtubeModel.getId());

                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                            //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                            youTubeThumbnailLoader.release();
                        }
                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                            //print or show error when thumbnail load failed
                            Log.e(TAG, "Youtube Thumbnail Error");
                        }
                    });
                }
                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                    //print or show error when initialization failed
                    Log.e(TAG, "Youtube Initialization Failure");
                }
            });


            videoTitle.setText(youtubeModel.getTitle());
            videoDuration.setText(String.valueOf(youtubeModel.getDuration()));
            videoChannel.setText(youtubeModel.getChannel());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChildActivity.onFragmentInteraction(youtubeModel.getId());
                }
            });
        }
        private void swapFragment(Fragment fragment, View view) {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_slot, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
