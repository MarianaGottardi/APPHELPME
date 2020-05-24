package com.mariana.gottardi.telaloginsqlite.Help.mensagem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mariana.gottardi.telaloginsqlite.Help.cuidador.BaseDAO;
import com.mariana.gottardi.telaloginsqlite.Help.cuidador.ListaCuidadorActivity;
import com.mariana.gottardi.telaloginsqlite.R;

import com.mariana.gottardi.telaloginsqlite.Help.cuidador.CuidadorActivity;

import java.util.List;

public class SmsActivity extends AppCompatActivity {
    //Widgets
    private String numero;
    private String nome;
    private List<String> cuidadores;
    private final static int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private Button sendMessageHelp;
    private Button sendMessageFome;
    private Button sendMessageBanheiro;
    private Button sendMessageBanho;
    private Button sendMessageSede;
    private Button sendMessageCalor;
    private Button sendMessageFrio;
    private Button btnCadastroCuidador;
    private TextView nomeCuidador;

    BaseDAO baseDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        setTitle("Help-Me!");




        baseDAO = new BaseDAO(this);
        nomeCuidador = findViewById(R.id.nomeCuidador);

        numero =  baseDAO.getNumeroCuidador();
        nome = baseDAO.getNomeCuidador();
        nomeCuidador.setText(nome);
        cuidadores = baseDAO.listaCuidador();


        sendMessageHelp = (Button) findViewById(R.id.ma_button_emergencia);
        sendMessageHelp.setEnabled(false);
        sendMessageFome = (Button) findViewById(R.id.ma_button_fome);
        sendMessageFome.setEnabled(false);
        sendMessageBanheiro = (Button) findViewById(R.id.ma_button_banheiro);
        sendMessageBanheiro.setEnabled(false);
        sendMessageBanho = (Button) findViewById(R.id.ma_button_banho);
        sendMessageBanho.setEnabled(false);
        sendMessageSede = (Button) findViewById(R.id.ma_button_sede);
        sendMessageSede.setEnabled(false);
        sendMessageCalor = (Button) findViewById(R.id.ma_button_calor);
        sendMessageCalor.setEnabled(false);
        sendMessageFrio = (Button) findViewById(R.id.ma_button_frio);
        sendMessageFrio.setEnabled(false);


        btnCadastroCuidador = findViewById(R.id.ma_button_cadastro);
        registerForContextMenu(btnCadastroCuidador);

        if (checkPermission(Manifest.permission.SEND_SMS)) {
            sendMessageHelp.setEnabled(true);
            sendMessageFome.setEnabled(true);
            sendMessageBanheiro.setEnabled(true);
            sendMessageBanho.setEnabled(true);
            sendMessageSede.setEnabled(true);
            sendMessageCalor.setEnabled(true);
            sendMessageFrio.setEnabled(true);

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        sendMessageHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Paciente pede Socorro!";
                enviaMensagem(msg);
            }
        });
        sendMessageFome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Paciente está com fome!";
                enviaMensagem(msg);
            }
        });

        sendMessageBanheiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Paciente quer ir ao Banheiro!";
                enviaMensagem(msg);
            }
        });

        sendMessageBanho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Paciente necessita de Banho!";
                enviaMensagem(msg);
            }
        });

        sendMessageSede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Paciente está com sede!";
                enviaMensagem(msg);
            }
        });

        sendMessageCalor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Paciente está com calor!";
                enviaMensagem(msg);
            }
        });
        sendMessageFrio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Paciente está com frio!";
                enviaMensagem(msg);
            }
        });

        btnCadastroCuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Instanciando uma Intent. Ela será responsável por abrir a segunda Tela
                Intent it = new Intent(SmsActivity.this, ListaCuidadorActivity.class);
                startActivity(it);
            }
        });


    }

    private void enviaMensagem(String msg) {
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            if(numero != null){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(numero, null, msg, null, null);
                Toast.makeText(SmsActivity.this, "Mensagem enviada para " + nome, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SmsActivity.this, "Cadastre um cuidador!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(SmsActivity.this, "Permissão Negada!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    sendMessageHelp.setEnabled(true);
                    sendMessageFome.setEnabled(true);
                    sendMessageBanheiro.setEnabled(true);
                    sendMessageBanho.setEnabled(true);
                    sendMessageSede.setEnabled(true);
                    sendMessageCalor.setEnabled(true);
                    sendMessageFrio.setEnabled(true);
                }
                break;
        }

    }
    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo) {
        for(String c: cuidadores){
            contextMenu.add(Menu.NONE, 1, Menu.NONE, c);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        nome = item.getTitle().toString();
        nomeCuidador.setText(nome);
        baseDAO.alteraCuidadorAtivo(nome);
        numero = baseDAO.getNumeroCuidador();
        return super.onContextItemSelected(item);
    }


}//Fim da Classe SMS
