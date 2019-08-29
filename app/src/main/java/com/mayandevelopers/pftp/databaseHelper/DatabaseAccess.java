package com.mayandevelopers.pftp.databaseHelper;

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

    /*public String getArboles(){
        c=db.rawQuery("select latitud from arboles", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            usuario.setId(c.getString(0));
            String arboles = c.getString(0);
            buffer.append(""+arboles+ "\n");
        }

        return buffer.toString();
    }*/


}
