package com.example.memeshare;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.ViewHolder> {
    private final List<MemeModel>list;
    public MemeAdapter(List<MemeModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MemeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meme_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MemeAdapter.ViewHolder holder, int position) {
        MemeModel model = list.get(position);
        holder.title.setText(model.getDescription());
        Glide.with(holder.memeImg.getContext()).load(model.getImgurl()).listener(new RequestListener<Drawable>() {
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
            i.setType("text/plain");i.putExtra(Intent.EXTRA_TEXT,"Checkout the meme\n"+model.getImgurl());
            v.getContext().startActivity(Intent.createChooser(i,"choose an app"));
        });
        holder.save.setOnClickListener(v -> {
            SavedPOJO pojo = new SavedPOJO(model.getImgurl());
            try{
                SavedFragment.noteViewModel.insert(pojo);
            }catch(Exception e){
                Log.d("memeogram",e.getMessage());
            }
            holder.save.setImageResource(R.drawable.ic_baseline_bookmark_24);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView memeImg;
        private final ImageView share;
        private final TextView title;
        private final ProgressBar pb;
        private final ImageView save;
        public ViewHolder(View itemView) {
            super(itemView);
            memeImg = itemView.findViewById(R.id.memeImg);
            share = itemView.findViewById(R.id.share);
            title = itemView.findViewById(R.id.meme_description);
            pb = itemView.findViewById(R.id.progressBar);
            save = itemView.findViewById(R.id.save);
        }
    }
}
