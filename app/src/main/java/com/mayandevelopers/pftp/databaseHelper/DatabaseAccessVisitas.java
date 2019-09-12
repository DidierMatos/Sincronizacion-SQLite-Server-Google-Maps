package com.mayandevelopers.pftp.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mayandevelopers.pftp.models.Coordenadas;
import com.mayandevelopers.pftp.models.CoordenadasMuestra;
import com.mayandevelopers.pftp.models.MuestrasModel;
import com.mayandevelopers.pftp.models.VisitasModel;

import java.io.ByteArrayInputStream;
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
                                   String id_arbol, String fe_registro,  String lat, String lng){
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
        values.put("latitud", lat);
        values.put("longitud", lng);

        Long id = db.insert("visitas",null,values);
        closeBD();

        return id.toString();
    }

    // AGREGAR IMAGEN DE LA VISITA //
    public String agregarImagenVisita(String origen, String id_referencia, String nombre, byte[] imagen, String fecha_registro){

        ContentValues values = new ContentValues();
        values.put("origen",origen);
        values.put("id_referencia",id_referencia);
        values.put("nombre",nombre);
        values.put("imagen",imagen);
        values.put("fecha_registro",fecha_registro);

        Long dato = null;

        openBD();

        dato = db.insert("imagenes",null,values);

        closeBD();
        return dato.toString();
    }

    // OBTENER LOS DATOS DE UNA VISITA A ACTUALIZAR //
    public List<VisitasModel> getDatosVisitaUpdate(int idVisita ){
        String id_visita = String.valueOf(idVisita);
        List<VisitasModel> visita = new ArrayList<>();
        openBD();

        c=db.rawQuery("SELECT * FROM visitas WHERE id ='"+id_visita+"'",null);

        if (c.moveToFirst()){
            do {
                visita.add(new VisitasModel(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),
                        c.getString(9), c.getString(10), c.getString(11), c.getString(12)));
            }while (c.moveToNext());
        }

        closeBD();
        return visita;
    }

    // ELIMINAR UNA VISITA //
    public int eliminarVisitaArbol (int id_visita){
        openBD();
        String[] id = new String[] {String.valueOf(id_visita)};

        int res = db.delete("visitas","id = ?",id);

        return res;
    }

    // TRAER IMAGEN DE LA VISITA //
    public Bitmap traerImagenVisita(int id_visita_referencia){

        Bitmap bitmap = null;
        openBD();

        c=db.rawQuery("SELECT * FROM imagenes WHERE id_referencia='"+id_visita_referencia+"'",null);

        if (c.moveToFirst()){
            byte[] blob = c.getBlob(4);
            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            bitmap = BitmapFactory.decodeStream(bais);
        }

        closeBD();
        return bitmap;
    }


    // ACTUALIZAR UNA VISITA //
    public int actualizarVisita(String id_visita, ContentValues values){
        openBD();

        String[] id = new String[] {id_visita};

        int id_v = db.update("visitas",values, "id = ?",id);

        closeBD();
        return id_v;
    }

    // ELIMINAR LA IMAGEN DE LA VISITA  //
    public int eliminarImagenVisita (String id_visita){
        openBD();
        String[] id = new String[] {id_visita};

        int res = db.delete("imagenes","id_referencia = ?",id);

        return res;
    }





}
