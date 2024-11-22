package com.softcastapp.models;

public class UsuarioLogin {
    private String email;
    private String senha;

    public UsuarioLogin() {
        // Construtor padrão
    }

    public UsuarioLogin(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    // Getters e setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
