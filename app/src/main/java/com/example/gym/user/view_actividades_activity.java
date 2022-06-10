package com.example.gym.user;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;

import com.example.gym.Clases.Actividad;
import com.example.gym.Clases.Reserva;
import com.example.gym.R;
import com.example.gym.UserSession;
import com.example.gym.data.ComFunctions;
import com.example.gym.pojos.FireConnection;
import com.example.gym.pojos.PojosClass;
import com.example.gym.reciclerLayout.AdapterActivity;
import com.example.gym.reciclerLayout.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class view_actividades_activity extends AppCompatActivity {
    String day = "01/01/2000";
    private Button bClose;
    private Button bAddByCode;

    List<Actividad> correctActivity = new ArrayList<>();
    private  ArrayAdapter<Actividad> arrayAdapter;
    private List<Actividad> activityArray = new ArrayList<>();

    private List<String> nombreActividad = new ArrayList<>();
    private List<String> idActividad = new ArrayList<>();
    private List<String> horaCierre = new ArrayList<>();
    private List<String> horaApertura = new ArrayList<>();
    private AdapterActivity adapterActivity;

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    SimpleDateFormat sdfDay = new SimpleDateFormat("dd/MM/yyyy");
    private FirebaseFirestore db;

    private ListView lvActividades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_actividades);
        bClose = findViewById(R.id.bClose);
        bAddByCode = findViewById(R.id.bAddCode);
        Intent i = getIntent();
        db = FirebaseFirestore.getInstance();
        lvActividades = findViewById(R.id.lv_Actividades);
        day = i.getStringExtra("day");
/* TODO intentar hacer que funcione el reciclerView
        rv_actividades = findViewById(R.id.rvActividades);
        queryAllProjects();
        //TODO bucle con lo siguiente para añadir cosas
        adapter = new Adapter(getApplicationContext(), modelList);
        rv_actividades.setAdapter(adapter);
 */
        //try {
            prueba(UserSession.getUsuario().getIdGimnasios(), day);
            /*
        } catch (Exception e) {
            if (UserSession.getUsuario().getIdGimnasios() != 0) {
                e.printStackTrace();
                Toast.makeText(this, "No hay ninguna actividad", Toast.LENGTH_SHORT).show();
            } else {
                e.printStackTrace();
                Toast.makeText(this, "Inscribete a un gimansio para ver las actividades", Toast.LENGTH_SHORT).show();
            }
        }
        */
        lvActividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO comprobar si el esta completo la actividad
                //TODO apuntarse a la actividad
                String actID = idActividad.get(i);
                Actividad act = PojosClass.getActividadesDao().getActividad(Integer.parseInt(actID), actividad -> {
                    if (actividad.getAforo()==actividad.getAforo_actual()) {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.aforo_maximo) , Toast.LENGTH_SHORT).show();
                    } else {
                        try  {
                            Reserva res = PojosClass.getReservaDao().getReserva(UserSession.getUsuario().getUser()+actividad.getIdActividad(), reserva -> {
                                reserva.getIdReserva();
                            }, e -> {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                            if (res != null) {
                                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.ya_estas_incrito), Toast.LENGTH_SHORT).show();
                            } else {
                                PojosClass.getReservaDao().addReserva(new Reserva(UserSession.getUsuario().getUser(), actividad.getIdActividad() ,UserSession.getUsuario().getUser() + "" + actividad.getIdActividad()));
                                actividad.sumAforo_actual();
                                PojosClass.getActividadesDao().setActiviad(actividad);
                            }
                        } catch (Exception e) {
                            PojosClass.getReservaDao().addReserva(new Reserva(UserSession.getUsuario().getUser(), actividad.getIdActividad() ,UserSession.getUsuario().getUser() + "" + actividad.getIdActividad()));
                            actividad.sumAforo_actual();
                            PojosClass.getActividadesDao().setActiviad(actividad);
                        }
                    }
                }, e -> {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.reintentar), Toast.LENGTH_SHORT).show();
                });
            }
        });


        bClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bAddByCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
                alertBuilder.setTitle(R.string.adjunta_id_gym);
                final EditText activityID = new EditText(view.getContext());
                activityID.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8) }); // Se añade un maximo de numeros al edit text
                alertBuilder.setView(activityID);
                alertBuilder.setCancelable(true);
                alertBuilder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            PojosClass.getActividadesDao().getActividad(Integer.parseInt(activityID.getText().toString()), (actividad ->{
                                try {
                                    if (actividad.getAforo_actual() == actividad.getAforo()) {
                                        PojosClass.getUsuarioDAO().addGym(Integer.parseInt(activityID.getText().toString()));
                                        Reserva r1 = new Reserva(UserSession.getUsuario().getUser(), actividad.getIdActividad(), UserSession.getUsuario().getUser()+ actividad.getIdActividad());
                                        PojosClass.getReservaDao().addReserva(r1); // Se añade la nueva id de gimnasion en la session de usuario actual
                                        Toast.makeText(view.getContext(), getString(R.string.insctito_exito) + actividad.getIdActividad(), Toast.LENGTH_SHORT).show();
                                        //Resta uno en el aforo de la actividad
                                        actividad.sumAforo_actual();
                                        PojosClass.getActividadesDao().setActiviad(actividad);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "La actividad esta llena", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }), (failure -> {
                                Toast.makeText(view.getContext(), failure.getMessage(), Toast.LENGTH_SHORT).show();
                            }));
                        } catch (Exception e) {
                            Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton(R.string.cerrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), R.string.cancelado_operacion, Toast.LENGTH_SHORT).show();
                    }
                });
                alertBuilder.show();
            }
        });

    }

    public void prueba(int gymID, String actDate){
        idActividad = new ArrayList<>();
        db.collection(ComFunctions.ACTIVIDADES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getDouble("gymID") == (gymID)) {
                            Date selectedDay = null;
                            try {
                                selectedDay = sdfDay.parse(day);
                            } catch (Exception e) {
                            }
                                if (document.getDate("dia").equals(selectedDay)) {
                                    for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                                        if (entry.getKey().equals("idActividad")) {
                                            idActividad.add(String.valueOf(entry.getValue()));
                                        }
                                    }
                                }

                        }
                    }
                } else {
                    return;
                }
                getActividad(idActividad);
            }
        });
    }

    private void getActividad(List<String> idActividad) {
        nombreActividad = new ArrayList<>();
        horaApertura = new ArrayList<>();
        horaCierre = new ArrayList<>();
        db.collection(ComFunctions.ACTIVIDADES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        for (int i = 0; i<idActividad.size(); i++) {
                            if (document.getId().equals(idActividad.get(i))){
                                nombreActividad.add(document.getString("nombre"));
                                String hora_inicio = sdf.format(document.getDate("hora_inicio"));
                                String hora_fin = sdf.format(document.getDate("hora_fin"));
                                horaApertura.add(hora_inicio);
                                horaCierre.add(hora_fin);
                            }
                        }
                    }
                }
                adapterActivity = new AdapterActivity(view_actividades_activity.this, idActividad, nombreActividad, horaApertura, horaCierre);
                lvActividades.setAdapter(adapterActivity);
            }
        });
    }



        //Oddly enough, this log displays before the other logs.
        //Also, the list is empty here, which is probably what I'm unintentionally feeding into the Recycler View's adapter
}