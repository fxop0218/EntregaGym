package com.example.gym.pojos;

import androidx.annotation.NonNull;

import com.example.gym.Clases.Actividad;
import com.example.gym.Clases.Reserva;
import com.example.gym.data.ComFunctions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReservaDAOImp implements reservaDAO{

    @Override
    public void addReserva(Reserva r1) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reservas").document(r1.getIdReserva()).set(r1);
    }

    @Override
    public Reserva getReserva(int idReserva) {
        return null;
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
        String[] reserveId = new String[1];
        FirebaseFirestore db = FireConnection.getDb();
        db.collection("Reserva")
                .whereEqualTo(ComFunctions.ID_ACTIVIDAD, acivityID)
                .whereEqualTo(ComFunctions.USER_NAME, userName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                reserveId[0] = document.getString(ComFunctions.ID_RESERVA);
                            }
                        }
                        // En caso de que no encuentre, continua, en caso contrario lo elimina
                        if (!reserveId[0].isEmpty()) {
                            db.collection(ComFunctions.RESERVA).document(reserveId[0]).delete();
                        }
                    }
                });
    }
}