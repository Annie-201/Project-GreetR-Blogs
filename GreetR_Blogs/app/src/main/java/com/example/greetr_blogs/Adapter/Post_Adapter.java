package com.example.greetr_blogs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greetr_blogs.Model.Post_Model;
import com.example.greetr_blogs.R;

import java.util.List;

public class Post_Adapter extends RecyclerView.Adapter<Post_Adapter.MyHolder> {

    Context context;
    List<Post_Model> post_modelList;

    public Post_Adapter(Context context, List<Post_Model> post_modelList) {
        this.context = context;
        this.post_modelList = post_modelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_post, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String title = post_modelList.get(position).getpTitle();
        String description = post_modelList.get(position).getpDescription();
        String image = post_modelList.get(position).getpImage();
        holder.postTitle.setText(title);
        holder.postDescription.setText(description);

        Glide.with(context).load(image).into(holder.postImage);

    }

    @Override
    public int getItemCount() {
        return post_modelList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView postImage;
        TextView postTitle, postDescription;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.postImage);
            postTitle = itemView.findViewById(R.id.postTitle);
            postDescription = itemView.findViewById(R.id.postDescription);
        }
    }
}
