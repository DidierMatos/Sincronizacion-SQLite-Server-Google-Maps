package com.mayandevelopers.pftp.views;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mayandevelopers.pftp.MainActivity;
import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.controllers.RvArbolesController;
import com.mayandevelopers.pftp.models.ArbolesModel;

import java.util.ArrayList;
import java.util.List;

public class ArbolesActivity extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<ArbolesModel> arboles_model;
    RecyclerView rv_mis_arboles;
    RvArbolesController rv_arboles_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arboles);

        rv_mis_arboles = findViewById(R.id.rvMisArboles);


        arboles_model = new ArrayList<>();


        toolbar = (Toolbar) findViewById(R.id.toolbarArboles);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        for (int i = 0; i< 5; i++){

            //AGREGANDO LO NUEVO A LA LISTA  //
            arboles_model.add(new ArbolesModel("1"));


            //ASIGNANDO ADAPTADOR AL RECYCLER VIEW//
            rv_arboles_controller = new RvArbolesController(ArbolesActivity.this, (ArrayList<ArbolesModel>) arboles_model);
            rv_mis_arboles.setLayoutManager(new LinearLayoutManager(ArbolesActivity.this, LinearLayoutManager.VERTICAL,false ));
            rv_mis_arboles.setAdapter(rv_arboles_controller);
        }


       /* Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        FloatingActionButton fab = findViewById(R.id.fabAddArbol);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            Intent intent = new Intent(ArbolesActivity.this, AgregarArbolActivity.class);
            startActivity(intent);
            finish();
            }
        });



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
