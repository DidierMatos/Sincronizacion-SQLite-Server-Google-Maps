package com.mayandevelopers.pftp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccessMuestras;
import com.mayandevelopers.pftp.models.Coordenadas;
import com.mayandevelopers.pftp.models.CoordenadasMuestra;
import com.mayandevelopers.pftp.models.MuestrasModel;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sin;
import static java.lang.StrictMath.acos;
import static java.lang.StrictMath.cos;

public class MapaMuestraActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {

    private GoogleMap mMap;
    private Marker markerPrueba;
    double myLat, mylng, radio;
    List<Coordenadas> listCoordenadas;
    List<CoordenadasMuestra> coordenadasMuestra = new ArrayList<>();

    String nombre, folio, la, lo, ra, fecha_registro, fecha_actualizacion, id_centro, id_muestra;


    Button btn_guardar, btn_cancelar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_muestra);

        btn_guardar = findViewById(R.id.btnGuardarMuestra);
        btn_cancelar = findViewById(R.id.btnCancelarMuestra);


        // DATOS DE LA MUESTRA A GUARDAR //
        SharedPreferences coordenadas = getSharedPreferences("Coordenadas", Context.MODE_PRIVATE);
        id_muestra = coordenadas.getString("id_muestra","");
        nombre = coordenadas.getString("nombre","");
        folio = coordenadas.getString("folio","");
        la = coordenadas.getString("latitud","");
        lo = coordenadas.getString("longitud","");
        ra = coordenadas.getString("radio","");
        fecha_registro = coordenadas.getString("fecha_registro","");
        fecha_actualizacion = coordenadas.getString("fecha_actualizacion","");
        id_centro = coordenadas.getString("id_centro","");

        // convertir coordenadas a double //
        myLat = Double.parseDouble(la);
        mylng = Double.parseDouble(lo);
        radio = Double.parseDouble(ra);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapMuestra);
        mapFragment.getMapAsync(this);

        final DatabaseAccessMuestras databaseAccessMuestras = DatabaseAccessMuestras.getInstance(getApplicationContext());


        // guardar las coordenadas encontradas en mi lista//
        listCoordenadas = databaseAccessMuestras.getCoordenadasArboles(id_centro);

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // GUARDAR O ACTUALIZAR LA MUESTRA //
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // CONDICION PARA IDENTIFICAR SI SE ESTA ACTUALIZANDO UNA MUESTRA O SE ESTA CREANDO UNA MUESTRA //
                if (!id_muestra.equals("")){
                    // PREPARAR VALORES A ACTUALIZAR //
                    ContentValues valuesUpdate = new ContentValues();
                    valuesUpdate.put("nombre", nombre);
                    valuesUpdate.put("folio", folio);
                    valuesUpdate.put("latitud", la);
                    valuesUpdate.put("longitud", lo);
                    valuesUpdate.put("radio", ra);
                    valuesUpdate.put("fecha_actualizacion", fecha_actualizacion);
                    valuesUpdate.put("id_c", id_centro);

                    // EJECUTAR METODO PARA ACTUALIZAR //
                    int num_registros_actualizados = databaseAccessMuestras.actualizarMuestra(id_muestra, valuesUpdate);
                    int num_reg_eliminados = databaseAccessMuestras.eliminarRelacionMuestraArbol(id_muestra);

                    // RETARDO ENTRE INSERCIONES //
                    new Handler().postDelayed(new Runnable(){
                        public void run(){
                            // INSERTAR A LA BD LA RELACION DE LA MUESTRA-ARBOL //
                            String id = databaseAccessMuestras.agregarRelacionMuestraArbol(id_muestra, coordenadasMuestra);
                            //Toast.makeText(MapaMuestraActivity.this, id, Toast.LENGTH_SHORT).show();
                            Toast.makeText(MapaMuestraActivity.this, "La muestra se actualizó correctamente", Toast.LENGTH_SHORT).show();
                            Intent ir = new Intent(MapaMuestraActivity.this,MuestrasRanchosActivity.class);
                            ir.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(ir);
                        }
                    }, 1000);

                }else{

                    // insertar muestra en base de datos //
                    final String id_muestra =databaseAccessMuestras.agregarMuestra(nombre, folio,la, lo, ra, fecha_registro, id_centro);

                    // RETARDO ENTRE INSERCIONES //
                    new Handler().postDelayed(new Runnable(){
                        public void run(){
                            // INSERTAR A LA BD LA RELACION DE LA MUESTRA-ARBOL //
                            String id = databaseAccessMuestras.agregarRelacionMuestraArbol(id_muestra, coordenadasMuestra);
                            //Toast.makeText(MapaMuestraActivity.this, id, Toast.LENGTH_SHORT).show();
                            Toast.makeText(MapaMuestraActivity.this, "La muestra se guardo correctamente", Toast.LENGTH_SHORT).show();
                            Intent ir = new Intent(MapaMuestraActivity.this,MuestrasRanchosActivity.class);
                            ir.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(ir);
                        }
                    }, 1000);

                }
               /* Intent ir = new Intent(MapaMuestraActivity.this, MuestrasRanchosActivity.class);
                startActivity(ir);*/
            }
        });

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(markerPrueba)){
            String lat, lng;
            lat = Double.toString(marker.getPosition().latitude);
            lng = Double.toString(marker.getPosition().longitude);
            //Double.toString(marker.getPosition().latitude);
            Toast.makeText(this, lat+" "+ lng, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        /// estilo del mapa
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) { }
        } catch (Resources.NotFoundException e) { }

        // permiso para acceder a la ubicacion actual del gps //
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }else{

        }
        // llevar a mi posision actual //
        mMap.setMyLocationEnabled(true);
        LatLng myPosition = new LatLng(myLat,mylng);
        mMap.addCircle(new CircleOptions()
                .center(new LatLng(myLat, mylng))
                .radius(radio)
                .strokeWidth(3)
                .strokeColor(Color.RED)
                .fillColor(Color.argb(128,255,0,0))
                .clickable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,17));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        Antut(googleMap);

    }

    // crear marcadores //
    public void Antut (GoogleMap googleMap){
        mMap = googleMap;


        for (int i = 0; i<listCoordenadas.size();i++){

            Coordenadas listLat = listCoordenadas.get(i);
            double lati = listLat.getLat();
            double longi= listLat.getLng();
            String id = listLat.getId();

            double PI_RAD = Math.PI / 180.0;
            double phi1 = myLat * PI_RAD;
            double phi2 = lati * PI_RAD;
            double lam1 = mylng * PI_RAD;
            double lam2 = longi * PI_RAD;

            double distancia = 6371.01 * acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1));
           // Toast.makeText(this, Double.toString(distancia), Toast.LENGTH_SHORT).show();

            if (distancia <= radio/1000){

                // AGREGAR COORDENADAS DE LOS ARBOLES A UNA LISTA //
                addCoordenadas(id, lati, longi);
                LatLng arbol = new LatLng(lati , longi);
                mMap.addMarker(new MarkerOptions().position(arbol).title("Cedro").snippet("Arbol en crecimiento altura 1.5 m").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_tree_green)));
            }else{
                LatLng arbol = new LatLng(lati , longi);
                mMap.addMarker(new MarkerOptions().position(arbol).title("Cedro").snippet("Arbol en crecimiento altura 1.5 m").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_tree_red)));
            }
        }


        /*// prueba /
        LatLng prueba = new LatLng(20.212, -87.466 );
        markerPrueba = googleMap.addMarker(new MarkerOptions()
                .position(prueba)
                .title("Prueba")
                .draggable(true)

        );*/

        // establecer escuchador a los markers //
        //googleMap.setOnMarkerClickListener(this);


    }

    // AGREGAR LAS COORDENADAS QUE ESTAN DENTRO DEL RANGO A UNA NUEVA LISTA //
    private void addCoordenadas(String id, double latitud, double longitud) {

        coordenadasMuestra.add(new CoordenadasMuestra(id,latitud,longitud));
        int tamanio = coordenadasMuestra.size();
        String size = String.valueOf(tamanio);
        //Toast.makeText(MapaMuestraActivity.this, id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
