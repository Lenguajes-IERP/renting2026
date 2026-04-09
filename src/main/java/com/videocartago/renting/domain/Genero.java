package com.videocartago.renting.domain;

public class Genero {
    private int generoId;
    private String nombreGenero;
   
    public Genero() {
    }
    public Genero(int generoId, String nombreGenero) {
        this.generoId = generoId;
        this.nombreGenero = nombreGenero;
    }
 
    public int getGeneroId() {
        return generoId;
    }
 
    public void setGeneroId(int generoId) {
        this.generoId = generoId;
    }
 
    public String getNombreGenero() {
        return nombreGenero
;
    }
 
    public void setNombreGenero(String nombreGenero

    ) {
        this.nombreGenero = nombreGenero;
    }
   
}
 
