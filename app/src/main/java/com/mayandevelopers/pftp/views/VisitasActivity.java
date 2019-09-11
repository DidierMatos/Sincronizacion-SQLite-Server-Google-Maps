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
import android.widget.Toast;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.controllers.RvVisitasController;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccessMuestras;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccessVisitas;
import com.mayandevelopers.pftp.models.VisitasModel;

import java.util.ArrayList;
import java.util.List;

public class VisitasActivity extends AppCompatActivity {

    DatabaseAccessVisitas databaseAccessVisitas;


    List<VisitasModel> visitasModel;
    RvVisitasController rv_visitas_controller;
    RecyclerView rv_visitas;

    int id_arbol;


    FloatingActionButton flt_action_btn_add;
    Toolbar toolbar;
    ImageButton imgbtn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitas);
        // OBTENER INSTANCIA DE MI CLASE DATABASE ACCESS //
        databaseAccessVisitas = DatabaseAccessVisitas.getInstance(getApplicationContext());

        rv_visitas = (RecyclerView) findViewById(R.id.rvVisitas);
        flt_action_btn_add = (FloatingActionButton) findViewById(R.id.flactbtnVisitas);
        imgbtn_back = findViewById(R.id.imgbtnBackVisitas);

        // OBTENER ID DEL ARBOL PARA DESPLEGRAR SUS VISITAS //
        id_arbol = getIntent().getIntExtra("id_arbol",0);
        //Toast.makeText(VisitasActivity.this, String.valueOf(id_arbol), Toast.LENGTH_SHORT).show();

        // DESPLEGAR LAS VISITAS DEL ARBOL //
        rv_visitas_controller = new RvVisitasController(VisitasActivity.this,databaseAccessVisitas.getVisitasArboles(id_arbol));
        rv_visitas.setLayoutManager(new LinearLayoutManager(VisitasActivity.this, RecyclerView.VERTICAL,false));
        rv_visitas.setAdapter(rv_visitas_controller);


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
                add_visita.putExtra("id_arbol", id_arbol);
                add_visita.putExtra("accion","agregar");
                startActivity(add_visita);
            }
        });







    }
}
