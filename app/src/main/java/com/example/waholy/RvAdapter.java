package com.example.waholy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aws on 28/01/2018.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> implements Filterable {

    RequestOptions options ;
    private Context mContext ;
    protected List<Post> mData , filterList ;
    CustomFilter filter;
    public RvAdapter(Context mContext, List lst) {

        this.mContext = mContext;
        this.mData = lst;
        this.filterList = lst;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.listboard,parent,false);

        final MyViewHolder myViewHolder = new MyViewHolder(view);
        // click listener here
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.topic.setText(mData.get(position).getTopic());
        holder.job.setText(mData.get(position).getJob());
        holder.name.setText(mData.get(position).getName());
        holder.amount.setText(mData.get(position).getAmount());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String gTopic = mData.get(position).getTopic();
                String gName = mData.get(position).getName();
                String gAmount = mData.get(position).getAmount();
                String gTag = mData.get(position).getJob();
                String gDetail = mData.get(position).getDetails();
                Intent intent = new Intent(mContext,DetailActivity.class);
                intent.putExtra("iTopic",gTopic);
                intent.putExtra("iName",gName);
                intent.putExtra("iAmount",gAmount);
                intent.putExtra("iTag",gTag);
                intent.putExtra("iDetail",gDetail);
                mContext.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter = new CustomFilter(filterList,this);
        }
        return filter;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView topic,job,name,amount,detail;
        Button btnDetail;
        ItemClickListener itemClickListener;
        public MyViewHolder(View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.topic);
            job = itemView.findViewById(R.id.job);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            detail=itemView.findViewById(R.id.dDetail);

            btnDetail = itemView.findViewById(R.id.btn_detail);
            btnDetail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClickListener(v,getLayoutPosition());

        }
        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
        }
    }
}