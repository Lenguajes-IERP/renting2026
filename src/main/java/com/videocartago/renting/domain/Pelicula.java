package com.videocartago.renting.domain;

import java.util.ArrayList;
import java.util.List;

public class Pelicula {
    private int peliculaId;
    private String titulo;
    private boolean subtitulada;
    private boolean estreno;
    private Genero genero;
    private List<Actor> actores;
   
    public Pelicula() {
        this.genero = new Genero();
        this.actores = new ArrayList<Actor>(); // Inicializar con una lista vacía
    }
 
    public int getPeliculaId() {
        return peliculaId;
    }
 
    public void setPeliculaId(int peliculaId) {
        this.peliculaId = peliculaId;
    }
 
    public String getTitulo() {
        return titulo;
    }
 
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
 
    public boolean isSubtitulada() {
        return subtitulada;
    }
 
    public void setSubtitulada(boolean subtitulada) {
        this.subtitulada = subtitulada;
    }
 
    public boolean isEstreno() {
        return estreno;
    }
 
    public void setEstreno(boolean estreno) {
        this.estreno = estreno;
    }
 
    public Genero getGenero() {
        return genero;
    }
 
    public void setGenero(Genero genero) {
        this.genero = genero;
    }
 
    public List<Actor> getActores() {
        return actores;
    }
 
    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }
 
   
   
   
}
