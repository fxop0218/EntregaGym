package com.example.gym.pojos;

import com.google.firebase.firestore.FirebaseFirestore;

public class FireConnection {
    private static FirebaseFirestore db;


    public static FirebaseFirestore getDb () {
     return FirebaseFirestore.getInstance();
    }
}
