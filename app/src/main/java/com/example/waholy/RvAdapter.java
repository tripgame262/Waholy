package com.example.waholy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aws on 28/01/2018.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {

    RequestOptions options ;
    private Context mContext ;
    private List<Post> mData ;
    private List<String> topic;

    public RvAdapter(Context mContext, List lst) {


        this.mContext = mContext;
        this.mData = lst;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_edit)
                .error(R.drawable.ic_edit);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.listboard,parent,false);
        // click listener here
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.topic.setText(mData.get(position).getTopic());
        holder.job.setText(mData.get(position).getJob());
        holder.name.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView topic,job,name;
        public MyViewHolder(View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.topic);
            job = itemView.findViewById(R.id.job);
            name = itemView.findViewById(R.id.name);
        }
    }
    public void updateList(List<String> newList){
        topic = new ArrayList<>();
        topic.addAll(newList);
        notifyDataSetChanged();
    }
}