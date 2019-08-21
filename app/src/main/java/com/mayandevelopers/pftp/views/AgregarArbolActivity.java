package com.mayandevelopers.pftp.views;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mayandevelopers.pftp.R;

public class AgregarArbolActivity extends AppCompatActivity {

    String valorUpdate;

    TextView txtview_add_arbol;
    ImageButton imgbtn_ubicacion;
    Button imgbtn_add_arbol;
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

    }
}
