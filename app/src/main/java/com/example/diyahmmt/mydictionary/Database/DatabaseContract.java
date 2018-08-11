package com.example.diyahmmt.mydictionary.Database;

import android.provider.BaseColumns;

/**
 * Created by ACER on 06-08-2018.
 */

public class DatabaseContract {
    public static String TABLE_IND_TO_ENG = "ind_to_eng";
    public static String TABLE_ENG_TO_IND = "eng_to_ind";

    public static final class KamusColumns implements BaseColumns {
        public static String FIELD_WORD = "word";
        public static String FIELD_MEANING = "meaning";
    }
}
