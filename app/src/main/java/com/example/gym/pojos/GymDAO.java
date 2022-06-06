package com.example.gym.pojos;

import com.example.gym.Clases.Gym;
import com.example.gym.Clases.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public interface GymDAO {
  public void setNewGym(Gym gym);
  public Gym getGym(int idGym, OnSuccessListener<Gym> listener,  OnFailureListener failureListener);

}