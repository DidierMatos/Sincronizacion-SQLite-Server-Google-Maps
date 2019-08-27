package com.mayandevelopers.pftp.databaseHelper;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME ="pftp_db.sqlite";
    private static final int DATABASE_VERSION=1;


    public DataBaseOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

}
