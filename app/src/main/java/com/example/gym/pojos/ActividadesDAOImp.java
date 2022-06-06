package com.example.gym.pojos;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.gym.Clases.Actividad;
import com.example.gym.Clases.Usuario;
import com.example.gym.data.ComFunctions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseCommonRegistrar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActividadesDAOImp implements ActividadesDAO{
    public static final String ACTIVIDADES = "Actividades";
    public static final String GYM_ID = "gymID";
    public static final String DIA = "dia";
    public static final String ID_ACTIVIDAD = "idActividad";

    @Override
    public void setActiviad(Actividad a1) {
        FirebaseFirestore db = FireConnection.getDb();
        db.collection(ACTIVIDADES).document(a1.getIdActividad()+"").set(a1);
    }

    @Override
    public Actividad getActividad(int idActividad, OnSuccessListener<Actividad> listener,  OnFailureListener failure) {
        final Actividad[] act = new Actividad[1];
        FirebaseFirestore db = FireConnection.getDb();
        DocumentReference documentReference = db.collection(ACTIVIDADES).document(idActividad+"");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                act[0] = documentSnapshot.toObject(Actividad.class);
                listener.onSuccess(act[0]);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                failure.onFailure(new Exception("Error al encontrar la actividad"));
            }
        });
        if (act[0] != null) return act[0];
        return null;
    }

    @Override
    public List<Actividad> getGymActivity(int gymID, String actDate, OnSuccessListener<List<Actividad>> listener, OnFailureListener failureListener) throws Exception {
        Date actualDate = ComFunctions.strToDateDay(actDate);
        final List<Actividad>[] gymArray = new List[]{new ArrayList<>()};
        FirebaseFirestore db = FireConnection.getDb();
        //        db.collection(ACTIVIDADES).whereEqualTo(GYM_ID, gymID).whereEqualTo(DIA, actualDate).get()
        db.collection(ACTIVIDADES).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshot) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : documentSnapshot) {
                            gymArray[0].add(queryDocumentSnapshot.toObject(Actividad.class));
                        }
                        //gymArray[0] = (documentSnapshot.toObjects(Actividad.class));
                        listener.onSuccess(gymArray[0]);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                failureListener.onFailure(new Exception("No se ha encontrado ninguna actividad"));
            }
        });

        if (!gymArray[0].isEmpty()) return gymArray[0];
        return null;
    }

    @Override
    public Actividad[] getUserActivitys(int idUser) {
        return new Actividad[0];
    }

    /**
     *
     * Busca una actividad por la id, y comprueba que sea superior a la fecha actual
     *
     * @param idActividad id de la actividad que queremos consultar
     * @param listener Si la consulta es exitosa
     * @return la actividad con las caracteristicas en caso de que exista
     */

    @Override
    public Actividad getActividadById(int idActividad, OnCompleteListener<QuerySnapshot> listener) {
        Actividad[] act = new Actividad[1];
        FirebaseFirestore db = FireConnection.getDb();

        db.collection(ACTIVIDADES).whereEqualTo(ID_ACTIVIDAD, idActividad).whereGreaterThan(DIA, ComFunctions.getActualDate()) // TODO comprobar si esto funciona bien
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                act[0] = document.toObject(Actividad.class);
                            }
                        }
                    }
                });
        if (act[0] == null) {
            return null;
        }
        return act[0];
    }
}
