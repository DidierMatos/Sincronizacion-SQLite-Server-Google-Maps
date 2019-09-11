package com.mayandevelopers.pftp.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mayandevelopers.pftp.models.Coordenadas;
import com.mayandevelopers.pftp.models.CoordenadasMuestra;
import com.mayandevelopers.pftp.models.MuestrasModel;
import com.mayandevelopers.pftp.models.VisitasModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessVisitas {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccessVisitas instance;
    Cursor c = null;

    private DatabaseAccessVisitas(Context context) {
        this.openHelper = new DataBaseOpenHelper(context);
    }

    public static DatabaseAccessVisitas getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccessVisitas(context);
        }
        return instance;
    }

    public  void openBD(){
        this.db=openHelper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    public void closeBD(){
        if(db!=null){
            this.db.close();
        }
    }


   /* // VISTA MUESTRAS RANCHOS ACTIVITY //
    public List<MuestrasModel> getMuestras(String idCentro){
        openBD();
        List <MuestrasModel> muestras = new ArrayList<>();
        // consultar si existe el email //
        c = db.rawQuery("SELECT * FROM muestras WHERE id_c ='"+idCentro+"'",null);

        // verificamos si el cursor tiene algun valor //
        if(c.moveToFirst()){
            do{
                muestras.add(new MuestrasModel(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8)));
            }while (c.moveToNext());
        }
        closeBD();
        return muestras;
    }*/


    // OBTENER LAS VISITAS DE UN ARBOL //
    public List<VisitasModel> getVisitasArboles(int id_arbol){
        List<VisitasModel> visitas = new ArrayList<>();

        openBD();
        c = db.rawQuery("SELECT * FROM visitas WHERE id_a ='"+id_arbol+"'",null);
        if (c.moveToFirst()){

            do {
                visitas.add(new VisitasModel( c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4),
                        c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getString(9),
                        c.getString(10), c.getString(11), c.getString(12)));

            } while (c.moveToNext());

        }

        closeBD();
        return visitas;
    }

    // REGISTRAR UNA VISITA DE UN ARBOL //
    public String registrarVisita (String fecha, String altura, String diametro, String observaciones, String vigor,  String condicion, String sanidad,
                                   String id_arbol, String fe_registro, String fe_actualizacion, String lat, String lng){
        openBD();

        ContentValues values = new ContentValues();
        values.put("fecha_visita", fecha);
        values.put("altura", altura);
        values.put("diametro", diametro);
        values.put("observaciones", observaciones);
        values.put("vigor", vigor);
        values.put("condicion", condicion);
        values.put("sanidad", sanidad);
        values.put("id_a", id_arbol);
        values.put("fecha_registro", fe_registro);
        values.put("fecha_actualizacion", "");
        values.put("latitud", lat);
        values.put("longitud", lng);

        Long id = db.insert("visitas",null,values);
        closeBD();

        return id.toString();
    }

   /* // AGREGAR UNA LA RELACION DE LA MUESTRA //
    public String agregarRelacionMuestraArbol(String id_muestra, List<CoordenadasMuestra> coordenadas){

        String id_arbol = "";
        Long dato = null;

        openBD();

        for (int i=0; i<coordenadas.size();i++){
            CoordenadasMuestra listLat = coordenadas.get(i);

            id_arbol = listLat.getId();

            ContentValues values = new ContentValues();
            values.put("id_muestra", id_muestra);
            values.put("id_arbol",id_arbol);

            dato = db.insert("muestra_arbol",null,values);
        }

        closeBD();
        return dato.toString();
    }

    // OBTENER LOS DATOS DE UNA MUESTRA //
    public List<MuestrasModel> getMuestraById(String id ){
        List<MuestrasModel> muestra = new ArrayList<>();
        openBD();

        c=db.rawQuery("SELECT * FROM muestras WHERE id ='"+id+"'",null);

        if (c.moveToFirst()){
            do {
                muestra.add(new MuestrasModel(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8)));
            }while (c.moveToNext());
        }

        closeBD();
        return muestra;

    }

    // ACTUALIZAR UNA MUESTRA //
    public int actualizarMuestra(String id_muestra, ContentValues values){
        openBD();

        String[] id = new String[] {id_muestra};

        int id_m = db.update("muestras",values, "id = ?",id);

        closeBD();

        return id_m;
    }*/

    // ELIMINAR UNA VISITA //
    public int eliminarVisitaArbol (int id_visita){
        openBD();
        String[] id = new String[] {String.valueOf(id_visita)};

        int res = db.delete("visitas","id = ?",id);

        return res;
    }



}
