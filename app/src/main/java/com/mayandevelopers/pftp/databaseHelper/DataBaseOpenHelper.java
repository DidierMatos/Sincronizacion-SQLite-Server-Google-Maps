package com.mayandevelopers.pftp.databaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="pftp_db.sqlite";
    private static final int DATABASE_VERSION=1;

     final String CREAR_TABLA_MUESTRAS = "CREATE TABLE muestras (id INTEGER PRIMARY KEY AUTOINCREMENT, folio TEXT, nombre TEXT, latitud TEXT, longitud TEXT, radio TEXT, id_c TEXT, fecha_registro TEXT, fecha_actualizacion TEXT)";


    public DataBaseOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
