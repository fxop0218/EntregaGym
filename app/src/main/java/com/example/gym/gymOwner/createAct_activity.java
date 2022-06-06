package com.example.gym.gymOwner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.gym.Clases.Actividad;
import com.example.gym.R;
import com.example.gym.UserSession;
import com.example.gym.data.ComFunctions;
import com.example.gym.pojos.PojosClass;
import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class createAct_activity extends AppCompatActivity {
    private Button bHoraA, bHoraC, bAdd;
    private EditText etHoraA, etHoraC, etActivityID, etNombreAct, etAforoMax;
    private int horaA, horaC, minuteA, minuteC;
    private Date day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_act);
        Intent actIntent = getIntent();
        day = ComFunctions.strToDateDay(actIntent.getStringExtra("day")); //Dia selecionado para añadir a la base de datos
        bHoraC = findViewById(R.id.bSelectHoraC);
        bHoraA = findViewById(R.id.bSelectHoraA);
        bAdd = findViewById(R.id.bAdd);

        etHoraA = findViewById(R.id.etHoraA);
        etHoraC = findViewById(R.id.etHoraC);
        etActivityID = findViewById(R.id.etIdActividad);
        etAforoMax = findViewById(R.id.etAforoMax);
        etNombreAct = findViewById(R.id.etName);

        /**
         * Seleciona la hora de Comienzo
         */

        bHoraA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        horaA = selectedHour;
                        minuteA = selectedMinute;
                        etHoraA.setText(String.format(Locale.getDefault(), "%02d:%02d", horaA, minuteA));
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(createAct_activity.this, AlertDialog.THEME_HOLO_DARK, onTimeSetListener, horaA, minuteA, true);
                timePickerDialog.setTitle("Seleciona la hora de apertura del gimnasio");
                timePickerDialog.show();
            }
        });

        bHoraC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        horaC = selectedHour;
                        minuteC = selectedMinute;
                        etHoraC.setText(String.format(Locale.getDefault(), "%02d:%02d", horaC, minuteC));
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(createAct_activity.this, AlertDialog.THEME_HOLO_DARK, onTimeSetListener, horaC, minuteC, true);
                timePickerDialog.setTitle("Seleciona la hora de apertura del gimnasio");
                timePickerDialog.show();
            }
        });

        etActivityID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ComFunctions.is_not_correct(etActivityID,8)) {
                    etActivityID.setError("El id de la actividad tiene que tener 8 numeros");
                    bAdd.setEnabled(setRegisterEnabled());
                }
            }
        });

        etNombreAct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ComFunctions.is_not_correct(etNombreAct,3, 40)) {
                    etNombreAct.setError("El nombre tiene que estar entre 3 y 40 catacteres");
                    bAdd.setEnabled(setRegisterEnabled());
                }
            }
        });

        etAforoMax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!etAforoMax.getText().toString().isEmpty()) {
                    if (Integer.parseInt(etAforoMax.getText().toString()) < 1 || Integer.parseInt(etAforoMax.getText().toString()) > 999) {
                        etAforoMax.setError("El aforo tiene que estar comprendido entre el 1 y el 999");
                        bAdd.setEnabled(setRegisterEnabled());
                    }
                }
            }
        });

        etHoraA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etHoraA.setError(null);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!etHoraC.getText().toString().isEmpty()) {
                    if (!ComFunctions.isCorrectHour(etHoraA.getText().toString(), etHoraC.getText().toString())) {
                        etHoraA.setError("Introduce una hora coerente");
                    }
                }
                bAdd.setEnabled(setRegisterEnabled());

            }
        });


        etHoraC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etHoraC.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!etHoraC.getText().toString().isEmpty()) {
                    if (!ComFunctions.isCorrectHour(etHoraA.getText().toString(), etHoraC.getText().toString())) {
                        etHoraC.setError("Introduce una hora coerente");
                    }
                }
                bAdd.setEnabled(setRegisterEnabled());
            }
        });

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idGym = UserSession.getUsuario().getIdGimnasios();
                Actividad a1 = new Actividad(Integer.parseInt(etActivityID.getText().toString()), etNombreAct.getText().toString(),
                        idGym, Integer.parseInt(etAforoMax.getText().toString()),
                        ComFunctions.strToDate(etHoraA.getText().toString()), ComFunctions.strToDate(etHoraC.getText().toString()), day);
                PojosClass.getActividadesDao().setActiviad(a1);
                Toast.makeText(getApplicationContext(), "Actividad añadida con exito", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
    private boolean setRegisterEnabled() {
        if (ComFunctions.is_not_correct(etNombreAct, 3, 40)) return false;

        if (etAforoMax.getText().toString().isEmpty()) {
            return false;
        } else {
            if (Integer.parseInt(etAforoMax.getText().toString()) < 1 || Integer.parseInt(etAforoMax.getText().toString()) > 999) {
                return false;
            }
        }
        if (Integer.parseInt(etAforoMax.getText().toString()) < 1 || Integer.parseInt(etAforoMax.getText().toString()) > 999) return false;
        if (!ComFunctions.isCorrectHour(etHoraA.getText().toString(), etHoraC.getText().toString())) return false;
        return true;
    }
}