package com.example.gym.pojos;

import com.example.gym.Clases.Actividad;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public interface ActividadesDAO {
    public void setActiviad(Actividad a1);
    public Actividad getActividad(int idActividad, OnSuccessListener<Actividad> listener, OnFailureListener failure);
    public List<Actividad> getGymActivity(int gymID, String actDate, OnSuccessListener<List<Actividad>> listener, OnFailureListener failureListener) throws Exception;
    public Actividad[] getUserActivitys(int idUser);
    public Actividad getActividadById (int idActividad, OnCompleteListener<QuerySnapshot> listener);
}
