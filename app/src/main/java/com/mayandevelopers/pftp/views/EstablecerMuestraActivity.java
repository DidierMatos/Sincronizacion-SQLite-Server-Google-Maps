package com.mayandevelopers.pftp.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.mayandevelopers.pftp.R;

public class EstablecerMuestraActivity extends AppCompatActivity {

    ImageButton imgbtn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establecer_muestra);

        imgbtn_back = (ImageButton) findViewById(R.id.imgbtnEstablecerMuestra);

        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
