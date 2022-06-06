package com.example.gym.Clases;

public class Reserva {

    private String usuario;
    private String idReserva;
    private int actividad;

    public Reserva(String usuario, int actividad, String idReserva) {
        this.usuario = usuario;
        this.actividad = actividad;
        this.idReserva = idReserva;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getActividad() {
        return actividad;
    }

    public void setActividad(int actividad) {
        this.actividad = actividad;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }
}