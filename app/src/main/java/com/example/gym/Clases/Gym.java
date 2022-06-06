package com.example.gym.Clases;

import java.util.Date;

public class Gym {

    private int idGym;
    private String ciudad;
    private int CP;
    private Date hora_apertura;
    private Date hora_cerrada;

    public Gym(int idGym, String ciudad, int CP, Date hora_apertura, Date hora_cerrada) {
        this.idGym = idGym;
        this.ciudad = ciudad;
        this.CP = CP;
        this.hora_apertura = hora_apertura;
        this.hora_cerrada = hora_cerrada;
    }

    public Gym() { }

    public int getIdGym() {
        return idGym;
    }

    public void setIdGym(int idGym) {
        this.idGym = idGym;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCP() {
        return CP;
    }

    public void setCP(int CP) {
        this.CP = CP;
    }

    public Date getHora_apertura() {
        return hora_apertura;
    }

    public void setHora_apertura(Date hora_apertura) {
        this.hora_apertura = hora_apertura;
    }

    public Date getHora_cerrada() {
        return hora_cerrada;
    }

    public void setHora_cerrada(Date hora_cerrada) {
        this.hora_cerrada = hora_cerrada;
    }
}
