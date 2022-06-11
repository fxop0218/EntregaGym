package com.example.gym.pojos;

import androidx.annotation.NonNull;

import com.example.gym.Clases.Actividad;
import com.example.gym.Clases.Reserva;
import com.example.gym.data.ComFunctions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReservaDAOImp implements reservaDAO{

    @Override
    public void addReserva(Reserva r1) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reservas").document(r1.getIdReserva()).set(r1);
    }

    @Override
    public Reserva getReserva(String idReserva, OnSuccessListener<Reserva> listener, OnFailureListener failureListener) throws Exception {
        Reserva[] res = new Reserva[1];
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reservas").document(idReserva).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                res[0] = documentSnapshot.toObject(Reserva.class);
                listener.onSuccess(res[0]);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                failureListener.onFailure(new Exception("Error"));
            }
        });;
        return res[0];
    }

    @Override
    public ArrayList<Reserva> getReservaByActivityID(int activityID) {
        return null;
    }

    @Override
    public ArrayList<Reserva> getReservaByUserName(String userName) {
        return null;
    }

    @Override
    public void deleteReserva(String userName, int acivityID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reservas").document(userName + acivityID).delete();
    }
    @Override
    public void deleteReserva(String idReserva) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(idReserva).delete();
    }
}