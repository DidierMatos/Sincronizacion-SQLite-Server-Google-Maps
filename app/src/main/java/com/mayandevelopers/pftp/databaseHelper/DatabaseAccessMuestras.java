package com.mayandevelopers.pftp.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.mayandevelopers.pftp.models.Coordenadas;
import com.mayandevelopers.pftp.models.CoordenadasMuestra;
import com.mayandevelopers.pftp.models.ModeloPrueba;
import com.mayandevelopers.pftp.models.MuestrasModel;
import com.mayandevelopers.pftp.models.RanchosModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessMuestras {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccessMuestras instance;
    Cursor c = null;

    private DatabaseAccessMuestras(Context context) {
        this.openHelper = new DataBaseOpenHelper(context);
    }

    public static DatabaseAccessMuestras getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccessMuestras(context);
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


    // VISTA MUESTRAS RANCHOS ACTIVITY //
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
    }

    // ELIMINAR UNA MUESTRA //
    public String eliminarMuestra(String id){
        openBD();
        //db.execSQL("PRAGMA foreign_key=ON");
        c = db.rawQuery("DELETE FROM  muestras WHERE id = '"+id+"'",null);

        if (c.moveToFirst()){
            return "1";
        }else {
            return "0";
        }
    }

    /*public List<ModeloPrueba> obtenerIdArbolesMuestra(String id_muestra){

        openBD();
        //db.execSQL("PRAGMA foreign_key=ON");
        List <ModeloPrueba> prueba = new ArrayList<>();

        c=db.rawQuery("SELECT * FROM muestra_arbol where id_muestra = '"+id_muestra+"'",null);
        if (c.moveToFirst()){
            do {
                prueba.add(new ModeloPrueba(c.getString(0),c.getString(1)));

            }while (c.moveToNext());
        }

        closeBD();
        return prueba;
    }*/

    // OBTENER LAS COORDENADAS DE LOS ARBOLES //
    public List<Coordenadas> getCoordenadasArboles(String id_centro){
        List<Coordenadas> coordenadas = new ArrayList<>();

        openBD();
        c = db.rawQuery("SELECT  id, latitud, longitud FROM arboles WHERE id_c ='"+id_centro+"'",null);
        if (c.moveToFirst()){


            do {
                coordenadas.add(new Coordenadas(c.getString(0),c.getDouble(1),c.getDouble(2)));
            } while (c.moveToNext());
        }

        closeBD();
        return coordenadas;
    }

    // AGREGAR UNA MUESTRA //
    public String agregarMuestra (String nombre, String folio, String la, String lo, String ra,  String fecha_registro, String id_centro){
        openBD();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("folio", folio);
        values.put("latitud", la);
        values.put("longitud", lo);
        values.put("radio", ra);
        values.put("fecha_registro", fecha_registro);
        values.put("id_c", id_centro);

        Long id = db.insert("muestras",null,values);
        closeBD();

        return id.toString();
    }

    // AGREGAR UNA LA RELACION DE LA MUESTRA //
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
    }

    // ELIMINAR LA RELACION DE UNA MUESTRA CON ARBOLES DESPUES DE ACTUALIZAR LA MUESTRA //
    public int eliminarRelacionMuestraArbol (String id_muestra){
        openBD();
        String[] id = new String[] {id_muestra};

        int res = db.delete("muestra_arbol","id_muestra = ?",id);

        return res;
    }



}
