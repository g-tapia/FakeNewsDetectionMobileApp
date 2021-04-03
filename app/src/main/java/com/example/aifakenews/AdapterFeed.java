package com.example.aifakenews;

import android.content.Context;
import android.security.identity.InvalidRequestMessageException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.MyViewHolder> {

    Context context;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    RequestManager glide;

    public AdapterFeed(Context context,  ArrayList<ModelFeed> modelFeedArrayList) {

        this.context = context;
        this.modelFeedArrayList = modelFeedArrayList;
        glide = Glide.with(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final ModelFeed modelFeed = modelFeedArrayList.get(position);

        holder.tv_name.setText(modelFeed.getName());
        holder.tv_likes.setText(String.valueOf(modelFeed.getLikes()));
        holder.tv_comments.setText(modelFeed.getComments() + " comments");
        holder.tv_time.setText(modelFeed.getTime());
        holder.tv_status.setText(modelFeed.getStatus());

    }

    @Override
    public int getItemCount() {

        if (modelFeedArrayList != null) {
            return modelFeedArrayList.size();
        }
        else {
            return 0;
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_time, tv_likes, tv_comments, tv_status;
        ImageView imgView_proPic;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgView_proPic = (ImageView)itemView.findViewById(R.id.imgView_profile_pic);

            tv_name = (TextView) itemView.findViewById(R.id.textView_Name);
            tv_comments = (TextView) itemView.findViewById(R.id.textView_comments);
            tv_time = (TextView) itemView.findViewById(R.id.textView_timeSincePost);
            tv_likes = (TextView) itemView.findViewById(R.id.textView_likes);
            tv_status = (TextView) itemView.findViewById(R.id.textView_status);
        }

    }
}
