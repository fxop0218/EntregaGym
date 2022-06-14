package com.example.gym.gymOwner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gym.Clases.Actividad;
import com.example.gym.Clases.Reserva;
import com.example.gym.R;
import com.example.gym.UserSession;
import com.example.gym.data.ComFunctions;
import com.example.gym.pojos.PojosClass;
import com.example.gym.reciclerLayout.AdapterActivity;
import com.example.gym.reciclerLayout.AdapterActivityOwner;
import com.example.gym.user.view_actividades_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class view_created_activity extends AppCompatActivity {

    private ListView lvUpcomign;
    private Button bCloseUp;
    FirebaseFirestore db;
    private List<String> nombreActividad = new ArrayList<>();
    private List<String> idActividad = new ArrayList<>();
    private List<String> horaCierre = new ArrayList<>();
    private List<String> horaApertura = new ArrayList<>();
    private List<String> diaActividad = new ArrayList<>();
    private List<String> idReserva = new ArrayList<>();
    private List<String> correctID = new ArrayList<>();

    private Calendar calendar;
    private AdapterActivityOwner adapterActivity;

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    SimpleDateFormat sdfDay = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_created);

        setContentView(R.layout.activity_view_upcoming);
        db = FirebaseFirestore.getInstance();
        bCloseUp = findViewById(R.id.bClose);
        lvUpcomign = findViewById(R.id.lvActividades);
        calendar = Calendar.getInstance();
        calendar.setLenient(false);
        //lvUpcomign = findViewById(R.id.lvUpcoming);

        prueba();


        /**
         * Cierra la activity cuando lo tienes correcto
         *
         */
        bCloseUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lvUpcomign.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO comprobar si el esta completo la actividad
                //TODO apuntarse a la actividad
                String actID = correctID.get(i);
                Actividad act = PojosClass.getActividadesDao().getActividad(Integer.parseInt(actID), actividad -> {
                    if (actividad.getAforo()==actividad.getAforo_actual()) {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.aforo_maximo) , Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                        dialog.setMessage("Delete activity");
                        dialog.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i3) {
                                PojosClass.getActividadesDao().deleteActividad(Integer.parseInt(actID));
                                getReservas(Integer.parseInt(actID));
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i2) {
                                finish();
                            }
                        });
                        dialog.show();
                    }
                }, e -> {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.reintentar), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    public void getReservas(int actividadID){
        idReserva = new ArrayList<>();
        db.collection("reservas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        double valD = document.getDouble("actividad");
                        int val = (int) valD;
                        if (val == actividadID) {
                        for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                            if (entry.getKey().equals("actividad")) {
                                    idReserva.add(String.valueOf(entry.getValue()));
                                }
                            }
                        }
                    }
                } else {
                    return;
                }
                for (int i = 0; i < idReserva.size(); i++) {
                    PojosClass.getReservaDao().deleteReserva(idReserva.get(i));
                }
            }
        });
    }

    public void prueba(){
        idActividad = new ArrayList<>();
        db.collection(ComFunctions.ACTIVIDADES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getString("gymID").equals(UserSession.getUsuario().getIdGimnasios())) {
                            for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                                if (entry.getKey().equals("idActividad")) {
                                    idActividad.add(String.valueOf(entry.getValue()));
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
                            if (document.getId().equals(idActividad.get(i))) {
                                Date diaCmp = document.getDate("dia");
                                calendar.setTime(diaCmp);
                                calendar.add(Calendar.MONTH, 1);
                                diaCmp = calendar.getTime();
                                if (diaCmp.after(ComFunctions.getActualDate()) || diaCmp.equals(ComFunctions.getActualDate())) {
                                    double n1 = document.getDouble("idActividad");
                                    int idCorrecto = (int) n1 ;
                                    correctID.add(idCorrecto+"");
                                    nombreActividad.add(document.getString("nombre"));
                                    String hora_inicio = sdf.format(document.getDate("hora_inicio"));
                                    String hora_fin = sdf.format(document.getDate("hora_fin"));
                                    horaApertura.add(hora_inicio);
                                    horaCierre.add(hora_fin);
                                    calendar.setTime(document.getDate("dia"));
                                    calendar.add(Calendar.MONTH, 1);
                                    String dia = sdfDay.format(calendar.getTime());
                                    diaActividad.add(dia);
                                }
                            }
                        }
                    }
                }
                adapterActivity = new AdapterActivityOwner(view_created_activity.this, correctID, nombreActividad, horaApertura, horaCierre, diaActividad);
                lvUpcomign.setAdapter(adapterActivity);
            }
        });
    }
}