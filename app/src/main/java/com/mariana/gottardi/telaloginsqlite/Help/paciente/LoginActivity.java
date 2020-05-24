package com.mariana.gottardi.telaloginsqlite.Help.paciente;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mariana.gottardi.telaloginsqlite.Help.cuidador.CuidadorActivity;
import com.mariana.gottardi.telaloginsqlite.Help.mensagem.SmsActivity;
import com.mariana.gottardi.telaloginsqlite.R;

public class LoginActivity extends AppCompatActivity {
    EditText et_email, et_password;
    Button bt_login;

    BancoDadosHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        db= new BancoDadosHelper(this);

        et_email = findViewById(R.id.la_et_email);
        et_password = findViewById(R.id.la_et_senha);
        bt_login =(Button) findViewById(R.id.ra_bt_login);


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = et_email.getText().toString();
                String senha = et_password.getText().toString();

                if (email.equals("")){
                    Toast.makeText(LoginActivity.this,"Email não inserido, tente novamente!",Toast.LENGTH_SHORT).show();
                }else if(senha.equals("")){
                    Toast.makeText(LoginActivity.this,"Insira a senha!",Toast.LENGTH_SHORT).show();
                }else{
                   String res  = db.ValidarLogin(email,senha);
                   if(res.equals("OK")) {
                      Toast.makeText(LoginActivity.this, "Login OK", Toast.LENGTH_SHORT).show();
                       //Instanciando uma Intent. Ela será responsável por abrir a segunda Tela
                       Intent it = new Intent(LoginActivity.this, SmsActivity.class);
                       startActivity(it);
                   }else{
                       Toast.makeText(LoginActivity.this,"Login inválido, tentar novamente!",Toast.LENGTH_SHORT).show();
                   }

                }

            }
        });

    }
}
