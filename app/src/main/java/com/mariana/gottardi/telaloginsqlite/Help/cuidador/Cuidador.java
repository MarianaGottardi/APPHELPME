package com.mariana.gottardi.telaloginsqlite.Help.cuidador;

public class Cuidador {

    private String key;
    private String nomeCuidador;
    private String telefoneCuidador;
    private Boolean ativarMensagem;
    private String tipoCuidador;

    public Cuidador() {
    }

    public Cuidador(String key, String nomeCuidador, String telefoneCuidador, Boolean ativarMensagem, String tipoCuidador) {
        this.key = key;
        this.nomeCuidador = nomeCuidador;
        this.telefoneCuidador = telefoneCuidador;
        this.ativarMensagem = ativarMensagem;
        this.tipoCuidador = tipoCuidador;
    }

    public Cuidador(String nomeCuidador, String telefoneCuidador, Boolean ativarMensagem, String tipoCuidador) {
        this.nomeCuidador = nomeCuidador;
        this.telefoneCuidador = telefoneCuidador;
        this.ativarMensagem = ativarMensagem;
        this.tipoCuidador = tipoCuidador;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNomeCuidador() {
        return nomeCuidador;
    }

    public void setNomeCuidador(String nomeCuidador) {
        this.nomeCuidador = nomeCuidador;
    }

    public Boolean getAtivarMensagem() {
        return ativarMensagem;
    }

    public void setAtivarMensagem(Boolean ativarMensagem) {
        this.ativarMensagem = ativarMensagem;
    }

    public String getTipoCuidador() {
        return tipoCuidador;
    }

    public void setTipoCuidador(String tipoCuidador) {
        this.tipoCuidador = tipoCuidador;
    }

    public String getTelefoneCuidador() {
        return telefoneCuidador;
    }

    public void setTelefoneCuidador(String telefoneCuidador) {
        this.telefoneCuidador = telefoneCuidador;
    }

    @Override
    public String toString() {
        return "Cuidador{" +
                "key='" + key + '\'' +
                ", nomeCuidador='" + nomeCuidador + '\'' +
                ", telefoneCuidador='" + telefoneCuidador + '\'' +
                ", ativarMensagem=" + ativarMensagem +
                ", tipoCuidador='" + tipoCuidador + '\'' +
                '}';
    }
}//Fim da Classe Cuidador
