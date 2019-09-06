package com.mayandevelopers.pftp.views;
import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.controllers.RvRanchosController;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccess;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccessRanchos;
import com.mayandevelopers.pftp.models.RanchosModel;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RanchosActivity extends AppCompatActivity {

    RvRanchosController rv_ranchos_controller;

    RecyclerView rv_ranchos;
    ImageButton imgbtn_back;

    int id_miespecie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranchos);

        rv_ranchos = (RecyclerView) findViewById(R.id.rvRanchos);
        imgbtn_back = (ImageButton) findViewById(R.id.imgbtnBackRanchos);

        //String id_miespecie;
        //Bundle parametros = this.getIntent().getExtras();

        id_miespecie = getIntent().getIntExtra("id_miespecie",0);

        //id_miespecie = parametros.getString("id_miespecie");
        //Toast.makeText(this, String.valueOf(id_miespecie) , Toast.LENGTH_SHORT).show();
        //Log.i("TEST",id_miespecie);

        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadMisRanchos();



    }


    public void loadMisRanchos(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.openRead();

        rv_ranchos_controller = new RvRanchosController(RanchosActivity.this, databaseAccess.getRanchos());
        rv_ranchos.setLayoutManager(new LinearLayoutManager(RanchosActivity.this, RecyclerView.VERTICAL,false ));
        rv_ranchos.setAdapter(rv_ranchos_controller);

        databaseAccess.close();
    }
}
