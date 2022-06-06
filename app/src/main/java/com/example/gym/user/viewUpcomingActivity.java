package com.example.gym.user;

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
import com.example.gym.pojos.PojosClass;

import java.util.ArrayList;

public class viewUpcomingActivity extends AppCompatActivity {

    private ListView lvUpcomign;
    private Button bCloseUp;
    ArrayList<Reserva> registerArray = new ArrayList<>();
    ArrayList<Actividad> activityArray = new ArrayList<>();
    private AlertDialog.Builder builder = new AlertDialog.Builder(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_upcoming);
        bCloseUp = findViewById(R.id.bCloseUp);
        //lvUpcomign = findViewById(R.id.lvUpcoming);

        registerArray = PojosClass.getReservaDao().getReservaByUserName(UserSession.getUsuario().getUser());

        try {
            activityArray = getUserActivity(registerArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        ArrayAdapter<Actividad> arrayAdapter = new ArrayAdapter<Actividad>(this, R.layout.activity_view_actividades, R.id.textView, activityArray);
        lvUpcomign.setAdapter(arrayAdapter);

        lvUpcomign.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO poner un alert dialog que te permita ver mas detalles
                //TODO En el alertDialog poner que puedas borrarte de la acividad

                Actividad selectedAct = activityArray.get(i); //Guarda la acividad selecionada
                builder.setMessage(R.string.eliminar_actividad).setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            PojosClass.getReservaDao().deleteReserva(UserSession.getUsuario().getUser(), selectedAct.getIdActividad());
                            Toast.makeText(getApplicationContext(), R.string.eliminar_reserva, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), R.string.erro_eliminar_actividad, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), R.string.accion_cancelada, Toast.LENGTH_SHORT).show();
                            }
                        }
                );
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
}