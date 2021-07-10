package com.example.memeshare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ExploreAdapter extends ArrayAdapter<ExploreModel> {
    private final List<ExploreModel> list;
    public ExploreAdapter (Context context, ArrayList<ExploreModel>list){
        super(context,0,list);
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ExploreModel model = list.get(position);
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.explore_layout,parent,false);
        ImageView exploreMeme = listItemView.findViewById(R.id.exploreMeme);
        ConstraintLayout singleExploreMeme = listItemView.findViewById(R.id.singleExploreMeme);
        singleExploreMeme.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(),ExploreRecycler.class);
            i.putExtra("imageURL",model.getImageURL());
            i.putExtra("description",model.getDescription());
            v.getContext().startActivity(i);
        });
        Glide.with(getContext()).load(model.getImageURL()).into(exploreMeme);
        return listItemView;
    }
}
