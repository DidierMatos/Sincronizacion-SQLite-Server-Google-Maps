package com.mayandevelopers.pftp.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mayandevelopers.pftp.models.ArbolesModel;
import com.mayandevelopers.pftp.models.EspeciesModel;
import com.mayandevelopers.pftp.models.RanchosModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private static DatabaseAccess instance;
    private SQLiteDatabase db;
    Cursor c = null;

    String especies;

    private DatabaseAccess(Context context) {
        this.openHelper = new DataBaseOpenHelper(context); // llamando al constructor //
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

    public  void openRead(){
        this.db=openHelper.getReadableDatabase();
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

    public String getEspecies(){
        c=db.rawQuery("select nombre from especies", new String[]{});
        StringBuffer buffer = new StringBuffer();

        while(c.moveToNext()){

            String especies = c.getString(0);
            buffer.append(""+especies+ "\n");
        }

        return buffer.toString();
    }

    public List<EspeciesModel> getEspecies2(){

        List<EspeciesModel> especies = new ArrayList<>();

        c=db.rawQuery("select * from especies",null);

        //StringBuffer buffer = new StringBuffer();
        /*EspeciesModel especiesModel = new EspeciesModel();*/

        if(c.moveToFirst()){
            do {
                especies.add(new EspeciesModel(c.getInt(0),c.getString(1)));
            }while(c.moveToNext());
        }

        return especies;
        //return buffer.toString();
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
        //registro.put("id", 1);
        registro.put("nombre",nombreEspecie);

        db2.insert("especies",null,registro);

        db2.close();



    }

    public void editarEspecies(int id_especie,String nombre_especie){

        SQLiteDatabase db2 = openHelper.getWritableDatabase();

        ContentValues actualizacion = new ContentValues();
        actualizacion.put("id", id_especie);
        actualizacion.put("nombre",nombre_especie);

        db2.update("especies",actualizacion,"id = ?", new String[] { String.valueOf(id_especie)});

        db2.close();

    }

    public void eliminarEspecies(int id_especie){

        SQLiteDatabase db2 = openHelper.getWritableDatabase();

        //ContentValues eliminacion = new ContentValues();
        //eliminacion.put("id", id_especie);
        //actualizacion.put("nombre",nombre_especie);

        db2.delete("especies","id = ?", new String[] { String.valueOf(id_especie)});

        db2.close();

    }

    public List<RanchosModel> getRanchos(){
        List<RanchosModel> ranchos = new ArrayList<>();

        //c=db.rawQuery("select * from centros where id = '"+id_especie+"'",null);
        c=db.rawQuery("select * from centros",null);

        //StringBuffer buffer = new StringBuffer();
        /*EspeciesModel especiesModel = new EspeciesModel();*/

        if(c.moveToFirst()){
            do {
                ranchos.add(new RanchosModel(c.getInt(0),c.getString(1),c.getString(2),c.getString(3), c.getString(4), c.getString(5)));
            }while(c.moveToNext());
        }

        return ranchos;
        //return buffer.toString();
    }


    public List<ArbolesModel> getArboles(int id_especie, int id_centro){
        List<ArbolesModel> arboles = new ArrayList<>();

        //c=db.rawQuery("select * from centros where id = '"+id_especie+"'",null);
        c=db.rawQuery("select * from arboles where id_e" + "= ? and id_c" + " = ?",new String[]{String.valueOf(id_especie), String.valueOf(id_centro)});

        //StringBuffer buffer = new StringBuffer();
        /*EspeciesModel especiesModel = new EspeciesModel();*/

        if(c.moveToFirst()){
            do {
                arboles.add(new ArbolesModel(c.getInt(0),c.getString(1),c.getString(2),c.getString(3), c.getString(4), c.getInt(5), c.getInt(6)));
            }while(c.moveToNext());
        }

        return arboles;
        //return buffer.toString();
    }


}
