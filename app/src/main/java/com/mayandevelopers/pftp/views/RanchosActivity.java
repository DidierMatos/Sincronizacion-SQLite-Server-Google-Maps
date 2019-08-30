package com.mayandevelopers.pftp.views;
import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.controllers.RvRanchosController;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccessRanchos;
import com.mayandevelopers.pftp.models.RanchosModel;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;


public class RanchosActivity extends AppCompatActivity {

    RvRanchosController rv_ranchos_controller;

    RecyclerView rv_ranchos;
    ImageButton imgbtn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranchos);

        rv_ranchos = (RecyclerView) findViewById(R.id.rvRanchos);
        imgbtn_back = (ImageButton) findViewById(R.id.imgbtnBackRanchos);


        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // INSTANCIA DE MI CLASE DATABASE ACCES //
        DatabaseAccessRanchos databaseAccessRanchos = DatabaseAccessRanchos.getInstance(getApplicationContext());

        //OBTENIENDO LOS RANCHOS Y ASIGNANDO ADAPTADOR AL RECYCLER VIEW//
        rv_ranchos_controller = new RvRanchosController(RanchosActivity.this,databaseAccessRanchos.obtenerRanchos());
        rv_ranchos.setLayoutManager(new LinearLayoutManager(RanchosActivity.this, RecyclerView.VERTICAL,false ));
        rv_ranchos.setAdapter(rv_ranchos_controller);


    }
}
