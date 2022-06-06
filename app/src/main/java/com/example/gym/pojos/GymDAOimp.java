package com.example.gym.pojos;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.gym.Clases.Gym;
import com.example.gym.Clases.Usuario;
import com.example.gym.UserSession;
import com.example.gym.data.ComFunctions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class  GymDAOimp implements GymDAO{

    @Override
    public void setNewGym(Gym gym) {
        FirebaseFirestore db = FireConnection.getDb();
        db.collection(ComFunctions.GYM).document(gym.getIdGym()+"").set(gym);

    }

    @Override
    public Gym getGym(int idGym, OnSuccessListener<Gym> listener, OnFailureListener failureListener) {
        final Gym[] gym = new Gym[1];
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(ComFunctions.GYM).document(idGym+"");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                gym[0] = documentSnapshot.toObject(Gym.class);
                listener.onSuccess(gym[0]);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                failureListener.onFailure(new Exception("No se ha encontrado ningun gimnasio con ese id"));
            }
        });
        if (gym[0] != null) return gym[0];
        return null;
    }
}
