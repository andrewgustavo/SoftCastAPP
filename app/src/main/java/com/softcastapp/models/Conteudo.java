package com.softcastapp.models;

import java.io.Serializable;

public class Conteudo implements Serializable {
    private int ID;
    private String titulo;
    private String tipo;
    private String descricao;
    private String classificacaoIndicativa;
    private String videoPath;

    // Construtor
    public Conteudo(int ID, String titulo, String tipo, String descricao, String classificacaoIndicativa, String videoPath) {
        this.ID = ID;
        this.titulo = titulo;
        this.tipo = tipo;
        this.descricao = descricao;
        this.classificacaoIndicativa = classificacaoIndicativa;
        this.videoPath = videoPath;
    }

    public int getConteudoID() {
        return ID;
    }

    public void setConteudoID(int ID) {
        this.ID = ID;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }

    public void setClassificacaoIndicativa(String classificacaoIndicativa) {
        this.classificacaoIndicativa = classificacaoIndicativa;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
