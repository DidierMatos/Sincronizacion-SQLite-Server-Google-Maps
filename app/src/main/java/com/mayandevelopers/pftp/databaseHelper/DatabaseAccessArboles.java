package com.mayandevelopers.pftp.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mayandevelopers.pftp.models.ArbolesModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessArboles {

    private SQLiteOpenHelper openHelper;
    private static DatabaseAccessArboles instance;
    private SQLiteDatabase db;
    Cursor c = null;

    String especies;

    private DatabaseAccessArboles(Context context) {
        this.openHelper = new DataBaseOpenHelper(context); // llamando al constructor //
    }

    public static DatabaseAccessArboles getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccessArboles(context);
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

    public List<ArbolesModel> getArboles(int id_especie, int id_centro){

        openReadBD();

        List<ArbolesModel> arboles = new ArrayList<>();

        c=db.rawQuery("select * from arboles where id_e" + "= ? and id_c" + " = ?",new String[]{String.valueOf(id_especie), String.valueOf(id_centro)});

        if(c.moveToFirst()){
            do {
                arboles.add(new ArbolesModel(c.getInt(0),c.getString(1),c.getString(2),c.getString(3), c.getString(4), c.getInt(5), c.getInt(6)));
            }while(c.moveToNext());
        }

        closeBD();
        return arboles;
    }

    public List<ArbolesModel> editArboles(int id_arbol){

        openReadBD();

        List<ArbolesModel> arboles = new ArrayList<>();

        c=db.rawQuery("select * from arboles where id" + "= ?",new String[]{String.valueOf(id_arbol)});

        if(c.moveToFirst()){
            do {
                arboles.add(new ArbolesModel(c.getInt(0),c.getString(1),c.getString(2),c.getString(3), c.getString(4), c.getInt(5), c.getInt(6)));
            }while(c.moveToNext());
        }

        closeBD();
        return arboles;
    }

    public void updateArbol(int id_arbol, String folio_arbol, String numserie_arbol, String latitud_arbol, String longitud_arbol){

        //Log.v("UPDATEARBOL", id_arbol+folio_arbol+latitud_arbol+longitud_arbol);
        openWriteBD();
        SQLiteDatabase db2 = openHelper.getWritableDatabase();

        ContentValues actualizacion2 = new ContentValues();
        actualizacion2.put("id", id_arbol);
        actualizacion2.put("folio", folio_arbol);
        actualizacion2.put("num_serie", numserie_arbol);
        actualizacion2.put("latitud", latitud_arbol);
        actualizacion2.put("longitud", longitud_arbol);
        //actualizacion2.put("id_c", 1);
        //actualizacion2.put("id_e",6);
        //actualizacion2.put("fecha_registro", "sdfsafd");
        //actualizacion2.put("fecha_actualizacion", "asdafs");

        //actualizacion2.put("sad","sad");

        db2.update("arboles",actualizacion2,"id = ?", new String[] {String.valueOf(id_arbol)});

        //String strSQL = "UPDATE arboles set folio = "+ folio_arbol +", latitud = "+latitud_arbol+", longitud = "+longitud_arbol+ " WHERE id ="+ id_arbol;
        //db2.execSQL(strSQL);

        closeBD();

    }

    public void addArboles(int id_e, int id_c, String folio_arbol, String numserie_arbol, String latitud_arbol, String longitud_arbol){

        openWriteBD();

        SQLiteDatabase db2 = openHelper.getWritableDatabase();

        ContentValues registro = new ContentValues();
        //registro.put("id", 1);
        registro.put("id_e",id_e);
        registro.put("id_c",id_c);
        registro.put("folio", folio_arbol);
        registro.put("num_serie", numserie_arbol);
        registro.put("latitud",latitud_arbol);
        registro.put("longitud",longitud_arbol);

        db2.insert("arboles",null,registro);

        closeBD();

    }

    public List<ArbolesModel> getArbolesInfo(int id_arbol){

        openReadBD();
        List<ArbolesModel> arboles = new ArrayList<>();

        c=db.rawQuery("select * from arboles where id" + "= ?",new String[]{String.valueOf(id_arbol)});

        if(c.moveToFirst()){
            do {
                arboles.add(new ArbolesModel(c.getInt(0),c.getString(1),c.getString(2),c.getString(3), c.getString(4), c.getInt(5), c.getInt(6)));
            }while(c.moveToNext());
        }

        closeBD();
        return arboles;
    }

    public void eliminarArboles(int id_arbol){

        openWriteBD();

        SQLiteDatabase db2 = openHelper.getWritableDatabase();

        db2.delete("arboles","id = ?", new String[] { String.valueOf(id_arbol)});
        db2.delete("muestra_arbol", "id_arbol = ?", new String[] {String.valueOf(id_arbol)});

        closeBD();

    }

}
