package com.softcastapp.models;

public class UsuarioRegister {
    private String nome;
    private String email;
    private String senha;
    private String dtNascimento;

    public UsuarioRegister(String nome, String email, String senha, String dtNascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dtNascimento = dtNascimento;
    }

    // Getters e Setters
}
