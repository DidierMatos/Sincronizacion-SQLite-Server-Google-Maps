package com.mayandevelopers.pftp.views;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mayandevelopers.pftp.MainActivity;
import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccessVisitas;
import com.mayandevelopers.pftp.models.VisitasModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AgregarVisitaActivity extends AppCompatActivity {

    DatabaseAccessVisitas databaseAccessVisitas;
    LocationManager mlocManager;
    Localizacion Local;
    List<VisitasModel> visita;

    private static final int PICK_IMAGE_REQUEST = 1;
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private Bitmap bitmapImage;
    String fecha;
    int id_arbol;
    int id_visita;
    String action;
    double my_latitud;
    double my_longitud;

    String lat;
    String lng;


    FloatingActionButton floatbtn_tomar_foto;
    ImageView imgview_foto;
    ImageButton imgbtn_back;
    ImageButton imgbtn_fecha;
    TextView txt_toolbar_text;
    Button btn_cancelar, btn_guardar;
    EditText edtxt_fecha_visita, edtxt_altura, edtxt_diametro, edtxt_observaciones, edtxt_vigor, edtxt_condicion, edtxt_sanidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_visita);
        // INSTANCIA DE MI CLASE DATABASE //
        databaseAccessVisitas = DatabaseAccessVisitas.getInstance(getApplicationContext());


        floatbtn_tomar_foto = (FloatingActionButton) findViewById(R.id.floatbtnTomarFoto);
        imgview_foto        = (ImageView) findViewById(R.id.imgviewFotoVisita);
        imgbtn_back         = (ImageButton) findViewById(R.id.imgbtnBackRegVisita);
        imgbtn_fecha        = (ImageButton) findViewById(R.id.imgbtnCalendario);
        edtxt_fecha_visita  = (EditText) findViewById(R.id.edtxtFechaVisita);
        edtxt_altura        = (EditText) findViewById(R.id.edtxtAltura);
        edtxt_diametro      = (EditText) findViewById(R.id.edtxtDiametro);
        edtxt_observaciones = (EditText) findViewById(R.id.edtxtObservaciones);
        edtxt_vigor         = (EditText) findViewById(R.id.edtxtVigor);
        edtxt_condicion     = (EditText) findViewById(R.id.edtxtCondicion);
        edtxt_sanidad       = (EditText) findViewById(R.id.edtxtSanidad);
        txt_toolbar_text    = (TextView) findViewById(R.id.txt_toolbar);
        btn_cancelar        = (Button) findViewById(R.id.btnCancelarVisita);
        btn_guardar        = (Button) findViewById(R.id.btnGuardarVisita);


        // OBTENER ACCION A REALIZAR //
        action = getIntent().getStringExtra("accion");

        if (action.equals("update")){
            // OBTENER ID DE LA VISITA Y ID DEL ARBOL //
            id_visita = getIntent().getIntExtra("id_visita",0);
            id_arbol = getIntent().getIntExtra("id_arbol",0);

            // TRAER DATOS Y LA IMAGEN DE LA VISITA A EDITAR //
            traerDatosVisita(id_visita);
            txt_toolbar_text.setText("Actualizar visita");
        }else {
            // OBTENER ID DEL ARBOL //
            id_arbol = getIntent().getIntExtra("id_arbol",0);
            // permisos en el manifest //
            permisosManifest();
        }

        // GUARDAR O ACTUALIZAR VISITA //
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (action.equals("agregar")){
                    // EJECUTAR METODO PARA GUARDAR //
                    guardarVisita();

                } else if (action.equals("update")){
                    // EJECUTAR METODO ACTUALIZAR //
                    actualizarVisita(String.valueOf(id_visita));

                }
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // TOMAR FOTO //
        floatbtn_tomar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFoto();
            }
        });

        // REGRESAR A LA VISTA ANTERIOR //
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // POP UP SELECCIONAR LA FECHA //
        imgbtn_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDialog();
            }
        });


    }

    // ACTUALIZAR VISITA //
    private void actualizarVisita(String id_visita){

        String fecha = edtxt_fecha_visita.getText().toString().trim();
        String altura = edtxt_altura.getText().toString().trim();
        String diametro = edtxt_diametro.getText().toString().trim();
        String observaciones = edtxt_observaciones.getText().toString().trim();
        String vigor = edtxt_vigor.getText().toString().trim();
        String condicion = edtxt_condicion.getText().toString().trim();
        String sanidad = edtxt_sanidad.getText().toString().trim();
        //String fecha_registro = obtenerFecha();
        String fecha_actualizacion = obtenerFecha();
        //String latitud = lat;
        //String longitud = lng;


        if ( fecha.equals("") || altura.equals("") || diametro.equals("") || observaciones.equals("") || vigor.equals("") || condicion.equals("") || sanidad.equals("")) {

            Toast.makeText(AgregarVisitaActivity.this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
        }else{
            ContentValues values = new ContentValues();
            values.put("fecha_visita",fecha);
            values.put("altura",altura);
            values.put("diametro",diametro);
            values.put("observaciones",observaciones);
            values.put("vigor",vigor);
            values.put("condicion",condicion);
            values.put("sanidad",sanidad);
            values.put("fecha_actualizacion",fecha_actualizacion);

            //ACTUALIZAR Y RECUPERAR EL ID DE LA VISITA //
            int id_res_visi = databaseAccessVisitas.actualizarVisita(id_visita,values);
            //Toast.makeText(this, String.valueOf(id_res_visi), Toast.LENGTH_SHORT).show();

            // SI EL USUARIO AGREGO UNA NUEVA IMAGEN GUARDARLA //
            if (bitmapImage != null){
                eliminarImagenVisita(id_visita);
                guardarImagenVisita(id_visita);

            }else{
                terminarActividad();
            }

        }
    }

    // ELIMINAR IMAGEN DE LA VISITA //
    private void eliminarImagenVisita (String id){
        int num_reg_deleted = databaseAccessVisitas.eliminarImagenVisita(id);
        //Toast.makeText(this, String.valueOf(num_reg_deleted), Toast.LENGTH_SHORT).show();
    }

    // TRAER DATOS DE LA VISITA A ACTUALIZAR
    private void traerDatosVisita(int id_visita) {
        // consultar datos //
        visita = databaseAccessVisitas.getDatosVisitaUpdate(id_visita);

        for (int i=0; i<visita.size();i++){
            VisitasModel datos_visita = visita.get(i);

            String fecha_visita = datos_visita.getFecha_visita();
            String altura = datos_visita.getAltura();
            String diametro = datos_visita.getDiametro();
            String observaciones = datos_visita.getObservaciones();
            String vigor = datos_visita.getVigor();
            String condicion = datos_visita.getCondicion();
            String sanidad = datos_visita.getSanidad();

            edtxt_fecha_visita.setText(fecha_visita);
            edtxt_altura.setText(altura);
            edtxt_diametro.setText(diametro);
            edtxt_observaciones.setText(observaciones);
            edtxt_vigor.setText(vigor);
            edtxt_condicion.setText(condicion);
            edtxt_sanidad.setText(sanidad);
        }
        // EJECUTAR METODO//
        traerImagenVisita(id_visita);
    }

    // TRAER LA IMAGEN //
    private void  traerImagenVisita(int visita_id){

        Bitmap imagen = databaseAccessVisitas.traerImagenVisita(visita_id);
        imgview_foto.setImageBitmap(imagen);

    }

    private void permisosManifest(){
        // PERMISOS DE USO DEL GPS //
        if (ActivityCompat.checkSelfPermission(AgregarVisitaActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AgregarVisitaActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AgregarVisitaActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            obtenerUbicacion();
        }
    }

    // OBTENER UBICACION GPS //
    private void obtenerUbicacion(){

        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Local = new Localizacion();
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

    // GUARDAR LA VISITA //
    private void guardarVisita() {
        String fecha = edtxt_fecha_visita.getText().toString().trim();
        String altura = edtxt_altura.getText().toString().trim();
        String diametro = edtxt_diametro.getText().toString().trim();
        String observaciones = edtxt_observaciones.getText().toString().trim();
        String vigor = edtxt_vigor.getText().toString().trim();
        String condicion = edtxt_condicion.getText().toString().trim();
        String sanidad = edtxt_sanidad.getText().toString().trim();
        String latitud = lat;
        String longitud = lng;

        String fecha_registro = obtenerFecha();
        //String fecha_actualizacion = obtenerFecha();

        if ( bitmapImage == null ||fecha.equals("") || altura.equals("") || diametro.equals("") || observaciones.equals("") || vigor.equals("") || condicion.equals("") || sanidad.equals("")) {

            Toast.makeText(AgregarVisitaActivity.this, "Rellena todos los campos o sube una imagen", Toast.LENGTH_SHORT).show();
        }else{
           //GUARDAR Y RECUPERAR EL ID DE LA VISITA //
            String id_visita = databaseAccessVisitas.registrarVisita(fecha,altura,diametro,observaciones,vigor,condicion,sanidad,
                  String.valueOf(id_arbol),fecha_registro,latitud,longitud);

            // GUARDAR LA IMAGEN DE LA VISITA //
            guardarImagenVisita(id_visita);

        }
    }

    // GUARDAR IMAGEN DE VISITA //
    private void guardarImagenVisita(String id_visita)  {
        String origen = "3";
        String id_refe = id_visita;
        String nombre = generarNombreImagen();
        byte [] imagen = imageToByte(bitmapImage);
        String fecha_reg = obtenerFecha();

        // guardar la imagen //
        String id_regis = databaseAccessVisitas.agregarImagenVisita(origen,id_refe,nombre,imagen,fecha_reg);

        // CERRAR ACTIVIDAD //
        terminarActividad();

    }

    private void terminarActividad(){
        Intent ir = new Intent(AgregarVisitaActivity.this,VisitasActivity.class);
        ir.putExtra("id_arbol",id_arbol);
        ir.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ir);
    }

    // generar nombre unico de la imagen //
    private String generarNombreImagen(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String nombre_foto = "TREE -" + date + "- PPM.jpg";

        return nombre_foto;
    }

    // CONVERTIR IMAGEN A BYTE //
    private byte [] imageToByte(Bitmap bitmap){

        ByteArrayOutputStream stream = new ByteArrayOutputStream(20480);
        bitmap.compress(Bitmap.CompressFormat.JPEG,80, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }


   /* // PERMISOS  DE GPS//
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacion();
            }
        }
    }*/

    // Aqui empieza la Clase Localizacion //
    public class Localizacion implements LocationListener {
        AgregarVisitaActivity establecerMuestraActivity;

        public AgregarVisitaActivity getMainActivity() {
            return establecerMuestraActivity;
        }
        public void setMainActivity(AgregarVisitaActivity mainActivity) {
            this.establecerMuestraActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas debido a la deteccion de un cambio de ubicacion
            my_latitud = loc.getLatitude();
            my_longitud = loc.getLongitude();
            lat = String.valueOf(my_latitud);
            lng = String.valueOf(my_longitud);

            // REMOVER EL ECUCHADOR DE CAMBIIO DE UBICACION UNA VES QUE SE OBTENGA LA UBICACION DEL DISPOSITIVO //
            mlocManager.removeUpdates(this);

            //Toast.makeText(establecerMuestraActivity, lat+" ", Toast.LENGTH_SHORT).show();

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
            obtenerUbicacion();

            //Toast.makeText(establecerMuestraActivity, "GPS Activado", Toast.LENGTH_SHORT).show();
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

    // crear pop up //
    public void popUpDialog(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AgregarVisitaActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.popup_calendar,null);

        final TextView text_fecha = (TextView)mView.findViewById(R.id.txtFecha);
        Button btn_cancelar = (Button) mView.findViewById(R.id.btnCancelarFecha);
        Button btnguardar = (Button) mView.findViewById(R.id.btnGuardarFecha);
        CalendarView calendar_date = (CalendarView)mView.findViewById(R.id.calendarVisita);

        // obtener fecha actual //
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes  = calendar.get(Calendar.MONTH);
        int año = calendar.get(Calendar.YEAR);
        fecha = año  + "/" + (mes + 1) + "/" +dia ;

        text_fecha.setText(fecha);


        // escojer la fecha de la visita //
        calendar_date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fecha = year +"/" + (month + 1)  + "/" + dayOfMonth;
                text_fecha.setText(fecha);
            }
        });


        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtxt_fecha_visita.setText(fecha);
                dialog.dismiss();

            }
        });
    }

    // TOMAR FOTO //
    private void tomarFoto() {

        // COMPROBAR VERSION DE ANDROID //
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (validaPermisos()) {
                generateCropimage();
            }
        }else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Selecciona una imagen"), PICK_IMAGE_REQUEST);

        }
    }

    // VALIDAR SI EL USUARIO DA PERMISOS DE ACCEDER A LA CAMARA //
    private boolean validaPermisos() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }
        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }
        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }
        return false;
    }

    // MENSAJE A USUARIO //
    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(AgregarVisitaActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
                }
            }
        });
        dialogo.show();
    }

    // CORTAR IMAGEN //
    private void generateCropimage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CropImage.activity(null)
                    .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                    //.setBackgroundColor(getColor(R.color.colorcards))
                    .setCropShape(CropImageView.CropShape.OVAL)
                    //.setFixAspectRatio(false)
                    .setScaleType(CropImageView.ScaleType.CENTER_CROP)
                    .setRequestedSize(500, 500, CropImageView.RequestSizeOptions.RESIZE_INSIDE)
                    //.setActivityTitle("Editor")

                    //.setCropMenuCropButtonIcon(R.drawable.crop_image_menu_rotate_left)
                    .setActivityMenuIconColor(getColor(R.color.colorBotonVerde))
                    .start(AgregarVisitaActivity.this);
        }
    }

    // PERMISOS OTORGADOS //
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacion();
            }
        }

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(requestCode == 100) {

                if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                        && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                    generateCropimage();
                    //botonCargar.setEnabled(true);
                }else{
                    //solicitarPermisosManual();
                    //toastPersonalizado("Fallo");
                    Toast.makeText(this, "Fallo", Toast.LENGTH_SHORT).show();
                }

            }else{
                //toastPersonalizado("No aceptaste los permisos");
                Toast.makeText(this, "No aceptaste los permisos", Toast.LENGTH_SHORT).show();
            }
        }else {

            if(requestCode == MY_PERMISSIONS){
                if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(AgregarVisitaActivity.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                    floatbtn_tomar_foto.setEnabled(true);
                }
            }else{
                showExplanation();
            }
        }
    }

    // MENSAJE A USUARIO //
    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AgregarVisitaActivity.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

    // SETEAR IMAGEN EN EL IMAGE VIEW //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            // handle result of CropImageActivity
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    try {
                       /*Uri imageUri = data.getData();
                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        CropImage.activity(imageUri)
                                .setActivityMenuIconColor(Color.GREEN)
                                .start(Agregar_EmpresaActivity.this);
                    }*/
                        bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                        imgview_foto.setImageBitmap(bitmapImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());

                    // Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    //toastPersonalizado("Error al cortar la imagen" + result.getError().toString());
                    Toast.makeText(this, "Error al cortar imagen", Toast.LENGTH_SHORT).show();
                } else {
                    //toastPersonalizado("Proceso cancelado");
                    Toast.makeText(this, "Proceso cancelado", Toast.LENGTH_SHORT).show();
                }
            }
        }else{

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
                Uri filePath = data.getData();

                try{
                    ///OBTENER EL MAPA DE BITS
                    bitmapImage= MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
                    ///CONFIGURACION DEL MAPA DE BITS EN EL IMAGE VIEW
                    imgview_foto.setImageBitmap(bitmapImage);

                } catch (IOException e){
                    e.printStackTrace();

                }
            }
        }
    }
}
