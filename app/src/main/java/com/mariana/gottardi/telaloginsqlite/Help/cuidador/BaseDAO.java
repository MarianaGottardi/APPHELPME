package com.mariana.gottardi.telaloginsqlite.Help.cuidador;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BaseDAO extends SQLiteOpenHelper {

    private static int versao = 2;
    private static String nome = "cuidadorSqlite";

    public BaseDAO(Context context) {

        super(context, nome, null, versao);
    }

    @Override
    // Cria o banco pela 1° vez
    public void onCreate(SQLiteDatabase db) {
        String str = "CREATE TABLE Cuidador(nomeCuidador TEXT PRIMARY KEY,telefoneCuidador TEXT,ativarMensagem INTEGER,tipoCuidador INTEGER);";
        db.execSQL(str);
    }//Fim do método oncreate

    @Override
    //Atualiza o banco
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Cuidador;");
        onCreate(db);
    }//Fim do método OnUpgrade

    public long CriarCuidador(String nomeCuidador, String telefoneCuidador, Integer ativarMensagem, Integer tipoCuidador) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nomeCuidador", nomeCuidador);
        cv.put("telefoneCuidador", telefoneCuidador);
        cv.put("ativarMensagem", ativarMensagem);
        cv.put("tipoCuidador", tipoCuidador);

        if(ativarMensagem == 1){
            ContentValues c = new ContentValues();
            c.put("ativarMensagem", 0);
            db.update("Cuidador", c, null, null);
        }
        long result = db.insert("Cuidador", null, cv);
        return result;
    }//Fim do Método CriarCuidador

    public void alteraCuidadorAtivo(String nomeCuidador){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues ativo = new ContentValues();
        ativo.put("ativarMensagem", 1);

        ContentValues outros = new ContentValues();
        outros.put("ativarMensagem", 0);

        db.update("Cuidador", ativo, "nomeCuidador = ?", new String[]{nomeCuidador});
        db.update("Cuidador", outros, "nomeCuidador <> ?", new String[]{nomeCuidador});
    }


    public String getNumeroCuidador() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Cuidador WHERE ativarMensagem=? ", new String[]{"1"});
        if (c.getCount() > 0) {
            if (c != null && c.moveToFirst()) {
                String telefone = c.getString(c.getColumnIndex("telefoneCuidador"));
                c.close();
                return telefone;
            }
        }
        return null;
    }

    public String getNomeCuidador() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Cuidador WHERE ativarMensagem=? ", new String[]{"1"});
        if (c.getCount() > 0) {
            if (c != null && c.moveToFirst()) {
                String nome = c.getString(c.getColumnIndex("nomeCuidador"));
                c.close();
                return nome;
            }
        }
        return null;
    }

    public List<String> listaCuidador() {
        SQLiteDatabase db = getReadableDatabase();
        List<String> cuidadores = new ArrayList<>();
        String[] columns={"nomeCuidador"};
        Cursor c=db.query("Cuidador",columns,null,null,null,null,null);
        if (c.getCount() > 0) {
            if (c != null && c.moveToFirst()) {
                while (!c.isAfterLast()) {
                    String nome = c.getString(c.getColumnIndex("nomeCuidador"));
                    cuidadores.add(nome);
                    c.moveToNext();
                }
                c.close();
            }
        }
        return cuidadores;
    }

    public void excluiCuidador(String cuidador) {
        getWritableDatabase().delete("Cuidador", "nomeCuidador=?", new String[]{cuidador});
    }

    public Cuidador getCuidador(String nome) {
        boolean ativarMensagem = true;
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM Cuidador WHERE nomeCuidador=? ", new String[]{nome});
        if (c.getCount() <= 0 || c == null || !c.moveToFirst()) {
            return null;
        }
        String nomeCuidador = c.getString(c.getColumnIndex("nomeCuidador"));
        String telefone = c.getString(c.getColumnIndex("telefoneCuidador"));
        if (c.getInt(c.getColumnIndex("ativarMensagem")) != 1) {
            ativarMensagem = false;
        }
        String tipo = c.getString(c.getColumnIndex("tipoCuidador"));
        c.close();
        return new Cuidador(nomeCuidador, telefone, Boolean.valueOf(ativarMensagem), tipo);
    }

    public Long editaCuidador(String nomeCuidador, String telefoneCuidador, Integer ativarMensagem, Integer tipoCuidador, String nomeEdicao) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nomeCuidador", nomeCuidador);
        cv.put("telefoneCuidador", telefoneCuidador);
        cv.put("ativarMensagem", ativarMensagem);
        cv.put("tipoCuidador", tipoCuidador);

        if(ativarMensagem == 1){
            alteraCuidadorAtivo(nomeCuidador);
        }

        return Long.valueOf((long) db.update("Cuidador", cv, "nomeCuidador=?", new String[]{nomeEdicao}));
    }
}//Fim da Classe BaseDao
