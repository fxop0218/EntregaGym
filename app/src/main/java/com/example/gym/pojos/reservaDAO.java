package com.example.gym.pojos;

import com.example.gym.Clases.Reserva;

import java.util.ArrayList;

public interface reservaDAO {
    public void addReserva(Reserva r1);
    public Reserva getReserva (int idReserva);
    public ArrayList<Reserva> getReservaByActivityID(int activityID);
    public ArrayList<Reserva> getReservaByUserName (String userName);
    public void deleteReserva (String userName, int acivityID);
}
