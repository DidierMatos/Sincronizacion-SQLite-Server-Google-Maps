package com.mayandevelopers.pftp.views;

import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mayandevelopers.pftp.MainActivity;
import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccess;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccessLogin;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText edtxt_correo, edtxt_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btnEntrarLogin);
        edtxt_correo = findViewById(R.id.edtxtCorreoLogin);
        edtxt_pass = findViewById(R.id.edtxtPasswordLogin);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo = edtxt_correo.getText().toString();
                String pass = edtxt_pass.getText().toString();

                DatabaseAccessLogin databaseAccessLogin = DatabaseAccessLogin.getInstance(getApplicationContext());
                databaseAccessLogin.open();

                String respuesta = databaseAccessLogin.getUserByEmail(correo,pass);
                switch (respuesta) {
                    //Case statements
                    case "0":
                        // email incorrecto
                        popUpDialogEmail();
                        break;
                    case "1":
                        // login exitoso //
                        Toast.makeText(LoginActivity.this, "Sesion iniciada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case "2":
                        // password incorrecto //
                        popUpDialogPass();
                        break;

                }
                databaseAccessLogin.close();
            }
        });

        ///ESCUCHADOR DEL EDIT TEXTS COORREO//
        edtxt_correo.addTextChangedListener(loginTextWatcher);
        edtxt_pass.addTextChangedListener(loginTextWatcher);


    }

    private  TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String emailImput = edtxt_correo.getText().toString().trim();
            String passwordImput = edtxt_pass.getText().toString().trim();

            btn_login.setEnabled(!emailImput.isEmpty() && !passwordImput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    // crear pop up email incorrecto //
    public void popUpDialogEmail(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.popup_incorrect_email,null);

        Button btn_reintentar = (Button) mView.findViewById(R.id.btnCancelarEmail);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btn_reintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }

    // crear pop up password incorrecto //
    public void popUpDialogPass(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.popup_incorrect_pass,null);

        Button btn_reintentar = (Button) mView.findViewById(R.id.btnCancelarPass);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btn_reintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }

    private String encriptarPass(String pass) throws Exception {
        SecretKeySpec secretKey = generateKey(pass);
        Cipher cipher = Cipher.getInstance("BCRYPT");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte [] datosEncriptadosByte = cipher.doFinal(pass.getBytes());
        String datosEncriptadosString = Base64.encodeToString(datosEncriptadosByte, Base64.DEFAULT);

        return datosEncriptadosString;

    }

    private SecretKeySpec generateKey(String pass) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte [] key = pass.getBytes("UTF-8");
        key= sha.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key,"AES");

        return secretKey;

    }
}
