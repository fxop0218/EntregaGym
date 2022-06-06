package com.example.gym.data;

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

import com.example.gym.Clases.Gym;
import com.example.gym.R;
import com.example.gym.pojos.PojosClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterGymActivity extends AppCompatActivity {
    private EditText etGymID, etCity, etPostalCode, etHoraA, etHoraC;
    private Button bNextStep;
    private Button dataPickerHoraA, dataPickerHoraC;
    private SimpleDateFormat df = new SimpleDateFormat("HH:mm");
    private int horaC, minuteC, horaA, minuteA;
    private AtomicBoolean correct = new AtomicBoolean(false);

    /**
     * Creación de un nuevo gimnasio
     * Comprueba que todos los fatos son correctos y si no existe uno con la misma id
     * en caso positivo lo añade a la base de datos y te envía a la creación de un usuario
     * en caso positivo no te deja
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_gym);

        //Declare all the EditText
        etGymID = findViewById(R.id.etIdGym);
        etCity = findViewById(R.id.etCiudad);
        etPostalCode = findViewById(R.id.etCodigoPostal);
        etHoraA = findViewById(R.id.etHoraA);
        etHoraC = findViewById(R.id.etHoraC);
        bNextStep = findViewById(R.id.bNextStep);
        //Botones que permiten selecionar la hora
        dataPickerHoraA = findViewById(R.id.bSelectHoraA);
        dataPickerHoraC = findViewById(R.id.bSelectHoraC);

        //Listeners
        etGymID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ComFunctions.is_not_correct(etGymID,8)) {
                    etGymID.setError("El id del gimnasio tiene que tener 8 numeros");
                    bNextStep.setEnabled(setRegisterEnabled());
                }
            }
        });

        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ComFunctions.is_not_correct(etCity, 5, 30)) {
                    etCity.setError("El nombre de la ciudad tiene que tener entre 5 y 30 letras");
                    bNextStep.setEnabled(setRegisterEnabled());
                }
            }
        });

        etPostalCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ComFunctions.is_not_correct(etPostalCode, 5)) {
                    etPostalCode.setError("El codigo postal tiene que tener 5 numeros");
                    bNextStep.setEnabled(setRegisterEnabled());
                }
            }
        });

        etHoraA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etHoraA.getText().toString() != null) bNextStep.setEnabled(setRegisterEnabled());
            }
        });

        etHoraC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etHoraC.getText().toString() != null) bNextStep.setEnabled(setRegisterEnabled());
            }
        });
    }

    /**
     * Pasa al siguiente paso cuando todos los campos están rellenados de forma correcta
     * Comprueba si existe un gimnasio con la misma id, si es asi te salta un toast
     * @param v
     */
    public void bNextStep (View v) {
        //TODO Guardar aqui en la base de datos los archivos
        correct.set(false);
        try {
            Gym gym = PojosClass.getGymDAO().getGym(Integer.parseInt(etGymID.getText().toString()), (g -> {
                if (g != null) {
                    Toast.makeText(getApplicationContext(), "Ya existe un gimnasio con la ID" + g.getIdGym(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Gym g1 = new Gym(Integer.parseInt(etGymID.getText().toString()), etCity.getText().toString(), Integer.parseInt(etPostalCode.getText().toString()), df.parse(etHoraA.getText().toString()), df.parse(etHoraC.getText().toString()));
                        PojosClass.getGymDAO().setNewGym(g1);
                        Intent i = new Intent(this, RegisterActivity.class);
                        i.putExtra("owner", true);
                        i.putExtra("gymID", g1.getIdGym());
                        startActivity(i);
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                }
            }),(e -> {
                try {
                    Gym g1 = new Gym(Integer.parseInt(etGymID.getText().toString()), etCity.getText().toString(), Integer.parseInt(etPostalCode.getText().toString()), df.parse(etHoraA.getText().toString()), df.parse(etHoraC.getText().toString()));
                    PojosClass.getGymDAO().setNewGym(g1);
                    Intent i = new Intent(this, RegisterActivity.class);
                    i.putExtra("owner", true);
                    i.putExtra("gymID", g1.getIdGym());
                    startActivity(i);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }));
        } catch (Exception e) {
            Toast.makeText(this, "Error al introducir el gimnasio a la base de datos", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Seleccionar la hora de apertura mediante un PopUp
     * @param v
     */
    public void popTimerPickerApertura (View v) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                horaA = selectedHour;
                minuteA = selectedMinute;
                etHoraA.setText(String.format(Locale.getDefault(), "%02d:%02d", horaA, minuteA));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, onTimeSetListener, horaA, minuteA, true);
        timePickerDialog.setTitle("Seleciona la hora de apertura del gimnasio");
        timePickerDialog.show();
    }

    /**
     * Selecciona la hora de cierre mediante un PopUp
     * @param v
     */
    public void popTimerPickerCierre (View v) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                horaC = selectedHour;
                minuteC = selectedMinute;
                etHoraC.setText(String.format(Locale.getDefault(), "%02d:%02d", horaC, minuteC));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, onTimeSetListener, horaC, minuteC, true);
        timePickerDialog.setTitle("Seleciona la hora de cierre del gimnasio");
        timePickerDialog.show();
    }

    /**
     * Mantiene el botón de registrar enabled mientras los campos estén mal rellenados
     * @return boolean false cuando no están bien rellenados, true cuando están correctamente rellenados
     */
    public boolean setRegisterEnabled() {
        boolean correctLogin = true;
        if (ComFunctions.is_not_correct(etGymID, 8)) {
            correctLogin = false;
        };
        if (ComFunctions.is_not_correct(etCity, 5, 20)) {
            correctLogin = false;
        }
        if (ComFunctions.is_not_correct(etPostalCode, 5)) correctLogin = false;
        if (etHoraA.getText().toString() == null) correctLogin = false;
        if (etHoraC.getText().toString() == null) correctLogin = false;
        return correctLogin;
    }
}