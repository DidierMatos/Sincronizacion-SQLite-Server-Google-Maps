package com.mayandevelopers.pftp.views;

import android.content.Context;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.controllers.RvMuestrasController;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccessMuestras;
import com.mayandevelopers.pftp.models.MuestrasModel;

import java.util.ArrayList;
import java.util.List;

public class MuestrasRanchosActivity extends AppCompatActivity {

    List<MuestrasModel> muestrasModel;
    RvMuestrasController rv_muestras_controller;

    String id_centro;

    RecyclerView rv_muestras;
    ImageButton imgbtn_back;
    FloatingActionButton floatbtn_establecer_muestra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestras_ranchos);

        rv_muestras = (RecyclerView) findViewById(R.id.rvMuestras);
        imgbtn_back = (ImageButton) findViewById(R.id.imgbtnMuestras);
        floatbtn_establecer_muestra = findViewById(R.id.floatbtnAgregarMuestra);

        // OBTENER EL ID DEL RANCHO SELECCIONADO //
        SharedPreferences prefs = getSharedPreferences("idRancho", Context.MODE_PRIVATE);
        id_centro = prefs.getString("idRancho","");



        DatabaseAccessMuestras databaseAccessMuestras = DatabaseAccessMuestras.getInstance(getApplicationContext());


        // REGRESAR A LA ACTIVIDAD ANTERIOR //
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        floatbtn_establecer_muestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent establecer_muestra = new Intent(MuestrasRanchosActivity.this, EstablecerMuestraActivity.class);
                establecer_muestra.putExtra("id_centro", id_centro);
                establecer_muestra.putExtra("accion","create");
                startActivity(establecer_muestra);
            }
        });


        // MOSTRAR LAS MUESTRAS //
        rv_muestras_controller = new RvMuestrasController(MuestrasRanchosActivity.this,databaseAccessMuestras.getMuestras(id_centro));
        rv_muestras.setLayoutManager(new LinearLayoutManager(MuestrasRanchosActivity.this, RecyclerView.VERTICAL, false));
        rv_muestras.setAdapter(rv_muestras_controller);


    }
}
