package com.example.gym.Clases;

import java.util.Date;

public class Usuario extends Persona{

    private int idGimnasios;

    public Usuario(String nombre, String apellidos, String dni, int dataNacimiento, String user, String password) {
        super(nombre, apellidos, dni, dataNacimiento, user, password);
    }


    //Getters and setters

    public int getGimnasios() {
        return idGimnasios;
    }



    /* TODO
    public void setGym (Gym gimnasio) {
        gimnasios[gimnasios.length] = gimnasio;
    }
     */

    public void setGimnasios(int gimnasios) {
        this.idGimnasios = gimnasios;
    }
}
