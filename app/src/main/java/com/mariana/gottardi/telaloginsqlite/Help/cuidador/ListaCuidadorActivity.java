package com.mariana.gottardi.telaloginsqlite.Help.cuidador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.mariana.gottardi.telaloginsqlite.Help.mensagem.SmsActivity;
import com.mariana.gottardi.telaloginsqlite.R;

public class ListaCuidadorActivity extends AppCompatActivity {
    private BaseDAO baseDAO;
    private Button botaoNovo;
    private ListView lista;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_lista_cuidador);
        setTitle("Cuidadores");
        this.lista = (ListView) findViewById(R.id.lista);
        this.baseDAO = new BaseDAO(this);
        this.lista.setAdapter(new CuidadorAdapter(this.baseDAO.listaCuidador(), this, this.lista));
        this.botaoNovo = (Button) findViewById(R.id.Novo_Cuidador);

        botaoNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Instanciando uma Intent. Ela será responsável por abrir a segunda Tela
                Intent it = new Intent(ListaCuidadorActivity.this, CuidadorActivity.class);
                startActivity(it);
            }
        });

    }

    public void onBackPressed() {
        startActivity(new Intent(this, SmsActivity.class));
    }
}
