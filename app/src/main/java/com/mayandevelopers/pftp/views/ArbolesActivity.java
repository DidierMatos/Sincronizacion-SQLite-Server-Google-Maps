package com.mayandevelopers.pftp.views;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.controllers.RvArbolesController;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccess;
import com.mayandevelopers.pftp.models.ArbolesModel;

import java.util.ArrayList;

public class ArbolesActivity extends AppCompatActivity {


    ArrayList<ArbolesModel> arboles_model;
    RecyclerView rv_mis_arboles;
    RvArbolesController rv_arboles_controller;

    Toolbar toolbar;
    ImageButton imgbtn_back;

    int id_especie_obtenido, id_rancho_obtenido;
    String nombre_especie_obtenido, nombre_rancho_obtenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arboles);
        toolbar = (Toolbar) findViewById(R.id.toolbarArboles);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        rv_mis_arboles = findViewById(R.id.rvMisArboles);
        imgbtn_back = findViewById(R.id.imgbtnBackRanchos);
        
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        
        SharedPreferences prefs = getSharedPreferences("ESPECIE_SELECCIONADA", MODE_PRIVATE);
        id_especie_obtenido = prefs.getInt("id_especie", 77);
        nombre_especie_obtenido = prefs.getString("nombre_especie",null);
        //Toast.makeText(this, nombre_especie_obtenido, Toast.LENGTH_SHORT).show();

        SharedPreferences prefs2 = getSharedPreferences("RANCHO_SELECCIONADO", MODE_PRIVATE);
        id_rancho_obtenido = prefs2.getInt("id_rancho", 77);
        //Toast.makeText(this, "id_centro: "+id_rancho_obtenido, Toast.LENGTH_SHORT).show();
        nombre_rancho_obtenido = prefs2.getString("nombre_rancho",null);

  /*      id_rancho_obtenido = getIntent().getIntExtra("id_rancho", 77);
        //Toast.makeText(this, "id_centro: "+id_rancho_obtenido, Toast.LENGTH_SHORT).show();
        nombre_rancho_obtenido = getIntent().getStringExtra("nombre_rancho");
        //Toast.makeText(this,"id_especie: "+ id_especie_obtenida, Toast.LENGTH_LONG).show();*/

        //nombre_rancho_obtenido = getIntent().getStringExtra("nombre_rancho");
        Toast.makeText(this, "id_especie: " + id_especie_obtenido + " nombre_especie: " + nombre_especie_obtenido + " id_rancho: " + id_rancho_obtenido + " nombre_rancho: " + nombre_rancho_obtenido, Toast.LENGTH_SHORT).show();

        arboles_model = new ArrayList<>();

        loadMisArboles();

        /*for (int i = 0; i< 5; i++){

            //AGREGANDO LO NUEVO A LA LISTA  //
            //arboles_model.add(new ArbolesModel("1"));


            //ASIGNANDO ADAPTADOR AL RECYCLER VIEW//
            rv_arboles_controller = new RvArbolesController(ArbolesActivity.this,arboles_model);
            rv_mis_arboles.setLayoutManager(new LinearLayoutManager(ArbolesActivity.this, LinearLayoutManager.VERTICAL,false ));
            rv_mis_arboles.setAdapter(rv_arboles_controller);
        }*/


       /* Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        FloatingActionButton fab = findViewById(R.id.fabAddArbol);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            Intent intent = new Intent(ArbolesActivity.this, AgregarArbolActivity.class);
    /*        intent.putExtra("id_especie",id_especie_obtenido);
            intent.putExtra("nombre_especie",nombre_especie_obtenido);
            intent.putExtra("id_rancho",id_rancho_obtenido);
            intent.putExtra("nombre_rancho", nombre_rancho_obtenido);*/
            startActivity(intent);
            finish();
            }
        });



    }

    public void loadMisArboles(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.openRead();

        rv_arboles_controller = new RvArbolesController(ArbolesActivity.this, databaseAccess.getArboles(id_especie_obtenido, id_rancho_obtenido));
        rv_mis_arboles.setLayoutManager(new LinearLayoutManager(ArbolesActivity.this, RecyclerView.VERTICAL,false ));
        rv_mis_arboles.setAdapter(rv_arboles_controller);

        databaseAccess.close();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_options, menu);

        MenuItem menuItem = menu.findItem(R.id.find);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Escribe algo");
        MenuItemCompat.getActionView(menuItem);
        // searchView.setOnQueryTextListener(this);

        return true;
    }
}
