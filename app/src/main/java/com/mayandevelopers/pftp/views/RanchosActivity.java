package com.mayandevelopers.pftp.views;
import com.mayandevelopers.pftp.MainActivity;
import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.controllers.RvEspeciesController;
import com.mayandevelopers.pftp.controllers.RvRanchosController;
import com.mayandevelopers.pftp.models.EspeciesModel;
import com.mayandevelopers.pftp.models.RanchosModel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;


public class RanchosActivity extends AppCompatActivity {

    List<RanchosModel> ranchosModel;
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



        ranchosModel =  new ArrayList<>();
        for (int i = 0; i<6; i++){
            ranchosModel.add(new RanchosModel(1,"rancho 1"));

            //ASIGNANDO ADAPTADOR AL RECYCLER VIEW//
            rv_ranchos_controller = new RvRanchosController(RanchosActivity.this, (ArrayList<RanchosModel>) ranchosModel);
            rv_ranchos.setLayoutManager(new LinearLayoutManager(RanchosActivity.this, LinearLayoutManager.VERTICAL,false ));
            rv_ranchos.setAdapter(rv_ranchos_controller);

        }
    }
}
