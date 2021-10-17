package com.example.memeshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> {
    private List<ExploreModel> list;
    public ExploreAdapter(List<ExploreModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExploreModel model = list.get(position);
        Glide.with(holder.meme.getContext()).load(model.getImageURL()).into(holder.meme);
//        holder.singleMeme.setOnClickListener(v->{
//            Bundle bundle = new Bundle();
//            bundle.putString("imgURL",model.getImageURL());
//            bundle.putString("description",model.getDescription());
//            FragmentTransaction menuHome = ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction();
//            menuHome.replace(R.id.frameLayout,MainFragment.newInstance());
//            menuHome.commit();
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView meme;
        private final ConstraintLayout singleMeme;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            meme = itemView.findViewById(R.id.exploreMeme);
            singleMeme = itemView.findViewById(R.id.singleExploreMeme);
        }
    }
}