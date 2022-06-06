package com.example.gym.reciclerLayout;

public class Model {
    String name;
    String hora_inicio;
    String hora_fin;

    public Model(String name, String hora_inicio, String hora_fin) {
        this.name = name;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }
}
