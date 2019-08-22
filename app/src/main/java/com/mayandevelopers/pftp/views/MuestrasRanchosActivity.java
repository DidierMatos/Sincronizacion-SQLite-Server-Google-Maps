package com.mayandevelopers.pftp.views;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.controllers.RvMuestrasController;
import com.mayandevelopers.pftp.models.MuestrasModel;

import java.util.ArrayList;
import java.util.List;

public class MuestrasRanchosActivity extends AppCompatActivity {

    List<MuestrasModel> muestrasModel;
    RvMuestrasController rv_muestras_controller;

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
                startActivity(establecer_muestra);
            }
        });

        muestrasModel = new ArrayList<>();

        for (int i = 0; i<6; i++){
            muestrasModel.add(new MuestrasModel(1,"muestra 1","2019-09-12"));

            rv_muestras_controller = new RvMuestrasController(MuestrasRanchosActivity.this,muestrasModel);
            rv_muestras.setLayoutManager(new LinearLayoutManager(MuestrasRanchosActivity.this,LinearLayoutManager.VERTICAL, false));
            rv_muestras.setAdapter(rv_muestras_controller);

        }
    }
}
