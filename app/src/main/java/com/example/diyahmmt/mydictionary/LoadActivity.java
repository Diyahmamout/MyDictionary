package com.example.diyahmmt.mydictionary;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.diyahmmt.mydictionary.Helper.DictionaryHelper;
import com.example.diyahmmt.mydictionary.Model.DictionaryModel;
import com.example.diyahmmt.mydictionary.Preference.DictionaryPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoadActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        DictionaryHelper dictionaryHelper;
        DictionaryPreference appPreference;

        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            dictionaryHelper = new DictionaryHelper(LoadActivity.this);
            appPreference   = new DictionaryPreference(LoadActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Boolean firstRun = appPreference.getFirstRun();
            if (firstRun) {
                ArrayList<DictionaryModel> dictionaryModelsEngToInd = preLoadRaw("Eng");
                ArrayList<DictionaryModel> dictionaryModelsIndToEng = preLoadRaw("Ind");
                dictionaryHelper.open();
                Double progressMaxInsert = 100.0;
                int total_size = dictionaryModelsEngToInd.size() + dictionaryModelsIndToEng.size();
                Double progressDiff = (progressMaxInsert - progress) / total_size;

                //Eng
                dictionaryHelper.beginTransaction();
                try {
                    for (DictionaryModel model : dictionaryModelsEngToInd) {
                        dictionaryHelper.insertTransaction(model, "Eng");
                    }
                    dictionaryHelper.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }
                dictionaryHelper.endTransaction();
                progress += progressDiff;
                publishProgress((int) progress);

                //Ind
                dictionaryHelper.beginTransaction();
                try {
                    for (DictionaryModel model : dictionaryModelsIndToEng) {
                        dictionaryHelper.insertTransaction(model, "Ind");
                    }
                    dictionaryHelper.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }
                dictionaryHelper.endTransaction();
                progress += progressDiff;
                publishProgress((int) progress);

                dictionaryHelper.close();
                appPreference.setFirstRun(false);
                publishProgress((int) maxprogress);
            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);
                        publishProgress(50);
                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(LoadActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<DictionaryModel> preLoadRaw(String selection) {
        int raw_data;
        if(selection == "Eng"){
            raw_data = R.raw.english_indonesia;
        }else{
            raw_data = R.raw.indonesia_english;
        }
        ArrayList<DictionaryModel> dictionaryModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(raw_data);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                DictionaryModel dictionaryModel;

                dictionaryModel = new DictionaryModel(splitstr[0], splitstr[1]);
                dictionaryModels.add(dictionaryModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionaryModels;
    }
}
