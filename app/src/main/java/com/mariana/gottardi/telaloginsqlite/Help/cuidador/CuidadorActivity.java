package com.mariana.gottardi.telaloginsqlite.Help.cuidador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mariana.gottardi.telaloginsqlite.Help.mensagem.SmsActivity;
import com.mariana.gottardi.telaloginsqlite.R;

import java.util.ArrayList;

public class CuidadorActivity extends AppCompatActivity {
    //WIDGETS
    private EditText ma_et_nome;
    private EditText ma_et_telefone;
    //Cuidar para não confundir RadioButton com RadioGroup
    private RadioGroup rgtipo;
    //RadioButton do grupo Tipo
    private RadioButton rbCuidador;
    private RadioButton rbFamiliar;
    private RadioButton rbEnfermeiro;
    //RadioGroup do Ativar/Desativar Mensagem
    private RadioGroup rgAtivarDesativar;
    private RadioButton rbAtivar;
    private RadioButton rbDesativar;
    //Buttons
    private Button btCadastrarCuidador;
    private Button btLimparDadosCuidador;
    private  int ativarMensagem;


    private String nomeEdicao;
    private int tipocuidador;
    private boolean mostramensagem;
    BaseDAO baseDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador);
        setTitle("Cadastro do Cuidador");

        baseDAO = new BaseDAO(this);

        //Refs
        ma_et_nome = findViewById(R.id.ma_et_nome);
        ma_et_telefone = findViewById(R.id.ma_et_fone);
        rgtipo = findViewById(R.id.ma_rg_tipo);
        rbCuidador = findViewById(R.id.ma_rb_outros);
        rbFamiliar = findViewById(R.id.ma_rb_familiar);
        rbEnfermeiro = findViewById(R.id.ma_rb_enfermeiro);
        rgAtivarDesativar = findViewById(R.id.ma_rg_ativar_desativar);
        rbAtivar = findViewById(R.id.ma_rb_ativar);
        rbDesativar = findViewById(R.id.ma_rb_desativar);
        btCadastrarCuidador = findViewById(R.id.ma_bt_salvar_cuidador);
        btLimparDadosCuidador = findViewById(R.id.ma_bt_limparcuidador);


        //CheckBox Tipos
        rgtipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mostramensagem) {
                    if (checkedId == rbEnfermeiro.getId()) {
                        Toast.makeText(getBaseContext(), "Enfermeiro", Toast.LENGTH_SHORT).show();
                    }
                    if (checkedId == rbFamiliar.getId()) {
                        Toast.makeText(getBaseContext(), "Familiar", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Cuidador", Toast.LENGTH_SHORT).show();
                    }//Fim do else
                }

            }//fecha if
        });//Fim do metodo setOnCheckedChangeListener

        //CheckBox Ativar/Desativar
        rgAtivarDesativar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbAtivar.getId()) {
                    ativarMensagem = 1;
                    if (mostramensagem) {
                        Toast.makeText(getBaseContext(), "Mensagem Ativada", Toast.LENGTH_SHORT).show();
                    }
                } else if (checkedId == rbDesativar.getId()) {
                    ativarMensagem = 0;
                    if (mostramensagem) {
                        Toast.makeText(getBaseContext(), "Mensagem Desativada", Toast.LENGTH_SHORT).show();
                    }
                }//Fim do else
            }//fecha if
        });//Fim do metodo setOnCheckedChangeListener do Ativar/Desativar

        btCadastrarCuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeCuidador = CuidadorActivity.this.ma_et_nome.getText().toString();
                String telefoneCuidador = CuidadorActivity.this.ma_et_telefone.getText().toString();
                int tp = rgtipo.getCheckedRadioButtonId();
                if(tp != -1){
                    if(tp == rbFamiliar.getId()){
                        tipocuidador = 1;
                    }else if(tp == rbEnfermeiro.getId()){
                        tipocuidador = 2;
                    }else{
                        tipocuidador = 3;
                    }
                }else{
                    tipocuidador = -1;
                }
                if (!nomeCuidador.equals("")) {
                    if (!telefoneCuidador.equals("")) {
                        if (CuidadorActivity.this.nomeEdicao != null) {
                            if (CuidadorActivity.this.baseDAO.editaCuidador(nomeCuidador, telefoneCuidador, Integer.valueOf(CuidadorActivity.this.ativarMensagem), Integer.valueOf(tipocuidador), CuidadorActivity.this.nomeEdicao).longValue() > 0) {
                                Toast.makeText(CuidadorActivity.this, "Cuidador alterado com sucesso!", Toast.LENGTH_SHORT).show();
                                CuidadorActivity.this.startActivity(new Intent(CuidadorActivity.this, SmsActivity.class));
                            } else {
                                Toast.makeText(CuidadorActivity.this, "Falha na alteração do registro!", Toast.LENGTH_SHORT).show();
                            }
                            return;
                        } else if (CuidadorActivity.this.baseDAO.CriarCuidador(nomeCuidador, telefoneCuidador, Integer.valueOf(CuidadorActivity.this.ativarMensagem), Integer.valueOf(tipocuidador)) > 0) {
                            Toast.makeText(CuidadorActivity.this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show();
                            CuidadorActivity.this.startActivity(new Intent(CuidadorActivity.this, SmsActivity.class));
                            return;
                        } else {
                            Toast.makeText(CuidadorActivity.this, "Falha no registro!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                Toast.makeText(CuidadorActivity.this, "Cuidador não inserido,tentar novamente", Toast.LENGTH_SHORT).show();
            }
        });

        btLimparDadosCuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ma_et_nome.setText(null);
                ma_et_telefone.setText(null);
                rgtipo.clearCheck();
                rgAtivarDesativar.clearCheck();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.nomeEdicao = bundle.getString("nomeCuidador");
        }
        if (this.nomeEdicao != null) {
            mostramensagem = false;
            Cuidador cuidador = this.baseDAO.getCuidador(this.nomeEdicao);
            this.ma_et_nome.setText(cuidador.getNomeCuidador());
            this.ma_et_telefone.setText(cuidador.getTelefoneCuidador());
            if(cuidador.getAtivarMensagem().booleanValue()){
                this.rbAtivar.setChecked(true);
            }else{
                this.rbDesativar.setChecked(true);
            }
            if(!cuidador.getTipoCuidador().equals("-1")){
                if(cuidador.getTipoCuidador().equals("1")){
                    this.rbFamiliar.setChecked(true);
                }else if(cuidador.getTipoCuidador().equals("2")){
                    this.rbEnfermeiro.setChecked(true);
                }else{
                    this.rbCuidador.setChecked(true);
                }
            }
            mostramensagem = true;

        }
    }

}//Fim da Classe Cuidador
