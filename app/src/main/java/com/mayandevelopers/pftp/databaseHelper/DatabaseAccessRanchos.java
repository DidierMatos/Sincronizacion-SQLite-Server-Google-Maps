package com.mayandevelopers.pftp.databaseHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mayandevelopers.pftp.models.RanchosModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessRanchos {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccessRanchos instance;
    Cursor c = null;

    private DatabaseAccessRanchos(Context context) {
        this.openHelper = new DataBaseOpenHelper(context);
    }

    public static DatabaseAccessRanchos getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccessRanchos(context);
        }
        return instance;
    }

    public  void openReadBD(){
        this.db=openHelper.getReadableDatabase();
    }

    public  void openWriteBD(){
        this.db=openHelper.getWritableDatabase();
    }

    public void closeBD(){
        if(db!=null){
            this.db.close();
        }
    }

    public List<RanchosModel> obtenerRanchos() {
        openReadBD();
        c = db.rawQuery("SELECT * FROM centros",null);
        List<RanchosModel> ranchos = new ArrayList<>();

        if (c.moveToFirst()){
            do{
                ranchos.add(new RanchosModel(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5)));
            } while (c.moveToNext());
        }

        closeBD();
        return ranchos;
    }


/*    public List<RanchosModel> getRanchos(){
        List<RanchosModel> ranchos = new ArrayList<>();

        //c=db.rawQuery("select * from centros where id = '"+id_especie+"'",null);
        c=db.rawQuery("select * from centros",null);

        //StringBuffer buffer = new StringBuffer();
        *//*EspeciesModel especiesModel = new EspeciesModel();*//*

        if(c.moveToFirst()){
            do {
                ranchos.add(new RanchosModel(c.getInt(0),c.getString(1),c.getString(2),c.getString(3), c.getString(4), c.getString(5)));
            }while(c.moveToNext());
        }

        closeBD();
        return ranchos;
        //return buffer.toString();
    }*/


}
