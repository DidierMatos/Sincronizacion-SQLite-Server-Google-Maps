package com.mayandevelopers.pftp.views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;
import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.controllers.RvRanchosController;
import com.mayandevelopers.pftp.controllers.RvRanchosMuestrasController;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccessRanchos;
import com.mayandevelopers.pftp.models.RanchosModel;

import java.util.ArrayList;
import java.util.List;

public class MuestrasActivity extends AppCompatActivity {

    List<RanchosModel> ranchosModel;
    RvRanchosMuestrasController rv_ranchos_muestras_controller;

    RecyclerView rv_ranchos_muestras;
    ImageButton imgbtn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestras);

        rv_ranchos_muestras = (RecyclerView) findViewById(R.id.rvRanchosMuestras);
        imgbtn_back         = (ImageButton) findViewById(R.id.imgbtnRanchoMuestra);

        // REGRESAR A VISTA ANTERIOR //
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        DatabaseAccessRanchos databaseAccessRanchos = DatabaseAccessRanchos.getInstance(getApplicationContext());

        //ASIGNANDO ADAPTADOR AL RECYCLER VIEW//
        rv_ranchos_muestras_controller = new RvRanchosMuestrasController(MuestrasActivity.this,databaseAccessRanchos.obtenerRanchos());
        rv_ranchos_muestras.setLayoutManager(new LinearLayoutManager(MuestrasActivity.this, LinearLayoutManager.VERTICAL,false ));
        rv_ranchos_muestras.setAdapter(rv_ranchos_muestras_controller);

       /* for (int i = 0; i<4; i++){

            ranchosModel.add(new RanchosModel(1,"Rancho 1"));
            rv_ranchos_muestras_controller = new RvRanchosMuestrasController(MuestrasActivity.this,ranchosModel);
            rv_ranchos_muestras.setLayoutManager(new LinearLayoutManager(MuestrasActivity.this, LinearLayoutManager.VERTICAL,false));
            rv_ranchos_muestras.setAdapter(rv_ranchos_muestras_controller);
        }*/


    }
}
