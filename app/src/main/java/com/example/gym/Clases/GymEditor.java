package com.example.gym.Clases;

import java.util.Date;

public class GymEditor extends Persona {

    private int idGym;

    public GymEditor(String nombre, String apellidos, String dni, int dataNacimiento, String user, String password, int gym) {
        super(nombre, apellidos, dni, dataNacimiento, user, password);
        this.idGym = gym;
    }

    //Getters and setters

    public int getGym() {
        return idGym;
    }

    public void setGym(int gym) {
        this.idGym = gym;
    }

    public void get(){

    }
}
