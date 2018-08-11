package com.example.diyahmmt.mydictionary.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.diyahmmt.mydictionary.Model.DictionaryModel;
import com.example.diyahmmt.mydictionary.R;
import com.example.diyahmmt.mydictionary.ViewHolder.SearchViewHolder;

import java.util.ArrayList;

/**
 * Created by ACER on 06-08-2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private ArrayList<DictionaryModel> list = new ArrayList<>();

    public SearchAdapter() {
    }

    public void replaceAll(ArrayList<DictionaryModel> items) {
        list = items;
        notifyDataSetChanged();
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.content_list_item, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
