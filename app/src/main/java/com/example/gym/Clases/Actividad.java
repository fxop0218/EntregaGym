package com.example.gym.Clases;

import java.time.LocalDate;
import java.util.Date;

public class Actividad {

    private int idActividad;
    private String nombre;
    private String descripcion;
    private int aforo;
    private Date hora_inicio;
    private Date hora_fin;

    public Actividad(int idActividad, String nombre, String descripcion, int aforo, Date hora_inicio, Date hora_fin) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.aforo = aforo;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
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
}
