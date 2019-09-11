package com.mayandevelopers.pftp.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccessMuestras;
import com.mayandevelopers.pftp.models.MuestrasModel;

import java.util.Calendar;
import java.util.List;

public class EstablecerMuestraActivity extends AppCompatActivity {



    DatabaseAccessMuestras databaseAccessMuestras;
    List<MuestrasModel> listMuestras;

    ImageButton imgbtn_back;
    Button btn_obtener_ubicacion;
    Button btn_establecer_muestra;
    EditText edtxt_latitud;
    EditText edtxt_longitud;
    EditText edtxt_nombre;
    EditText edtxt_folio;
    EditText edtxt_radio;

    String nombre_muestra, folio_muestra, radio_muestra, latitud_muestra, longitud_muestra;

    String do_accion;
    String id_muestra_update;
    double my_latitud;
    double my_longitud;
    private String id_centro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establecer_muestra);
        // OBTENER INSTANCIA DE MI CLASE DATABASE ACCESS //
        databaseAccessMuestras = DatabaseAccessMuestras.getInstance(getApplicationContext());

        imgbtn_back            = findViewById(R.id.imgbtnEstablecerMuestra);
        btn_obtener_ubicacion  = findViewById(R.id.btnObtenerUbicacion);
        btn_establecer_muestra = findViewById(R.id.btnEstablecerMuestra);
        edtxt_latitud          = findViewById(R.id.edtxtLatitud);
        edtxt_longitud         = findViewById(R.id.edtxtLongitud);
        edtxt_nombre           = findViewById(R.id.edtxtNombre);
        edtxt_folio            = findViewById(R.id.edtxtFolio);
        edtxt_radio            = findViewById(R.id.edtxtRadio);

        // recuperar valor para identificar que la muestra se actualizara o se esta creando //
        do_accion = getIntent().getStringExtra("accion");

        // CONDICION PARA ACTUALIZAR //
        if (do_accion.equals("update")){
            id_muestra_update = getIntent().getStringExtra("id_muestra");
            traerDatosMuestra(id_muestra_update);
        }else{

            id_centro = getIntent().getStringExtra("id_centro");
        }

        // REGRESAR A LA ACTIVIDAD ANTERIOR //
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // OBTENER LA UBICACION GPS DEL USUARIO //
        btn_obtener_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Permisos //
                if (ActivityCompat.checkSelfPermission(EstablecerMuestraActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EstablecerMuestraActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EstablecerMuestraActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
                } else {
                    obtenerUbicacion();
                }
            }
        });




        btn_establecer_muestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = edtxt_nombre.getText().toString().trim();
                String folio = edtxt_folio.getText().toString().trim();
                String latitud = edtxt_latitud.getText().toString().trim();
                String longitud = edtxt_longitud.getText().toString().trim();
                String radio = edtxt_radio.getText().toString().trim();

                if (nombre.equals("") || folio.equals("") || latitud.equals("") || longitud.equals("") || radio.equals("")) {
                    Toast.makeText(EstablecerMuestraActivity.this, "Por favor llena todos los campos e intentalo de nuevo ", Toast.LENGTH_SHORT).show();
                } else {

                    guardarDatosMuestra();
                    Intent irMapa = new Intent(EstablecerMuestraActivity.this,MapaMuestraActivity.class);
                    startActivity(irMapa);
                }
            }
        });
    }

    // EJECUTAR CONSULTA //
    private void traerDatosMuestra(String id_muestra) {
        // recibir los datos que regresa la consulta //
        listMuestras=databaseAccessMuestras.getMuestraById(id_muestra);

        // RECORRER LA LISTA //
        for (int i=0;i<listMuestras.size();i++){
            MuestrasModel muestra = listMuestras.get(i);

            nombre_muestra = muestra.getNombreMuestra();
            folio_muestra = muestra.getFolioMuestra();
            radio_muestra = muestra.getRadioMuestra();
            latitud_muestra = muestra.getLatitudMuestra();
            longitud_muestra = muestra.getLongitudMuestra();
            id_centro = muestra.getIdCentro();
            //Toast.makeText(this, muestra.getFechaMuestra(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, muestra.getFechaActualizacion(), Toast.LENGTH_SHORT).show();
        }

        // SETEAR EN LOS EDTXT LOS DATOS DE LA MUESTRA //
        edtxt_nombre.setText(nombre_muestra);
        edtxt_folio.setText(folio_muestra);
        edtxt_radio.setText(radio_muestra);
        edtxt_latitud.setText(latitud_muestra);
        edtxt_longitud.setText(longitud_muestra);
    }

    // OBTENER UBICACION GPS //
    private void obtenerUbicacion(){

        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            /// si no esta activado el gps //
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
    }

    // PERMISOS  DE GPS//
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacion();
            }
        }
    }

    // Aqui empieza la Clase Localizacion //
    public class Localizacion implements LocationListener {
        EstablecerMuestraActivity establecerMuestraActivity;

        public EstablecerMuestraActivity getMainActivity() {
            return establecerMuestraActivity;
        }
        public void setMainActivity(EstablecerMuestraActivity mainActivity) {
            this.establecerMuestraActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas debido a la deteccion de un cambio de ubicacion

            String Text = "Mi ubicacion actual es: " + loc.getLatitude() + " " + loc.getLongitude();
            my_latitud = loc.getLatitude();
            my_longitud = loc.getLongitude();
            edtxt_latitud.setText(String.valueOf(my_latitud));
            edtxt_longitud.setText(String.valueOf(my_longitud));
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            //  String gp = "GPS Desactivado";
            Toast.makeText(establecerMuestraActivity, "GPS Desactivado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            Toast.makeText(establecerMuestraActivity, "GPS Activado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Toast.makeText(establecerMuestraActivity, "LocationProvider.AVAILABLE\"", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Toast.makeText(establecerMuestraActivity, "LocationProvider.OUT_OF_SERVICE\"", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Toast.makeText(establecerMuestraActivity, "LocationProvider.TEMPORARILY_UNAVAILABLE\"", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    // GUARDAR DATOS DE LA MUESTRA //
    private void guardarDatosMuestra(){
        String nombre = edtxt_nombre.getText().toString();
        String folio = edtxt_folio.getText().toString();
        String latitud = edtxt_latitud.getText().toString();
        String longitud = edtxt_longitud.getText().toString();
        String radio = edtxt_radio.getText().toString();

        String date = obtenerFecha();

        // GUARDAR EN SHARED PREFERENCES //
        SharedPreferences coordenadas = getSharedPreferences("Coordenadas",Context.MODE_PRIVATE);
        SharedPreferences.Editor editar = coordenadas.edit();

        editar.putString("id_muestra",id_muestra_update);
        editar.putString("nombre", nombre);
        editar.putString("folio", folio);
        editar.putString("latitud", latitud);
        editar.putString("longitud", longitud);
        editar.putString("radio", radio);
        editar.putString("fecha_registro",date);
        editar.putString("fecha_actualizacion",date);
        editar.putString("id_centro", id_centro);
        editar.apply();

        SharedPreferences getcoordenadas = getSharedPreferences("Coordenadas",Context.MODE_PRIVATE);
        String r = getcoordenadas.getString("fecha_registro","");
        //Toast.makeText(this, r, Toast.LENGTH_LONG).show();

        /*/ IR A LA VISTA DEL MAPA
        Intent ir = new Intent(EstablecerMuestraActivity.this, MapsActivity1.class);
        startActivity(ir);*/

    }

    private String obtenerFecha (){
        // obtener fecha actual //
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes  = calendar.get(Calendar.MONTH);
        int año = calendar.get(Calendar.YEAR);
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);
        int segundo = calendar.get(Calendar.SECOND);

        String fecha = año + "-" + (mes + 1) + "-" + dia + " " + hora + ":" + minuto + ":" + segundo;

        return fecha;
    }


}
