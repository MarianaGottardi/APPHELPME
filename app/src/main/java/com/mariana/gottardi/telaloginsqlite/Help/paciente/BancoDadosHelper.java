package com.mariana.gottardi.telaloginsqlite.Help.paciente;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDadosHelper extends SQLiteOpenHelper {
    private static int versao = 2;
    private static String nome = "telaloginsqlite";

    public BancoDadosHelper(Context context) {

        super(context, nome, null, versao);
    }

    @Override
    // Cria o banco pela 1° vez
    public void onCreate(SQLiteDatabase db) {
        String str = "CREATE TABLE Paciente(nome TEXT PRIMARY KEY,telefone TEXT,cep TEXT,rua TEXT,bairro TEXT,cidade TEXT,uf TEXT,numero TEXT,complemento TEXT,email TEXT,senha TEXT, logado INTEGER);";
        db.execSQL(str);
    }//Fim do método oncreate

    @Override
    //Atualiza o banco
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Paciente;");
        onCreate(db);
    }//Fim do método OnUpgrade

    public long CriarPaciente(String nome, String telefone, String cep, String rua, String bairro, String cidade, String uf, String numero, String complemento, String email, String senha) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", nome);
        cv.put("telefone", telefone);
        cv.put("cep", cep);
        cv.put("rua", rua);
        cv.put("bairro", bairro);
        cv.put("cidade", cidade);
        cv.put("uf", uf);
        cv.put("numero",numero);
        cv.put("complemento", complemento);
        cv.put("email", email);
        cv.put("senha", senha);
        cv.put("logado", 0);
        long result = db.insert("Paciente", null, cv);
        return result;
    }//Fim do Método CriarPaciente

    public String ValidarLogin(String email, String senha) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Paciente WHERE email=? AND senha=?", new String[]{email, senha});
        if (c.getCount() > 0) {
            if( c != null && c.moveToFirst() ) {
                String nome = c.getString(c.getColumnIndex("nome"));
                c.close();
                ContentValues cv = new ContentValues();
                cv.put("logado", 1);
            db.update("Paciente", cv, "nome=  ?", new String[]{nome});
                return "OK";
            }
        }
        return "ERRO";
    }//Fim do método Validar Login

    public boolean isLogado(){
        SQLiteDatabase db = getReadableDatabase();
        String[] columns={"logado"};

        Cursor c=db.query("Paciente",columns,null,null,null,null,null);
        if (c.getCount() > 0) {
            if( c != null && c.moveToFirst() ){
                String logado = c.getString(c.getColumnIndex("logado"));
                c.close();

                if(logado.equals("1")){
                    return true;
                }
            }

        }
        return false;
    }
}//Fim da classe Principal
