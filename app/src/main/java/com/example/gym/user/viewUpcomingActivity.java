package com.example.gym.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class viewUpcomingActivity extends AppCompatActivity {

    private ListView lvUpcomign;
    private Button bCloseUp;
    FirebaseFirestore db;
    private List<String> nombreActividad = new ArrayList<>();
    private List<String> idActividad = new ArrayList<>();
    private List<String> horaCierre = new ArrayList<>();
    private List<String> horaApertura = new ArrayList<>();
    private List<String> diaAct = new ArrayList<>();
    private List<String> correctID = new ArrayList<>();

    private AdapterActivityOwner adapterActivity;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    SimpleDateFormat sdfDay = new SimpleDateFormat("dd/MM/yyyy");
    private Calendar calendar;

    ArrayList<Reserva> registerArray = new ArrayList<>();
    ArrayList<Actividad> activityArray = new ArrayList<>();
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_upcoming);
        db = FirebaseFirestore.getInstance();
        bCloseUp = findViewById(R.id.bClose);
        lvUpcomign = findViewById(R.id.lvActividades);
        calendar = Calendar.getInstance();
        calendar.setLenient(false);
        //lvUpcomign = findViewById(R.id.lvUpcoming);


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
        prueba();

        lvUpcomign.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO poner un alert dialog que te permita ver mas detalles
                //TODO En el alertDialog poner que puedas borrarte de la acividad

                String actID = correctID.get(i);
                Actividad act = PojosClass.getActividadesDao().getActividad(Integer.parseInt(actID), actividad -> {
                    builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage(R.string.eliminar_actividad).setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                PojosClass.getReservaDao().deleteReserva(UserSession.getUsuario().getUser(), actividad.getIdActividad());
                                actividad.resAforo_actual();
                                PojosClass.getActividadesDao().setActiviad(actividad);
                                Toast.makeText(view.getContext(), R.string.eliminar_reserva, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(view.getContext(), R.string.erro_eliminar_actividad, Toast.LENGTH_SHORT).show();
                            }
                        }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(view.getContext(), R.string.accion_cancelada, Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                }, (e -> {
                    Toast.makeText(view.getContext(), getText(R.string.erro_eliminar_actividad), Toast.LENGTH_SHORT).show();
                }));
            }
        });
    }

    private ArrayList<Actividad> getUserActivity (ArrayList<Reserva> registerArray) throws Exception {
        ArrayList<Actividad> actList = new ArrayList<>();
        if (registerArray.isEmpty()) {
            throw new Exception(getString(R.string.error_encontrar_actividad));
        } else {
            for (Reserva reserva : registerArray) {
                Actividad act = PojosClass.getActividadesDao().getActividadById(reserva.getActividad(), listener -> {

                });
                actList.add(act);
            }
        }
        if (!actList.isEmpty()) return actList;
        Toast.makeText(getApplicationContext(), R.string.error_encontrar_actividad_proxima, Toast.LENGTH_LONG).show();
        throw new Exception(getString(R.string.no_proximas_actividades));
    }

    public void prueba(){
        idActividad = new ArrayList<>();
        db.collection("reservas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getString("usuario").equals(UserSession.getUsuario().getUser())) {
                            for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                                if (entry.getKey().equals("actividad")) {
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
        diaAct = new ArrayList<>();
        db.collection(ComFunctions.ACTIVIDADES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        for (int i = 0; i<idActividad.size(); i++) {
                            if (document.getId().equals(idActividad.get(i))){
                                Date diaCmp = document.getDate("dia");
                                calendar.setTime(diaCmp);
                                calendar.add(Calendar.MONTH, 1);
                                diaCmp = calendar.getTime();
                                if (diaCmp.after(ComFunctions.getActualDate()) || diaCmp.equals(ComFunctions.getActualDate())) {
                                    double vl1 = document.getDouble("idActividad");
                                    int vl2 = (int)vl1;
                                    correctID.add(vl2+"");
                                    nombreActividad.add(document.getData().get("nombre").toString());
                                    String hora_inicio = sdf.format(document.getDate("hora_inicio"));
                                    String hora_fin = sdf.format(document.getDate("hora_fin"));
                                    horaApertura.add(hora_inicio);
                                    horaCierre.add(hora_fin);
                                    String dia = sdfDay.format(calendar.getTime());
                                    diaAct.add(dia);
                                }

                            }
                        }
                    }
                }
                adapterActivity = new AdapterActivityOwner(viewUpcomingActivity.this, correctID, nombreActividad, horaApertura, horaCierre, diaAct);
                lvUpcomign.setAdapter(adapterActivity);
            }
        });
    }
}