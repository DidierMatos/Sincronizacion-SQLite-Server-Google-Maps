package com.mayandevelopers.pftp.views;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.controllers.RvArbolesController;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccess;
import com.mayandevelopers.pftp.models.ArbolesModel;

import java.util.ArrayList;
import java.util.List;

public class AgregarArbolActivity extends FragmentActivity implements SeekBar.OnSeekBarChangeListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener,
        AdapterView.OnItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    //EditText edtxt_longitud,edtxt_latitud, edtxt_radio;
    private Button btn_buscar, btn_buscar2;
    private double longitud, latitud, radio;
    private Boolean bandera = false;
    private Marker marcador;
    private UiSettings mUiSettings;
    CameraPosition BONDI;


    String valorUpdate;
    TextView txtview_add_arbol;
    Button btn_ubicacion;
    Button imgbtn_add_arbol, imgbtn_ubicacion;
    TextInputEditText edtxt_especie, edtxt_centro, edtxt_folio, edtxt_latitud, edtxt_longitud;
    ImageButton imgbtn_back;

    double lat;
    double lng;
    ProgressDialog pdialog_progress_empresa;

    int id_arbol_obtenido, id_especie_obtenido, id_rancho_obtenido;
    String nombre_especie_obtenido, nombre_rancho_obtenido, folio_arbol_obtenido;
    Object arbol;

    List<ArbolesModel> arboles_model;
    RecyclerView rv_mis_arboles;
    RvArbolesController rv_arboles_controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_arbol);

        txtview_add_arbol = findViewById(R.id.txtviewAddArbol);
        edtxt_especie = findViewById(R.id.edtxtEspecie);
        edtxt_centro = findViewById(R.id.edtxtCentro);
        edtxt_folio = findViewById(R.id.edtxtFolio);
        edtxt_latitud = findViewById(R.id.edtxtLatitud);
        edtxt_longitud = findViewById(R.id.edtxtLongitud);
        imgbtn_add_arbol = findViewById(R.id.btnAddArbol);
        btn_ubicacion = findViewById(R.id.btnUbicacionAddArbol);
        imgbtn_back = findViewById(R.id.imgbtnBackAddArbol);

        id_especie_obtenido = getIntent().getIntExtra("id_especie", 77);
        nombre_especie_obtenido = getIntent().getStringExtra("nombre_especie");
        Toast.makeText(this, String.valueOf(id_especie_obtenido), Toast.LENGTH_SHORT).show();
        id_rancho_obtenido = getIntent().getIntExtra("id_rancho", 77);
        nombre_rancho_obtenido = getIntent().getStringExtra("nombre_rancho");

        id_arbol_obtenido = getIntent().getIntExtra("id_arbol",77);
        folio_arbol_obtenido =getIntent().getStringExtra("folio_arbol");


        //Toast.makeText(this, "especie:"+id_especie_obtenida + " rancho:" + id_rancho_obtenido, Toast.LENGTH_SHORT).show();

       /* SharedPreferences prefs = getSharedPreferences("ESPECIE_SELECCIONADA", MODE_PRIVATE);
        id_especie_obtenida = prefs.getInt("id_especie", 77);*/
        /*SharedPreferences prefs2 = getSharedPreferences("RANCHO_SELECCIONADO", MODE_PRIVATE);
        id_rancho_obtenido = prefs2.getInt("id_rancho", 77);*/

        //Toast.makeText(this, "especie:"+id_especie_obtenida + " rancho:" + id_rancho_obtenido, Toast.LENGTH_SHORT).show();

        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        valorUpdate = getIntent().getStringExtra("update");

        if (valorUpdate == null){
            //CREAR
            //Toast.makeText(this, "hola", Toast.LENGTH_SHORT).show();
            edtxt_especie.setText(nombre_especie_obtenido);
            edtxt_centro.setText(nombre_rancho_obtenido);

        }else{

            editMiArbol();

            imgbtn_add_arbol.setText("Actualizar");
            txtview_add_arbol.setText("Actualizar Arbol");
            edtxt_especie.setText(nombre_especie_obtenido);
            edtxt_centro.setText(nombre_rancho_obtenido);

        }

        //edtxt_longitud = findViewById(R.id.edtxtlongitud);
        //edtxt_latitud = findViewById(R.id.edtxtlatitud);
        //edtxt_radio = findViewById(R.id.edtxtradio);
        //btn_buscar = findViewById(R.id.btnbuscar);
        //btn_buscar2 = findViewById(R.id.btnbuscar2);

        //edtxt_latitud.setText("-99.185594");
        //edtxt_longitud.setText("19.419289");
        //edtxt_radio.setText("100");


