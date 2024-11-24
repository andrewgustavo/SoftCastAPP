package com.softcastapp.models;

import java.io.Serializable;

public class Playlist implements Serializable {
    private int ID;
    private String Nome;
    private int UsuarioID;


    // Construtor padrão
    public Playlist() {
    }

    // Construtor com argumentos
    public Playlist(String nome, int usuarioId) {
                this.Nome = nome;
        this.UsuarioID = usuarioId;
    }
    public int getPlaylistID() {
        return ID;
    }
    public void setPlaylistID(int ID) {
        this.ID = ID;
    }
    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public int getId() {
        return UsuarioID;
    }

    public void setId(int usuarioId) {
        this.UsuarioID = usuarioId;
    }
}