package com.example.memeshare;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{
    List<VideoItems>list;

    public VideoAdapter(List<VideoItems> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.reels_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setVideoData(list.get(position));
        VideoItems model = list.get(position);
        holder.share.setOnClickListener(v->{
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");i.putExtra(Intent.EXTRA_TEXT,"Checkout the video\n"+model.getVideoURL());
            v.getContext().startActivity(Intent.createChooser(i,"choose an app"));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{
        private final VideoView videoView;
        private final ImageView share;
        private final ProgressBar reelsProgressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            share = itemView.findViewById(R.id.shareVideo);
            reelsProgressBar = itemView.findViewById(R.id.reelProgressBar);
        }
        void setVideoData(VideoItems videoItems){
            videoView.setVideoPath(videoItems.videoURL);
            videoView.setOnPreparedListener(mp -> {
                reelsProgressBar.setVisibility(View.GONE);
                mp.start();
            });
            videoView.setOnCompletionListener(MediaPlayer::start);
        }
    }
}
