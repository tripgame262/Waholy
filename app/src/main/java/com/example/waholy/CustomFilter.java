package com.example.waholy;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class CustomFilter extends Filter {

    List<Post> filterList;
    RvAdapter rvAdapter;

    public CustomFilter(List<Post> filterList, RvAdapter rvAdapter) {
        this.filterList = filterList;
        this.rvAdapter = rvAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if(constraint != null && constraint.length()>0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<Post> filterPost = new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                if(filterList.get(i).getJob().toUpperCase().contains(constraint)){
                    filterPost.add(filterList.get(i));
                }
            }
            results.count = filterPost.size();
            results.values = filterPost;
        }else{
            results.count = filterList.size();
            results.values = filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        rvAdapter.mData = (List<Post>) results.values;
        rvAdapter.notifyDataSetChanged();

    }
}
