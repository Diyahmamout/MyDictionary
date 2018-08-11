package com.example.diyahmmt.mydictionary.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ACER on 06-08-2018.
 */

public class DictionaryModel implements Parcelable {
    private int id;
    private String word;
    private String meaning;
    private String category;

    public DictionaryModel(String word, String deskripsi){
        this.word = word;
        this.meaning = deskripsi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.word);
        dest.writeString(this.meaning);
        dest.writeString(this.category);
    }

    public DictionaryModel() {
    }

    protected DictionaryModel(Parcel in) {
        this.id = in.readInt();
        this.word = in.readString();
        this.meaning = in.readString();
        this.category = in.readString();
    }

    public static final Creator<DictionaryModel> CREATOR = new Creator<DictionaryModel>() {
        @Override
        public DictionaryModel createFromParcel(Parcel source) {
            return new DictionaryModel(source);
        }

        @Override
        public DictionaryModel[] newArray(int size) {
            return new DictionaryModel[size];
        }
    };
}
