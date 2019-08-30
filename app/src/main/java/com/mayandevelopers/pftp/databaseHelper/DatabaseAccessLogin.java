package com.mayandevelopers.pftp.databaseHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccessLogin {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccessLogin instance;
    Cursor c = null;

    private DatabaseAccessLogin(Context context) {
        this.openHelper = new DataBaseOpenHelper(context);
    }

    public static DatabaseAccessLogin getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccessLogin(context);
        }
        return instance;
    }

    public  void open(){
        this.db=openHelper.getWritableDatabase();
    }

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }


    // VISTA LOGIN iniciar sesion //
    public String getUserByEmail(String correo, String pass){
        // consultar si existe el email //
        c = db.rawQuery("select * from usuarios where correo ='"+correo+"'",null);

        // verificamos si el cursor tiene algun valor //
        if(c.moveToFirst()){
            if (pass.equals(c.getString(10))){
                // Comparar contraseñas //
                return c.getString(1);
            } else{
                // contraseñas no son iguales //
                return "2";
            }
        }else{
            // no se encontro el correo //
            return "0";
        }
    }



}
