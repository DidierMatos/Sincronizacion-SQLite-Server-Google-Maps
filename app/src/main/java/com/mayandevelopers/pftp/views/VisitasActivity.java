package com.mayandevelopers.pftp.views;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.controllers.RvVisitasController;
import com.mayandevelopers.pftp.models.VisitasModel;

import java.util.ArrayList;
import java.util.List;

public class VisitasActivity extends AppCompatActivity {


    List<VisitasModel> visitasModel;
    RvVisitasController rv_visitas_controller;
    RecyclerView rv_visitas;


    FloatingActionButton flt_action_btn_add;
    Toolbar toolbar;
    ImageButton imgbtn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitas);

        rv_visitas = (RecyclerView) findViewById(R.id.rvVisitas);
        flt_action_btn_add = (FloatingActionButton) findViewById(R.id.flactbtnVisitas);
        imgbtn_back = findViewById(R.id.imgbtnBackVisitas);

        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        flt_action_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_visita = new Intent(VisitasActivity.this,AgregarVisitaActivity.class);
                startActivity(add_visita);
            }
        });



        visitasModel = new ArrayList<>();
        for (int i = 0; i<6; i++){

            visitasModel.add(new VisitasModel(1,"Visita 1: 2019-12-09"));

            rv_visitas_controller = new RvVisitasController(VisitasActivity.this,visitasModel);
            rv_visitas.setLayoutManager(new LinearLayoutManager(VisitasActivity.this, LinearLayoutManager.VERTICAL,false));
            rv_visitas.setAdapter(rv_visitas_controller);
        }
    }
}
