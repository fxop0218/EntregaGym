package com.example.gym.pojos;

import com.example.gym.Clases.Reserva;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public interface reservaDAO {
    public void addReserva(Reserva r1);
    public Reserva getReserva (String idReserva, OnSuccessListener<Reserva> listener, OnFailureListener failureListener) throws Exception;
    public ArrayList<Reserva> getReservaByActivityID(int activityID);
    public ArrayList<Reserva> getReservaByUserName (String userName);
    public void deleteReserva (String userName, int acivityID);
    public void deleteReserva(String idReserva);
}
