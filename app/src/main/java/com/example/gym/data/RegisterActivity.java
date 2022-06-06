package com.example.gym.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gym.Clases.Usuario;
import com.example.gym.MainActivity;
import com.example.gym.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class RegisterActivity extends Activity {
    private EditText etDni, etName, etSurname, etYear, etUserName, etPwd, etCfnPwd;
    private Button bRegister;
    private int actYear = Calendar.getInstance().get(Calendar.YEAR);
    private int year = 0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etDni = findViewById(R.id.etDni);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etUserName = findViewById(R.id.etUserName);
        etPwd = findViewById(R.id.etPwd);
        etCfnPwd = findViewById(R.id.etCfnPwd);
        etYear = findViewById(R.id.etYear);
        bRegister = findViewById(R.id.bRegsiter);

        bRegister.setEnabled(false);

        etDni.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!dni_validator()) {
                    etDni.setError("El dni tiene que contener 9 caracteres");
                    bRegister.setEnabled(setRegisterEnabled());
                }
            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (is_not_correct(etName, 3, 20)) {
                    etName.setError("El nombre tiene que tener entre 3 y 20 letras");
                    bRegister.setEnabled(setRegisterEnabled());
                }
            }
        });

        etSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (is_not_correct(etSurname, 5, 20)) {
                    etSurname.setError("El apellido tiene que tener entre 5 y 20 letras");
                    bRegister.setEnabled(setRegisterEnabled());
                }
            }
        });


        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (is_not_correct(etUserName, 5, 30)) {
                    etUserName.setError("El nombre de usuario tiene que tener entre 5 y 30 letras");
                    bRegister.setEnabled(setRegisterEnabled());
                }
            }
        });

        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (is_not_correct(etPwd, 5, 20)) {
                    etPwd.setError("La contraseña tiene que tener entre 5 y 20 letras");
                    bRegister.setEnabled(setRegisterEnabled());
                }
            }
        });

        etCfnPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!etPwd.getText().toString().equals(etCfnPwd.getText().toString())) {
                    etCfnPwd.setError("La contraseña no coincide");
                }
                bRegister.setEnabled(setRegisterEnabled());
            }
        });

        etYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etYear.getText().toString().isEmpty()) {
                    year = 0;
                } else {
                    year = Integer.parseInt(etYear.getText().toString());
                }
                if (is_not_correct_year(year)) {
                    etYear.setError("El año no esta entre los valores " + (actYear - 100) + " - " + actYear);
                }
                bRegister.setEnabled(setRegisterEnabled());
            }
        });
    }

    public void register(View view){
        //TODO guardar en la base de datos, si no se puede porque hay un usario on el mismo nombre te salta un error
        Usuario u1 = new Usuario(etName.getText().toString(), etSurname.getText().toString(), etDni.getText().toString(), Integer.parseInt(etYear.getText().toString()), etUserName.getText().toString(), etPwd.getText().toString());
        db.collection("users").document(u1.getUser()).set(u1);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public boolean setRegisterEnabled() {
        boolean correctLogin = true;
        if (is_not_correct(etName, 3, 20)) {
            correctLogin = false;
        };
        if (is_not_correct(etSurname, 5, 20)) {
            correctLogin = false;
        }
        if (is_not_correct_year(year)) correctLogin = false;
        //            etYear.setError("El año no puede ser inferior a " + (actYear - 100) + " y no puede ser superior a " + actYear);
        //            correctLogin = false;
        if (is_not_correct(etUserName, 5, 30)) correctLogin = false;
        if (is_not_correct(etPwd, 6, 30)) correctLogin = false;
        if (!etCfnPwd.getText().toString().equals(etPwd.getText().toString())) correctLogin = false;
        if (is_not_correct(etDni, 9)) correctLogin = false;
        return correctLogin;
    }

    private boolean is_not_correct_year(int year) {
        if (is_not_correct(etYear, 4)) return true;
        if (year < actYear - 100 || year > actYear) return true;
        return false;
    }

    private boolean is_not_correct(EditText etValidator, int i, int i2) {
        if (etValidator.getText().toString().length() < i || etValidator.getText().toString().length() > i2) return true;
        return false;
    }

    private boolean is_not_correct(EditText etValidator, int i) {
        if (etValidator.getText().toString().length() != i) return true;
        return false;
    }

    private boolean dni_validator() {
        String dni = etDni.getText().toString();
        String mayusLetter = "";
        if (dni.length() != 9 || !Character.isLetter(dni.charAt(8))) {
            return false;
        }
        mayusLetter = (dni.substring(8)).toUpperCase();

        if (onlyNumbers(dni) && letraDNI().equals(mayusLetter)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * Mira si el dni introducido por el usuario es correcto
     * y es un dni existente, es decir, que la letra coincide con
     * el calculo de los numeros anteriores
     *
     *
     * @param dni
     * @return Si el dni es correcto devuelve true, si no lo es false
     */
    private boolean onlyNumbers(String dni){
        int i,j=0;
        String numero="";
        String miDNI="";
        String[] unoNueve={"0","1","2","3","4","5","6","7","8","9"};
        for(i=0;i< dni.length()-1; i++) {
            numero = dni.substring(i, i + 1);

            for (j = 0; j < unoNueve.length; j++) {
                if (numero.equals(unoNueve[j])) {
                    miDNI += unoNueve[j];
                }
            }
        }
        if(miDNI.length() != 8){
            return false;
        } else {
            return true;
        }
    }

    private String letraDNI(){
        String dni = etDni.getText().toString();
        int miDNI=Integer.parseInt(dni.substring(0,8));
        int resto=0;
        String miletra="";
        String[] asignacionletra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X","B","N", "J", "Z","S","Q","V", "H", "L", "C", "K","E"};
        resto = miDNI % 23;
        miletra = asignacionletra[resto];
        return miletra;
    }
}