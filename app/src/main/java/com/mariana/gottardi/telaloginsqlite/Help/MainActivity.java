package com.mariana.gottardi.telaloginsqlite.Help;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mariana.gottardi.telaloginsqlite.Help.mensagem.SmsActivity;
import com.mariana.gottardi.telaloginsqlite.Help.paciente.BancoDadosHelper;
import com.mariana.gottardi.telaloginsqlite.R;

import com.mariana.gottardi.telaloginsqlite.Help.paciente.LoginActivity;
import com.mariana.gottardi.telaloginsqlite.Help.paciente.RegistrarActivity;

public class MainActivity extends AppCompatActivity {

    Button bt_entrar, bt_registrar;


    BancoDadosHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Help-Me!");

        db= new BancoDadosHelper(this);

        if(db.isLogado()){
            Intent it = new Intent(MainActivity.this, SmsActivity.class);
            startActivity(it);
        }

        bt_entrar = (Button) findViewById(R.id.bt_entrar);
        bt_registrar = (Button) findViewById(R.id.bt_registrar);

        bt_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        bt_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrarActivity.class);
                startActivity(intent);

            }
        });
    }
}
