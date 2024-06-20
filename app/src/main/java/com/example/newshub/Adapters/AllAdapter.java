package com.example.newshub.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newshub.Models.NewsPaper;
import com.example.newshub.NewsActivity;
import com.example.newshub.R;

import java.util.ArrayList;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.ViewHolder> {
    Context context;
    ArrayList<NewsPaper>list;
    public AllAdapter(Context context, ArrayList<NewsPaper> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.news_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsPaper newsPaper = list.get(position);
        holder.imageView.setImageResource(newsPaper.getImageI());
        holder.textView.setText(newsPaper.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newsIntent = new Intent(context, NewsActivity.class);
                newsIntent.putExtra("link",newsPaper.getLink());
                context.startActivity(newsIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_news);
            textView = itemView.findViewById(R.id.news_name);
        }
    }
}
