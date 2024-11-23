package com.softcastapp.models;

import java.io.Serializable;
import java.util.List;

public class UsuarioLogin implements Serializable {

    public int ID;
    private String email;
    private String senha;
    private List<Playlist> playlists; // Lista de playlists do usuário

    public UsuarioLogin() {
        // Construtor padrão
    }

    public UsuarioLogin(int ID, String email, String senha, List<Playlist> playlists) {
        this.ID = ID;
        this.email = email;
        this.senha = senha;
        this.playlists = playlists;
    }

    // Getters e setters

    public int getId() {
        return ID;
    }

    public void setId(int ID) {
        this.ID = ID;
    }
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

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }
}
