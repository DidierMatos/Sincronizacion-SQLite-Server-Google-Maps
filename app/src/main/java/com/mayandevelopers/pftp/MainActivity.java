package com.mayandevelopers.pftp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;
import com.mayandevelopers.pftp.controllers.RvEspeciesController;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccessEspecies;
import com.mayandevelopers.pftp.models.EspeciesModel;
import com.mayandevelopers.pftp.views.BuscarFolioActivity;
import com.mayandevelopers.pftp.views.LoginActivity;
import com.mayandevelopers.pftp.views.MuestrasActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private List<EspeciesModel> especiesModel;
    private RecyclerView rv_especies;
    private MenuItem item;
    private RvEspeciesController rv_especies_controller;
    private FloatingActionButton flt_action_btn_add;
    private SQLiteOpenHelper openHelper;

    private String fecha_registro;
    private String fecha_actualizacion;

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
        //txtview_get_especies = findViewById(R.id.txtviewGetEspeciesPrueba);

        // MENU DRAWER LAYOUT///
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        // set checked true //
        item = navigationView.getMenu().findItem(R.id.nav_crecimiento);
        item.setChecked(true);

        loadMisEspecies();

        flt_action_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDialog();
            }
        });
        /*DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.openRead();
        especiesModel = databaseAccess.getEspecies2();
        databaseAccess.close();*/


    }

    public void loadMisEspecies(){

        DatabaseAccessEspecies databaseAccess = DatabaseAccessEspecies.getInstance(getApplicationContext());

        rv_especies_controller = new RvEspeciesController(MainActivity.this, databaseAccess.getEspecies());
        rv_especies.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL,false ));
        rv_especies.setAdapter(rv_especies_controller);
    }

    // crear pop up //
    public void popUpDialog(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.popup_especies_name,null);

        CalendarView calendar = (CalendarView) mView.findViewById(R.id.calendarVisita);

        final EditText edtxt_nombre = (EditText) mView.findViewById(R.id.edtxtNombreMain);

        Button btn_cancelar = (Button) mView.findViewById(R.id.btnCancelarMain);
        Button btn_guardar = (Button) mView.findViewById(R.id.btnGuardarMain);


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

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseAccessEspecies databaseAccess = DatabaseAccessEspecies.getInstance(getApplicationContext());

                String nombre_especie = edtxt_nombre.getText().toString().trim();
                fecha_registro = obtenerFecha();

                if(nombre_especie == null || nombre_especie.equals("")){
                    Toast.makeText(MainActivity.this, "Ingresa un nombre valido", Toast.LENGTH_SHORT).show();

                }else{
                    databaseAccess.addEspecies(nombre_especie, fecha_registro);
                    dialog.dismiss();
                }

                loadMisEspecies();
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
        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    // IMPLEMENTACION DEL BUSCADOR //
    @Override
    public boolean onQueryTextChange(String newText) {
        // CREAR NUEVO ARRAY LIST PARA ALMACENAR LA ESPECIE ENCONTRADA //
        ArrayList<EspeciesModel> especie = new ArrayList<>();
        DatabaseAccessEspecies databaseAccess = DatabaseAccessEspecies.getInstance(getApplicationContext());

        for (EspeciesModel especieItem : databaseAccess.getEspecies() ){
            if (especieItem.getNombreEspecie().toLowerCase().contains(newText.toLowerCase())){
                especie.add(especieItem);
            }
        }
        rv_especies_controller = new RvEspeciesController(MainActivity.this, especie);
        rv_especies.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL,false ));
        rv_especies.setAdapter(rv_especies_controller);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        // Handle navigation view item clicks here.
        switch (menuItem.getItemId()){
            case R.id.nav_crecimiento:

                break;

            case R.id.nav_muestras:
                Intent muestras = new Intent(this, MuestrasActivity.class);
                startActivity(muestras);
                break;

            case R.id.nav_sesion:
                Intent logOut = new Intent(this, LoginActivity.class);
                clearSessionVariables();

                startActivity(logOut);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    ///// LIMPIAR VARIABLE DE SESION//
    private void clearSessionVariables(){
        //BORRAR DATOS DE LAS VARIABLES DE SESION///
        SharedPreferences preferences =getSharedPreferences("myPrefsLogin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    private String obtenerFecha (){
        // obtener fecha actual //
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes  = calendar.get(Calendar.MONTH);
        int año = calendar.get(Calendar.YEAR);
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);
        int segundo = calendar.get(Calendar.SECOND);

        String fecha = año + "-" + (mes + 1) + "-" + dia + " " + hora + ":" + minuto + ":" + segundo;

        return fecha;
    }

}
