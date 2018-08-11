package com.example.diyahmmt.mydictionary.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.diyahmmt.mydictionary.Database.DatabaseContract.KamusColumns.FIELD_MEANING;
import static com.example.diyahmmt.mydictionary.Database.DatabaseContract.KamusColumns.FIELD_WORD;
import static com.example.diyahmmt.mydictionary.Database.DatabaseContract.TABLE_ENG_TO_IND;
import static com.example.diyahmmt.mydictionary.Database.DatabaseContract.TABLE_IND_TO_ENG;

/**
 * Created by ACER on 06-08-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbdictionary";
    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_IND_TO_ENG = "create table "+TABLE_IND_TO_ENG+
            " ("+_ID+" integer primary key autoincrement, " +
            FIELD_WORD+" text not null, " +
            FIELD_MEANING+" text not null);";

    public static String CREATE_TABLE_ENG_TO_IND = "create table "+TABLE_ENG_TO_IND+
            " ("+_ID+" integer primary key autoincrement, " +
            FIELD_WORD+" text not null, " +
            FIELD_MEANING+" text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_IND_TO_ENG);
        db.execSQL(CREATE_TABLE_ENG_TO_IND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_IND_TO_ENG);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ENG_TO_IND);
        onCreate(db);
    }
}
