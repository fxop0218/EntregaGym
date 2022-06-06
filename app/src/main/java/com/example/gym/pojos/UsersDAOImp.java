package com.example.gym.pojos;

import android.content.Context;
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

public class UsersDAOImp implements UsersDAO{
  public static final String USERS = "users";
  public static final String NOMBRE = "nombre";
  public static final String APELLIDOS = "apellidos";
  public static final String DNI = "dni";
  public static final String USER = "user";
  public static final String PASSWORD = "password";
  public static final String ID_GIMNASIOS = "idGimnasios";
  public static final String GYM_OWNER = "gymOwner";
  Context context;
  @Override
  public Usuario getUsuario(String userName, OnSuccessListener<Usuario> listener, OnFailureListener failure) {

    final Usuario[] usr = new Usuario[1];
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection(USERS).document(userName);
    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
      @Override //String nombre, String apellidos, String dni, int dataNacimiento, String user, String password
      public void onSuccess(DocumentSnapshot documentSnapshot) {
        usr[0] = documentSnapshot.toObject(Usuario.class);
        //usr[0] = new Usuario(nombre, apellidos, dni, 2002, user, pwd,gymID ,owner);

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

  @Override
  public void setUsuario(Usuario u) {

  }

  /**
   * Actualiza la id de gymnasio de un usuario
   * @param gymID
   */
  @Override
  public void addGym(int gymID) throws Exception {
    FirebaseFirestore db = FireConnection.getDb();
    Usuario uChanged = UserSession.getUsuario();
    if (!uChanged.isGymOwner()) {
      uChanged.setIdGimnasios(gymID);
      db.collection(USERS).document(UserSession.getUsuario().getUser()).set(uChanged);
    } else {
      throw new Exception("No se puede mofificar el id de gimnasio si eres el dueño de uno");
    }
  }

  @Override
  public void deleteUser(String username) throws Exception {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    db.collection("users").document(username).delete();
  }
}
