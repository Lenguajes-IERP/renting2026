package com.videocartago.renting.domain;

public class Actor {
    private int actorId;
    private String nombreActor;
    private String apellidosActor;
   
    public Actor() {
    }
    public Actor(int actorId, String nombreActor, String apellidosActor) {
        this.actorId = actorId;
        this.nombreActor = nombreActor;
        this.apellidosActor = apellidosActor;
    }
 
    public int getActorId() {
        return actorId;
    }
 
    public void setActorId(int actorId) {
        this.actorId = actorId;
    }
 
    public String getNombreActor() {
        return nombreActor;
    }
 
    public void setNombreActor(String nombreActor) {
        this.nombreActor = nombreActor;
    }
 
    public String getApellidosActor() {
        return apellidosActor;
    }
 
    public void setApellidosActor(String apellidosActor) {
        this.apellidosActor = apellidosActor;
    }
 
   
   
}
