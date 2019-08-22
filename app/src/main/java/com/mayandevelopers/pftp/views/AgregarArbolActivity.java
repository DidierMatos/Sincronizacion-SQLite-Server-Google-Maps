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
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mayandevelopers.pftp.R;

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
    //ImageButton imgbtn_ubicacion;
    Button imgbtn_add_arbol, imgbtn_ubicacion;
    TextInputEditText edtxt_especie, edtxt_centro, edtxt_folio, edtxt_latitud, edtxt_longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_arbol);

        txtview_add_arbol = findViewById(R.id.txtviewAddArbol);
        edtxt_especie = findViewById(R.id.edtxtEspecie);
        edtxt_centro = findViewById(R.id.edtxtCentro);
        edtxt_folio = findViewById(R.id.edtxtLatitud);
        edtxt_latitud = findViewById(R.id.edtxtFolio);
        edtxt_longitud = findViewById(R.id.edtxtLongitud);
        imgbtn_add_arbol = findViewById(R.id.btnAddArbol);
        //imgbtn_ubicacion = findViewById(R.id.imgbtnUbicacionAddArbol);


        valorUpdate = getIntent().getStringExtra("update");

        if (valorUpdate == null){


        }else{
            imgbtn_add_arbol.setText("Actualizar");
            txtview_add_arbol.setText("Actualizar Arbol");

        }

        //edtxt_longitud = findViewById(R.id.edtxtlongitud);
        //edtxt_latitud = findViewById(R.id.edtxtlatitud);
        //edtxt_radio = findViewById(R.id.edtxtradio);
        //btn_buscar = findViewById(R.id.btnbuscar);
        //btn_buscar2 = findViewById(R.id.btnbuscar2);

        edtxt_latitud.setText("-99.185594");
        edtxt_longitud.setText("19.419289");
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

                latitud = Double.parseDouble(edtxt_latitud.getText().toString());
                longitud = Double.parseDouble(edtxt_longitud.getText().toString());

                setMap();

            }
        });



    }

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
        LatLng sydney = new LatLng(longitud, latitud);

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

            Bitmap imagenOriginal = BitmapFactory.decodeResource(getResources(),R.drawable.icon_marcador_arbol);
            Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal,80,80,false);

            marcador = mMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title("ARBOL 1")
                    .snippet("RANCHO 2")
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
                new CameraPosition.Builder().target(new LatLng(longitud, latitud))
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
