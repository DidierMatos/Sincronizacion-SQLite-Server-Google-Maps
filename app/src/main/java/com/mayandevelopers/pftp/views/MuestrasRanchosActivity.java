package com.mayandevelopers.pftp.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.controllers.RvMuestrasController;
import com.mayandevelopers.pftp.controllers.RvRanchosMuestrasController;
import com.mayandevelopers.pftp.models.MuestrasModel;
import com.mayandevelopers.pftp.models.RanchosModel;

import java.util.ArrayList;
import java.util.List;

public class MuestrasRanchosActivity extends AppCompatActivity {

    List<MuestrasModel> muestrasModel;
    RvMuestrasController rv_muestras_controller;

    RecyclerView rv_muestras;
    ImageButton imgbtn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestras_ranchos);

        rv_muestras = (RecyclerView) findViewById(R.id.rvMuestras);
        imgbtn_back = (ImageButton) findViewById(R.id.imgbtnMuestras);

        // REGRESAR A LA ACTIVIDAD ANTERIOR //
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
