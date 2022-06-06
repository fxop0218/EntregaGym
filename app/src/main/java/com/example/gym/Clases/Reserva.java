package com.example.gym.Clases;

public class Reserva {

    private int idReserva;
    private Usuario usuario;
    private Actividad actividad;

    public Reserva(Usuario usuario, Actividad actividad, int idReserva) {
        this.usuario = usuario;
        this.actividad = actividad;
        this.idReserva = idReserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }
}
