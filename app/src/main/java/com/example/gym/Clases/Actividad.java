package com.example.gym.Clases;

import java.time.LocalDate;
import java.util.Date;

public class Actividad {

    private int idActividad;
    private int gymID;
    private String nombre;
    private int aforo;
    private int aforo_actual = 0;
    private Date hora_inicio;
    private Date hora_fin;
    private Date dia;

    public Actividad() {}

    public Actividad(int idActividad, String nombre, int gymID, int aforo, Date hora_inicio, Date hora_fin, Date dia) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.gymID = gymID;
        this.aforo = aforo;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.dia = dia;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getGymID() {
        return gymID ;
    }

    public void setGymID(int gymID) {
        this.gymID = gymID;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public int getAforo_actual() {
        return aforo;
    }

    public void setAforo_actual(int aforo) {
        this.aforo = aforo;
    }

    public void sumAforo_actual() {
        if (aforo_actual < aforo) aforo_actual++;
    }

    public void resAforo_actual() {
        if (aforo_actual > 0) aforo_actual--;
    }

    public Date getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(Date hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public Date getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(Date hora_fin) {
        this.hora_fin = hora_fin;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }
}
