package com.example.gym.pojos;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.gym.Clases.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class UsersDAOImp implements UsersDAO{
    Context context;
    @Override
    public Usuario getUsuario(String userName, OnSuccessListener<Usuario> listener, OnFailureListener failure) {
        final Usuario[] usr = new Usuario[1];
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userName);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override //String nombre, String apellidos, String dni, int dataNacimiento, String user, String password
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombre = documentSnapshot.getString("nombre");
                String apellidos = documentSnapshot.getString("apellidos");
                String dni = documentSnapshot.getString("dni");
                String user = documentSnapshot.getString("user");
                String pwd = documentSnapshot.getString("password");

                usr[0] = new Usuario(nombre, apellidos, dni, 2002, user, pwd);

                listener.onSuccess(usr[0]);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                failure.onFailure(new Exception("usuario o contraseña incorrecto"));
            }
        });

        if (usr[0] != null) return usr[0];
        return null;
    }

    @Override
    public String getUserPwd(String userName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final String[] pwd = {"1"};

        DocumentReference docRef = db.collection("users").document(userName);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                pwd[0] = documentSnapshot.getString("password");
            }
        });
        return pwd[0];
    }
}
