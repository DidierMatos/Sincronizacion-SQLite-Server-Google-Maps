package com.mayandevelopers.pftp.views;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
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

public class VisitasActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {

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
        toolbar = (Toolbar) findViewById(R.id.toolbarVisitas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

    // IMFLAR EL MENU DE LA TOOLBAR //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_options, menu);

        MenuItem menuItem = menu.findItem(R.id.find);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Escribe una fecha");
        MenuItemCompat.getActionView(menuItem);
        // searchView.setOnQueryTextListener(this);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    // EJECUTAR METODO AL ESCRIBIR //
    @Override
    public boolean onQueryTextChange(String newText) {
        // CREAR NUEVO ARRAY LIST PARA ALMACENAR LAS VISITAS ENCONTRADAS //
        ArrayList<VisitasModel> visitas = new ArrayList<>();

        for (VisitasModel visitasItem : databaseAccessVisitas.getVisitasArboles(id_arbol) ){
            if (visitasItem.getFecha_registro().toLowerCase().contains(newText.toLowerCase())){
                visitas.add(visitasItem);
            }
        }
        // DESPLEGAR LAS VISITAS DEL ARBOL //
        rv_visitas_controller = new RvVisitasController(VisitasActivity.this,visitas);
        rv_visitas.setLayoutManager(new LinearLayoutManager(VisitasActivity.this, RecyclerView.VERTICAL,false));
        rv_visitas.setAdapter(rv_visitas_controller);

        return true;
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        return false;
    }
}
