package com.example.diyahmmt.mydictionary.ViewHolder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.diyahmmt.mydictionary.DetailDictionaryActivity;
import com.example.diyahmmt.mydictionary.Model.DictionaryModel;
import com.example.diyahmmt.mydictionary.R;

/**
 * Created by ACER on 06-08-2018.
 */

public class SearchViewHolder extends RecyclerView.ViewHolder {

    TextView tvWord, tvMeaning;

    public SearchViewHolder(View itemView) {
        super(itemView);
        tvWord  = (TextView)itemView.findViewById(R.id.tvWord);
        tvMeaning = (TextView)itemView.findViewById(R.id.tvMeaning);
    }

    public void bind(final DictionaryModel dictionaryModel) {
        tvWord.setText(dictionaryModel.getWord());
        tvMeaning.setText(dictionaryModel.getMeaning());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), DetailDictionaryActivity.class);
                intent.putExtra(DetailDictionaryActivity.ITEM_WORD, dictionaryModel.getWord());
                intent.putExtra(DetailDictionaryActivity.ITEM_MEANING, dictionaryModel.getMeaning());
                intent.putExtra(DetailDictionaryActivity.ITEM_CATEGORY, dictionaryModel.getCategory());
                itemView.getContext().startActivity(intent);
            }
        });
    }
}