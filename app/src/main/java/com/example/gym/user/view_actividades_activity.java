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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private FirebaseFirestore db;

    ListView lvActividades;

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

        arrayAdapter = new ArrayAdapter<Actividad>(this, R.layout.activity_view_actividades, R.id.textView, correctActivity);
        lvActividades.setAdapter(arrayAdapter);

        lvActividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO comprobar si el esta completo la actividad
                //TODO apuntarse a la actividad
                Actividad act = correctActivity.get(i);
                ArrayList<Reserva> reservaList = PojosClass.getReservaDao().getReservaByUserName(UserSession.getUsuario().getUser());

                boolean notAlisted = true;
                int r = 0;
                while (r < reservaList.size() && !notAlisted) {
                    if (act.getIdActividad() == reservaList.get(r).getActividad()) {
                        notAlisted = false;
                    }
                    r++;
                }

                if (notAlisted) {
                    try {
                        PojosClass.getReservaDao().addReserva(new Reserva(UserSession.getUsuario().getUser(), act.getIdActividad() ,UserSession.getUsuario().getUser() + "" + act.getIdActividad()));
                        act.resAforo_actual();
                        PojosClass.getActividadesDao().setActiviad(act); //Se actualiza la actividad

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), R.string.error_asignacion_actividad, Toast.LENGTH_SHORT).show();
                    }
                } else { //En caso de estar apuntado en la actividad, muestra un mensaeje;
                    Toast.makeText(getApplicationContext(), R.string.ya_apuntado_actividad, Toast.LENGTH_SHORT).show();
                }
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
        try {
            prueba(UserSession.getUsuario().getIdGimnasios(), day);
        } catch (Exception e) {
            if (UserSession.getUsuario().getIdGimnasios() != 0) {
                Toast.makeText(this, "No hay ninguna actividad", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Inscribete a un gimansio para ver las actividades", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<Actividad> correctActivitys(List<Actividad> allActivitys) {
        ArrayList<Actividad> correctActividades = new ArrayList<>();

        for (int i = 0; i < allActivitys.size(); i++) {
            if (allActivitys.get(i).getAforo() > allActivitys.get(i).getAforo_actual()) {
                correctActividades.add(allActivitys.get(i));
            }
        }
        return correctActividades;
    }

    public void prueba(int gymID, String actDate) throws Exception{
        String gymIDString = gymID + "";
        List<String> idActividad = new ArrayList<>();
        db.collection(ComFunctions.ACTIVIDADES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Map<String, Object> activityInfo = null;
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String gym = document.getData().get("gymID").toString();
                        if (gym.equals(gymIDString)) {
                            activityInfo = document.getData();
                        }
                    }
                }
                if (activityInfo.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No hay actividades disponibles este dia", Toast.LENGTH_SHORT).show();
                } else {
                    idActividad.addAll(activityInfo.keySet()); //TODO mirar que esta pasando aqui
                    getActividad(idActividad);
                }
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
                                nombreActividad.add(document.getData().get("nombre").toString());
                                String hora_inicio = sdf.format(document.getDate("hora_inicio"));
                                String hora_fin = sdf.format(document.get("hora_fin"));
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


    private void queryAllProjects() {
        //I've already tried to make a local list and return that, however,
        //the compiler would force me to declare the list as final here, and it wouldn't solve anything.
        db.collection(ComFunctions.ACTIVIDADES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Actividad actividad = document.toObject(Actividad.class);
                                activityArray.add(actividad);

                            }
                            //Here the list is OK. Filled with projects.
                            //I'd like to save the state of the list from here
                        } else {
                            Toast.makeText(getApplicationContext(), "Error al buscar actividades", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "No se ha encontrado ninguna atividad", Toast.LENGTH_SHORT).show();
            }
        });
        //Oddly enough, this log displays before the other logs.
        //Also, the list is empty here, which is probably what I'm unintentionally feeding into the Recycler View's adapter
    }
}