/*        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bandera = true;

                latitud = Double.parseDouble(edtxt_latitud.getText().toString());
                longitud = Double.parseDouble(edtxt_longitud.getText().toString());
                radio = Double.parseDouble(edtxt_radio.getText().toString());

                setMap();

            }
        });*/

        imgbtn_add_arbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bandera = false;
                //latitud = Double.parseDouble(edtxt_latitud.getText().toString());
                //longitud = Double.parseDouble(edtxt_longitud.getText().toString());
                setMap();
                addMiArbol();

            }
        });

        // OBTENER UBICACION//
        btn_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(AgregarArbolActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AgregarArbolActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        msgRecomendacion();

                } else {
                    locationStart();
                    //Toast.makeText(AgregarArbolActivity.this, "HOLA", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void addMiArbol(){

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());

        databaseAccess.open();

        //String id_especie = edtxt_especie.getText().toString();
        //String id_centro = edtxt_centro.getText().toString();
        String folio = edtxt_folio.getText().toString();
        String latitud = edtxt_latitud.getText().toString();
        String longitud = edtxt_longitud.getText().toString();

        if(folio != null){
            databaseAccess.addArboles(id_especie_obtenido, id_rancho_obtenido, folio, latitud, longitud);
        }else{
            Toast.makeText(AgregarArbolActivity.this, "Ingresa un nombre valido", Toast.LENGTH_SHORT).show();
        }

        databaseAccess.close();

        reloadActivity();

    }

    private void reloadActivity(){
        //finish();
        //startActivity(getIntent());
        Intent listarArboles = new Intent(AgregarArbolActivity.this, ArbolesActivity.class);
        listarArboles.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(listarArboles);
    }

    public void editMiArbol(){

        //int a=77;
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.openRead();

            //arbol = databaseAccess.editArboles(id_arbol_obtenido);
            arboles_model = databaseAccess.editArboles(id_arbol_obtenido);

            for(int i=0; i < arboles_model.size(); i++){

                ArbolesModel arboles_model2 = arboles_model.get(i);
                id_especie_obtenido = arboles_model2.getId_e();
                id_rancho_obtenido = arboles_model2.getId_c();
                //arboles_model2.getId_c();
                //arboles_model2.getId_e();
            }

        //Toast.makeText(this, arbol.toString(), Toast.LENGTH_SHORT).show();

        databaseAccess.close();

        //Toast.makeText(this, String.valueOf(a), Toast.LENGTH_SHORT).show();


    }



    private void msgRecomendacion(){
        final AlertDialog.Builder dialog_delete = new AlertDialog.Builder(AgregarArbolActivity.this);

        dialog_delete.setMessage("Para tener una mejor experiencia te recomendamos aceptar el permiso y activar tu GPS en el Modo ALTA PRECISION... ");
        dialog_delete.setCancelable(true);
        dialog_delete.setPositiveButton("Ir a activarlo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(AgregarArbolActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
            }
        });
        dialog_delete.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(AgregarArbolActivity.this, "Proceso cancelado", Toast.LENGTH_SHORT).show();
            }
        });

        dialog_delete.show();
    }

    private void dialogAddEmpresa(String showText){
        pdialog_progress_empresa = new ProgressDialog(AgregarArbolActivity.this);
        pdialog_progress_empresa.setCancelable(false);
        pdialog_progress_empresa.setTitle(showText);
        pdialog_progress_empresa.setMessage("Este proceso no debe tardar mucho");
        pdialog_progress_empresa.show();
        //pdialog_progress_empresa.setContentView(R.layout.);
    }

    // lLOCATION START //
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
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

    // PERMISOS //
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    // CLASE LOCATION //
    public class Localizacion implements LocationListener {
        AgregarArbolActivity agregarArbolActivity;

        public AgregarArbolActivity getMainActivity() {
            return agregarArbolActivity;
        }

        public void setMainActivity(AgregarArbolActivity mainActivity) {
            this.agregarArbolActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas debido a la deteccion de un cambio de ubicacion

            String Text = "Mi ubicacion actual es: " + loc.getLatitude() + " " + loc.getLongitude();
            lat = loc.getLatitude();
            lng = loc.getLongitude();

            edtxt_latitud.setText(String.valueOf(lat));
            edtxt_longitud.setText(String.valueOf(lng));

            // Toast.makeText(mainActivity, Text, Toast.LENGTH_SHORT).show();
            //this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            //  String gp = "GPS Desactivado";
            Toast.makeText(agregarArbolActivity, "GPS Desactivado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado

            // String gp = "GPS Activado";
            Toast.makeText(agregarArbolActivity, "GPS Activado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Toast.makeText(agregarArbolActivity, "LocationProvider.AVAILABLE\"", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Toast.makeText(agregarArbolActivity, "LocationProvider.OUT_OF_SERVICE\"", Toast.LENGTH_SHORT).show();

                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Toast.makeText(agregarArbolActivity, "LocationProvider.TEMPORARILY_UNAVAILABLE\"", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    ////////  A ////////
    public void setMap(){

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(AgregarArbolActivity.this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitud, longitud);

        /*mMap.addMarker(new MarkerOptions().position(sydney).title("AQUI"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/


        if(bandera){
            // Instantiates a new CircleOptions object and defines the center and radius
            CircleOptions circleOptions = new CircleOptions()
                    .center(sydney)
                    .radius(radio)
                    .strokeColor(Color.parseColor("#FF008000"))
                    .fillColor(Color.parseColor("#F24B3621"))
                    .strokeWidth(10)
                    .clickable(true); // In meters

            // Get back the mutable Circle
            Circle circle = mMap.addCircle(circleOptions);
        }else{
            //Toast.makeText(this, "HOLIII", Toast.LENGTH_SHORT).show();
            Bitmap imagenOriginal = BitmapFactory.decodeResource(getResources(),R.drawable.icon_marcador_arbol);
            Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal,80,80,false);

            String especie = edtxt_especie.getText().toString();
            String centro = edtxt_centro.getText().toString();

            marcador = mMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title(especie)
                    .snippet(centro)
                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_siembra))
                    .icon(BitmapDescriptorFactory.fromBitmap(imagenFinal))
            );
            marcador.setTag(0);
        }

        onGoToBondi();




    }

    private boolean checkReady() {
        if (mMap == null) {
            //Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Called when the Go To Bondi button is clicked.
     */
    public void onGoToBondi() {
        if (!checkReady()) {
            return;
        }

        BONDI =
                new CameraPosition.Builder().target(new LatLng(latitud, longitud))
                        /*.zoom(10.5f)
                        .bearing(300)
                        .tilt(50)
                        .build();*/
                        .zoom(15.5f)
                        .bearing(0)
                        .tilt(25)
                        .build();

        changeCamera(CameraUpdateFactory.newCameraPosition(BONDI));
    }

    private void changeCamera(CameraUpdate update) {
        changeCamera(update, null);
    }

    /**
     * Change the camera position by moving or animating the camera depending on the state of the
     * animate toggle button.
     */
    private void changeCamera(CameraUpdate update, GoogleMap.CancelableCallback callback) {

        // The duration must be strictly positive so we make it at least 1.
        mMap.animateCamera(update);

        //mMap.animateCamera(update, callback);

        //mMap.moveCamera(update);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

}
