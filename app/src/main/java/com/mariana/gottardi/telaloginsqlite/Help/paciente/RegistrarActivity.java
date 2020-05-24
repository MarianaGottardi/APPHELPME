package com.mariana.gottardi.telaloginsqlite.Help.paciente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mariana.gottardi.telaloginsqlite.Help.endereco.Util;
import com.mariana.gottardi.telaloginsqlite.R;

import com.mariana.gottardi.telaloginsqlite.Help.cuidador.CuidadorActivity;
import com.mariana.gottardi.telaloginsqlite.Help.endereco.Address;
import com.mariana.gottardi.telaloginsqlite.Help.endereco.ZipCodeListener;

public class RegistrarActivity extends AppCompatActivity {
    EditText et_usuario, et_telefone, et_email, et_senha1, et_senha2;
    EditText et_rua, et_complemento, et_cidade, et_cep, et_numero, et_bairro;
    Spinner spEstado;
    Button bt_registrarNovo;
    Button btLimparPaciente;

    BancoDadosHelper db;

    private Util util;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        setTitle("Cadastro de Paciente");

        db = new BancoDadosHelper(this);

        et_usuario = findViewById(R.id.et_usuario);
        et_telefone = findViewById(R.id.ma_et_telefone);
        et_rua = findViewById(R.id.ma_et_rua);
        et_complemento = findViewById(R.id.ma_et_complemento);
        et_numero = findViewById(R.id.ma_et_numero);
        et_cidade = findViewById(R.id.ma_et_cidade);
        et_bairro = findViewById(R.id.ma_et_bairro);
        spEstado = findViewById(R.id.ma_sp_uf);
        et_cep = findViewById(R.id.ma_et_cep);
        et_cep.addTextChangedListener(new ZipCodeListener(this));
        et_email = findViewById(R.id.et_email);
        et_senha1 = findViewById(R.id.et_senha_um);
        et_senha2 = findViewById(R.id.et_nova_senha);

        bt_registrarNovo = findViewById(R.id.bt_nova_senha);
        btLimparPaciente = findViewById(R.id.ma_bt_limpar_paciente);

        bt_registrarNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = et_usuario.getText().toString();
                String telefone = et_telefone.getText().toString();
                String cep = et_cep.getText().toString();
                String rua = et_rua.getText().toString();
                String bairro = et_bairro.getText().toString();
                String cidade = et_cidade.getText().toString();
                String uf = spEstado.getSelectedItem().toString();
                String numero = et_numero.getText().toString();
                String complemento = et_complemento.getText().toString();
                String email = et_email.getText().toString();
                String senha1 = et_senha1.getText().toString();
                String senha2 = et_senha2.getText().toString();

                if (nome.equals("") || telefone.equals("") || cep.equals("") || rua.equals("") || bairro.equals("") || cidade.equals("") || numero.equals("") || complemento.equals("") || email.equals("")) {
                    Toast.makeText(RegistrarActivity.this, "Usuário não inserido,preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else if (senha1.equals("") || senha2.equals("")) {
                    Toast.makeText(RegistrarActivity.this, "Preencha a senha corretamente", Toast.LENGTH_SHORT).show();
                } else if (!senha1.equals(senha2)) {
                    Toast.makeText(RegistrarActivity.this, "As senhas não correspondem", Toast.LENGTH_SHORT).show();
                } else {
                    long res = db.CriarPaciente(nome, telefone, cep, rua, bairro, cidade, uf, numero, complemento, email, senha1);
                    if (res > 0) {
                        Toast.makeText(RegistrarActivity.this, "Usuário registrado com sucesso!Faça seu Login!", Toast.LENGTH_SHORT).show();
                        //Instanciando uma Intent. Ela será responsável por abrir a segunda Tela
                        Intent it = new Intent(RegistrarActivity.this, LoginActivity.class);
                        startActivity(it);
                    } else {
                        Toast.makeText(RegistrarActivity.this, "Falha no registro!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        //BOTÃO LIMPAR DADOS PACIENTES
        btLimparPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_usuario.setText(null);
                et_telefone.setText(null);
                et_rua.setText(null);
                et_bairro.setText(null);
                et_complemento.setText(null);
                et_cidade.setText(null);
                et_numero.setText(null);
                et_cep.setText(null);
                et_email.setText(null);
                et_senha1.setText(null);
                et_senha2.setText(null);
            }
        });

        //Endereço com ViaCep
        Spinner spEstados = (Spinner) findViewById(R.id.ma_sp_uf);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.estado, android.R.layout.simple_spinner_item);
        spEstados.setAdapter(adapter);

        util = new Util(this, R.id.ma_et_cep, R.id.ma_et_rua,
                R.id.ma_et_complemento, R.id.ma_et_bairro, R.id.ma_et_cidade,
                R.id.ma_sp_uf, R.id.ma_et_numero);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Address.REQUEST_ZIP_CODE_CODE
                && resultCode == RESULT_OK) {

            et_cep.setText(data.getStringExtra(Address.ZIP_CODE_KEY));
        }
    }

    public String getUriZipCode() {
        return "https://viacep.com.br/ws/" + et_cep.getText() + "/json/";
    }


    public void lockFields(boolean isToLock) {
        util.lockFields(isToLock);
    }

    public void setDataViews(Address address) {
        setField(R.id.ma_et_rua, address.getLogradouro());
        setField(R.id.ma_et_complemento, address.getComplemento());
        setField(R.id.ma_et_bairro, address.getBairro());
        setField(R.id.ma_et_cidade, address.getLocalidade());
        setSpinner(R.id.ma_sp_uf, R.array.estado, address.getUf());
    }

    private void setField(int id, String data) {
        ((EditText) findViewById(id)).setText(data);
    }

    private void setSpinner(int id, int arrayId, String data) {
        String[] itens = getResources().getStringArray(arrayId);

        for (int i = 0; i < itens.length; i++) {

            if (itens[i].endsWith("(" + data + ")")) {
                ((Spinner) findViewById(id)).setSelection(i);
                return;
            }
        }
        ((Spinner) findViewById(id)).setSelection(0);
    }
}

