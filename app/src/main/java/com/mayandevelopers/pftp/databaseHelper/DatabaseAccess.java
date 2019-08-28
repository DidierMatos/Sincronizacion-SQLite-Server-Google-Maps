package com.mayandevelopers.pftp.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    private DatabaseAccess(Context context) {
        this.openHelper = new DataBaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccess(context);
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

    public String getArboles(){
        c=db.rawQuery("select latitud from arboles", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String arboles = c.getString(0);
            buffer.append(""+arboles+ "\n");
        }

        return buffer.toString();
    }

    public String getEspecies(){
        c=db.rawQuery("select nombre from especies", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String especies = c.getString(0);
            buffer.append(""+especies+ "\n");
        }

        return buffer.toString();
    }


    public void addEspecies(String nombreEspecie){

        /*SQLiteDatabase db2 = openHelper.getWritableDatabase();
        int num = 34;

        if(db2 != null){

            db2.execSQL("INSERT INTO especies (id, nombre) " +
                    "VALUES (num, '"+ nombreEspecie +"')");

        }

        db2.close();*/


        SQLiteDatabase db2 = openHelper.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("nombre",nombreEspecie);

        db2.insert("especies",null,registro);

        db2.close();



    }


}
