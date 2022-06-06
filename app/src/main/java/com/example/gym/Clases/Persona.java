package com.example.gym.Clases;

import java.util.Date;

public class Persona {

    private String nombre;
    private String apellidos;
    private String dni;
    private int dataNacimiento;
    private String user;
    private String password;

    public Persona(String nombre, String apellidos, String dni, int dataNacimiento, String user, String password) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.dataNacimiento = dataNacimiento;
        this.user = user;
        this.password = password;
    }

    //Getters and setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getDataNacimiento() {
        return dataNacimiento;
    }

    public void setDataNacimiento(int dataNacimiento) {
        this.dataNacimiento = dataNacimiento;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
