package com.mayandevelopers.pftp.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.mayandevelopers.pftp.models.ArbolesModel;
import com.mayandevelopers.pftp.models.EspeciesModel;
import com.mayandevelopers.pftp.models.RanchosModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessEspecies {

    private SQLiteOpenHelper openHelper;
    private static DatabaseAccessEspecies instance;
    private SQLiteDatabase db;
    Cursor c = null;

    String especies;

    private DatabaseAccessEspecies(Context context) {
        this.openHelper = new DataBaseOpenHelper(context); // llamando al constructor //
    }

    public static DatabaseAccessEspecies getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccessEspecies(context);
        }
        return instance;
    }

    public void openWriteBD() {
        this.db = openHelper.getWritableDatabase();
    }

    public void openReadBD() {
        this.db = openHelper.getReadableDatabase();
    }

    public void closeBD() {
        if (db != null) {
            this.db.close();
        }
    }

    public List<EspeciesModel> getEspecies(){

        openReadBD();

        List<EspeciesModel> especies = new ArrayList<>();

        c=db.rawQuery("select * from especies",null);

        if(c.moveToFirst()){
            do {
                especies.add(new EspeciesModel(c.getInt(0),c.getString(1)));
            }while(c.moveToNext());
        }

        closeBD();
        return especies;
    }


    public void addEspecies(String nombreEspecie, String fecha_registro){

        openWriteBD();

        ContentValues registro = new ContentValues();
        //registro.put("id", 1);
        registro.put("nombre",nombreEspecie);
        registro.put("fecha_registro", fecha_registro);
        //registro.put("fecha_actualizacion", fecha_actualizacion);

        db.insert("especies",null,registro);

        closeBD();



    }

    public void editarEspecies(int id_especie,String nombre_especie, String fecha_actualizacion){

        openWriteBD();

        ContentValues actualizacion = new ContentValues();
        actualizacion.put("id", id_especie);
        actualizacion.put("nombre",nombre_especie);
        actualizacion.put("fecha_actualizacion", fecha_actualizacion);

        db.update("especies",actualizacion,"id = ?", new String[] { String.valueOf(id_especie)});

        closeBD();

    }

    public void eliminarEspecies(int id_especie){

        openWriteBD();

        db.delete("especies","id = ?", new String[] { String.valueOf(id_especie)});

        closeBD();

    }

}