package com.mayandevelopers.pftp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
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
import com.mayandevelopers.pftp.models.Coordenadas;

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

    Button btn_guardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_muestra);

        btn_guardar = findViewById(R.id.btnGuardarMuestra);


        // buscar shared preferences //
        SharedPreferences coordenadas = getSharedPreferences("Coordenadas", Context.MODE_PRIVATE);
        String la = coordenadas.getString("latitud","");
        String lo = coordenadas.getString("longitud","");
        String ra = coordenadas.getString("radio","");

        // convertir coordenadas a double //
        myLat = Double.parseDouble(la);
        mylng = Double.parseDouble(lo);
        radio = Double.parseDouble(ra);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapMuestra);
        mapFragment.getMapAsync(this);

        // COORDENADAS DE EJEMPLO //
        listCoordenadas = new ArrayList<>();
        listCoordenadas.add(new Coordenadas(19.5775,-88.0454));
        listCoordenadas.add(new Coordenadas(19.5785,-88.0454));
        listCoordenadas.add(new Coordenadas(19.5785,-88.0447));
        listCoordenadas.add(new Coordenadas(19.5791,-88.0447));
        listCoordenadas.add(new Coordenadas(19.5785,-88.0457));
        listCoordenadas.add(new Coordenadas(19.5784,-88.0458));
        listCoordenadas.add(new Coordenadas(19.5795,-88.0457));
        listCoordenadas.add(new Coordenadas(19.5791,-88.0442));
        listCoordenadas.add(new Coordenadas(19.5791,-88.0464));
        listCoordenadas.add(new Coordenadas(20.212,-87.466));

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ir = new Intent(MapaMuestraActivity.this, MuestrasRanchosActivity.class);
                startActivity(ir);
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

            double PI_RAD = Math.PI / 180.0;
            double phi1 = myLat * PI_RAD;
            double phi2 = lati * PI_RAD;
            double lam1 = mylng * PI_RAD;
            double lam2 = longi * PI_RAD;

            double distancia = 6371.01 * acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1));
           // Toast.makeText(this, Double.toString(distancia), Toast.LENGTH_SHORT).show();

            if (distancia <= radio/1000){
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
