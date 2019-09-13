package com.mayandevelopers.pftp.views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
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

public class MuestrasActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    DatabaseAccessRanchos databaseAccessRanchos;

    List<RanchosModel> ranchosModel;
    RvRanchosMuestrasController rv_ranchos_muestras_controller;

    RecyclerView rv_ranchos_muestras;
    ImageButton imgbtn_back;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestras);
        toolbar = (Toolbar) findViewById(R.id.toolbarRanchoMuestra);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        rv_ranchos_muestras = (RecyclerView) findViewById(R.id.rvRanchosMuestras);
        imgbtn_back         = (ImageButton) findViewById(R.id.imgbtnRanchoMuestra);

        // REGRESAR A VISTA ANTERIOR //
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        databaseAccessRanchos = DatabaseAccessRanchos.getInstance(getApplicationContext());

        //ASIGNANDO ADAPTADOR AL RECYCLER VIEW//
        rv_ranchos_muestras_controller = new RvRanchosMuestrasController(MuestrasActivity.this,databaseAccessRanchos.obtenerRanchos());
        rv_ranchos_muestras.setLayoutManager(new LinearLayoutManager(MuestrasActivity.this, RecyclerView.VERTICAL,false ));
        rv_ranchos_muestras.setAdapter(rv_ranchos_muestras_controller);



    }

    // IMFLAR EL MENU DE LA TOOLBAR //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_options, menu);

        MenuItem menuItem = menu.findItem(R.id.find);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Nombre del centro");
        MenuItemCompat.getActionView(menuItem);
        // searchView.setOnQueryTextListener(this);
        searchView.setOnQueryTextListener(this);
        //searchView.setOnSuggestionListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        // CREAR NUEVO ARRAY LIST PARA ALMACENAR LAS VISITAS ENCONTRADAS //
        ArrayList<RanchosModel> ranchos = new ArrayList<>();

        for (RanchosModel ranchosItem : databaseAccessRanchos.obtenerRanchos() ){
            if (ranchosItem.getNombreRancho().toLowerCase().contains(newText.toLowerCase())){
                ranchos.add(ranchosItem);
            }
        }

        //ASIGNANDO ADAPTADOR AL RECYCLER VIEW//
        rv_ranchos_muestras_controller = new RvRanchosMuestrasController(MuestrasActivity.this,ranchos);
        rv_ranchos_muestras.setLayoutManager(new LinearLayoutManager(MuestrasActivity.this, RecyclerView.VERTICAL,false ));
        rv_ranchos_muestras.setAdapter(rv_ranchos_muestras_controller);

        return true;
    }
}
