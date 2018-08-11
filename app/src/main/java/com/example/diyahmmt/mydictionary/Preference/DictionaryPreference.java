package com.example.diyahmmt.mydictionary.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.diyahmmt.mydictionary.R;

/**
 * Created by ACER on 06-08-2018.
 */

public class DictionaryPreference {
    SharedPreferences preferences;
    Context context;

    public DictionaryPreference(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input){

        SharedPreferences.Editor editor = preferences.edit();
        String key = context.getResources().getString(R.string.dictionary_pref);
        editor.putBoolean(key,input);
        editor.commit();
    }

    public Boolean getFirstRun(){
        String key = context.getResources().getString(R.string.dictionary_pref);
        return preferences.getBoolean(key, true);
    }
}
