package com.alerta.mobile_ufrpe.alerta.Entidades;

import com.alerta.mobile_ufrpe.alerta.DAO.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Usuarios {
    private String id;
    private String email;
    private String senha;
    private String confSenha;
    private String nome;
    private String dataDeNascimento;
    private String sexo;

    public String getConfSenha() {
        return confSenha;
    }

    public void setConfSenha(String confSenha) {
        this.confSenha = confSenha;
    }

    public Usuarios() {

    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.INSTANCE.getFirebase();
        referenciaFirebase.child("usuario").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap(){

        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id", getId());
        hashMapUsuario.put("email", getEmail());
        hashMapUsuario.put("senha", getSenha());
        hashMapUsuario.put("nome", getNome());

        return hashMapUsuario;
    }



    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public String getDataDeNascimento() {
        return dataDeNascimento;
    }

    public String getSexo() {
        return sexo;
    }




    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataDeNascimento(String dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

}
