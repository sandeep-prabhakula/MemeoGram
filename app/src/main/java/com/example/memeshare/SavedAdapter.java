package com.example.memeshare;

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

import java.util.List;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {
    private final List<SavedMemes>list;
    public SavedAdapter(List<SavedMemes> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SavedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SavedAdapter.ViewHolder holder, int position) {
        SavedMemes model = list.get(position);
        Glide.with(holder.memeImg.getContext()).load(model.getImageURL()).listener(new RequestListener<Drawable>() {
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
        holder.share.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");i.putExtra(Intent.EXTRA_TEXT,"Checkout the meme\n"+model.getImageURL());
            v.getContext().startActivity(Intent.createChooser(i,"choose an app"));
        });
        holder.save.setOnClickListener(v -> {
            MyDbHandler dbHandler = new MyDbHandler(v.getContext());
            dbHandler.deleteTodo(model.getId());
            v.getContext().startActivity(new Intent(v.getContext(),Saved.class));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView memeImg;
        private final ImageView share;
        private final ProgressBar pb;
        private final ImageView save;
        public ViewHolder(View itemView) {
            super(itemView);
            memeImg = itemView.findViewById(R.id.memeImg);
            share = itemView.findViewById(R.id.share);
            pb = itemView.findViewById(R.id.progressBar);
            save = itemView.findViewById(R.id.save);
        }
    }
}
