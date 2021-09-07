package com.example.memeshare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {
    private List<SavedPOJO>notes = new ArrayList<>();

    @NonNull
    @Override
    public SavedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_layout,parent,false);
        return new ViewHolder(v);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(SavedAdapter.ViewHolder holder, int position) {
        SavedPOJO model = notes.get(position);
        holder.share.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");i.putExtra(Intent.EXTRA_TEXT,"Checkout the meme\n"+model.getLink());
            v.getContext().startActivity(Intent.createChooser(i,"choose an app"));
        });
        Glide.with(holder.memeImg.getContext()).load(model.getLink()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.pb.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.pb.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.memeImg);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNotes(List<SavedPOJO> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public SavedPOJO getNoteAt(int position) {
        return notes.get(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView memeImg;
        private final ImageView share;
        private final ProgressBar pb;
        public ViewHolder(View itemView) {
            super(itemView);
            memeImg = itemView.findViewById(R.id.memeImg);
            share = itemView.findViewById(R.id.share);
            pb = itemView.findViewById(R.id.progressBar);
        }
    }
}
