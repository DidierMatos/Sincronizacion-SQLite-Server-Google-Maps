package com.mayandevelopers.pftp.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.mayandevelopers.pftp.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AgregarVisitaActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private Bitmap bitmapImage;

    FloatingActionButton floatbtn_tomar_foto;
    ImageView imgview_foto;
    ImageButton imgbtn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_visita);

        floatbtn_tomar_foto = (FloatingActionButton) findViewById(R.id.floatbtnTomarFoto);
        imgview_foto        = (ImageView) findViewById(R.id.imgviewFotoVisita);
        imgbtn_back = (ImageButton) findViewById(R.id.imgbtnBackRegVisita);

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
