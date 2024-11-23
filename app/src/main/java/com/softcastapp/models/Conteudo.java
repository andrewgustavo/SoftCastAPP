package com.softcastapp.models;

import java.io.Serializable;

public class Conteudo implements Serializable {
    private int ID;
    private String Titulo;
    private String Tipo;
    private String Descricao;
    private String ClassificacaoIndicativa;
    private String VideoPath;

    // Construtor
    public Conteudo(int ID, String titulo, String tipo, String descricao, String classificacaoIndicativa, String videoPath) {
        this.ID = ID;
        this.Titulo = titulo;
        this.Tipo = tipo;
        this.Descricao = descricao;
        this.ClassificacaoIndicativa = classificacaoIndicativa;
        this.VideoPath = videoPath;
    }

    public int getId() {
        return ID;
    }

    public void setId(int ID) {
        this.ID = ID;
    }
    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        this.Titulo = Titulo;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        this.Tipo = tipo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        this.Descricao = descricao;
    }

    public String getClassificacaoIndicativa() {
        return ClassificacaoIndicativa;
    }

    public void setClassificacaoIndicativa(String classificacaoIndicativa) {
        this.ClassificacaoIndicativa = classificacaoIndicativa;
    }

    public String getVideoPath() {
        return VideoPath;
    }

    public void setVideoPath(String videoPath) {
        this.VideoPath = VideoPath;
    }
}
