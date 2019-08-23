package com.mayandevelopers.pftp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mayandevelopers.pftp.controllers.RvEspeciesController;
import com.mayandevelopers.pftp.models.EspeciesModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    NavigationView navigationView;
    List<EspeciesModel> especiesModel;
    RecyclerView rv_especies;
    RvEspeciesController rv_especies_controller;
    FloatingActionButton flt_action_btn_add;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        rv_especies =(RecyclerView) findViewById(R.id.rvEspecies);
        flt_action_btn_add=(FloatingActionButton) findViewById(R.id.flactbtnEspeciesMain);



        // MENU DRAWER LAYOUT///
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        especiesModel = new ArrayList<>();

        for (int i = 0; i< 5; i++){

            //AGREGANDO LO NUEVO A LA LISTA  //
            especiesModel.add(new EspeciesModel(1,"Caoba"));


            //ASIGNANDO ADAPTADOR AL RECYCLER VIEW//
            rv_especies_controller = new RvEspeciesController(MainActivity.this, (ArrayList<EspeciesModel>) especiesModel);
            rv_especies.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false ));
            rv_especies.setAdapter(rv_especies_controller);
        }
        flt_action_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDialog();
            }
        });



    }

    // crear pop up //
    public void popUpDialog(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.popup_especies_name,null);

        EditText edtxt_nombre = (EditText) mView.findViewById(R.id.edtxtNombreMain);
        Button btn_cancelar = (Button) mView.findViewById(R.id.btnCancelarMain);
        Button btnguardar = (Button) mView.findViewById(R.id.btnGuardarMain);


        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        return false;
    }
}
