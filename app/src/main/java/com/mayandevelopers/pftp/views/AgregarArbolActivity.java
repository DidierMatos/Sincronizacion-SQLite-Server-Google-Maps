package com.mayandevelopers.pftp.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mayandevelopers.pftp.R;

public class AgregarArbolActivity extends AppCompatActivity {

    String valorUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_arbol);

        valorUpdate = getIntent().getStringExtra("update");

    }
}
