package com.example.diyahmmt.mydictionary.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.diyahmmt.mydictionary.Database.DatabaseHelper;
import com.example.diyahmmt.mydictionary.Model.DictionaryModel;
import com.example.diyahmmt.mydictionary.R;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.diyahmmt.mydictionary.Database.DatabaseContract.KamusColumns.FIELD_MEANING;
import static com.example.diyahmmt.mydictionary.Database.DatabaseContract.KamusColumns.FIELD_WORD;
import static com.example.diyahmmt.mydictionary.Database.DatabaseContract.TABLE_ENG_TO_IND;
import static com.example.diyahmmt.mydictionary.Database.DatabaseContract.TABLE_IND_TO_ENG;

/**
 * Created by ACER on 06-08-2018.
 */

public class DictionaryHelper {
    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DictionaryHelper(Context context){
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public ArrayList<DictionaryModel> getDataByName(String search, String selection){
        String category = null;
        Cursor cursor;
        if(selection == "Eng"){
            cursor = database.query(TABLE_ENG_TO_IND,null,FIELD_WORD+" LIKE ?",new String[]{search.trim()+"%"},null,null,_ID + " ASC",null);
            category = context.getResources().getString(R.string.eng_to_ind);
        }else{
            cursor = database.rawQuery("SELECT * FROM " + TABLE_IND_TO_ENG +
                    " WHERE " + FIELD_WORD + " LIKE " + search.trim() + "%'", null);
            category = context.getResources().getString(R.string.ind_to_eng);
        }
        cursor.moveToFirst();

        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        DictionaryModel dictionaryModel;
        if (cursor.getCount()>0) {
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dictionaryModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_WORD)));
                dictionaryModel.setMeaning(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_MEANING)));
                dictionaryModel.setCategory(category);
                arrayList.add(dictionaryModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<DictionaryModel> getAllData(String selection){
        Cursor cursor;
        String category = null;
        if(selection == "Eng"){
            cursor = database.query(TABLE_ENG_TO_IND,null,null,null,null,null,_ID+ " ASC",null);
            category = context.getResources().getString(R.string.eng_to_ind);
        }else{
            cursor = database.query(TABLE_IND_TO_ENG,null,null,null,null,null,_ID+ " ASC",null);
            category = context.getResources().getString(R.string.ind_to_eng);
        }
        cursor.moveToFirst();

        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        DictionaryModel dictionaryModel;
        if (cursor.getCount()>0) {
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dictionaryModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_WORD)));
                dictionaryModel.setMeaning(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_MEANING)));
                dictionaryModel.setCategory(category);
                arrayList.add(dictionaryModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(DictionaryModel dictionaryModel, String selection){
        String table = null;
        if(selection == "Eng"){
            table = TABLE_ENG_TO_IND;
        }else{
            table = TABLE_IND_TO_ENG;
        }

        String sql = "INSERT INTO "+table+" ("+FIELD_WORD+", "+FIELD_MEANING
                +") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, dictionaryModel.getWord());
        stmt.bindString(2, dictionaryModel.getMeaning());
        stmt.execute();
        stmt.clearBindings();
    }

    public int update(DictionaryModel dictionaryModel, String selection){
        String table = null;
        if(selection == "Eng"){
            table = TABLE_ENG_TO_IND;
        }else{
            table = TABLE_IND_TO_ENG;
        }

        ContentValues args = new ContentValues();
        args.put(FIELD_WORD, dictionaryModel.getWord());
        args.put(FIELD_MEANING, dictionaryModel.getMeaning());
        return database.update(table, args, _ID + "= '" + dictionaryModel.getId() + "'", null);
    }

    public int delete(int id, String selection){
        String table = null;
        if(selection == "Eng"){
            table = TABLE_ENG_TO_IND;
        }else{
            table = TABLE_IND_TO_ENG;
        }

        return database.delete(table, _ID + " = '"+id+"'", null);
    }
}
