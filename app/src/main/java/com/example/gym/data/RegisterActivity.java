package com.example.gym.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.gym.Clases.Usuario;
import com.example.gym.MainActivity;
import com.example.gym.R;
import com.example.gym.UserSession;
import com.example.gym.pojos.PojosClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class RegisterActivity extends Activity {

    private EditText etDni, etName, etSurname, etYear, etUserName, etPwd, etCfnPwd;
    private Button bRegister, bCreateGymAccount;
    private int year = 0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private boolean isOwner = false;
    private int idGym = 0;
    private String DNIerror = "El dni tiene que contener 9 caracteres", NameError = "El nombre tiene que tener entre 3 y 20 letras", SurnameError = "El apellido tiene que tener entre 5 y 20 letras", UserNameError = "El correo debe tener entre 8 y 30 letras y debe contener @", PwdError = "La contraseña tiene que tener entre 5 y 20 letras";

    /**
     * Actividad donde se registran los usuarios,
     * La actividad cambia dependiendo de si el usuario ha registrado un gimnasio anteriormente o no
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent i = getIntent();

        etDni = findViewById(R.id.etDni);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etUserName = findViewById(R.id.etUserName);
        etPwd = findViewById(R.id.etPwd);
        etCfnPwd = findViewById(R.id.etCfnPwd);
        etYear = findViewById(R.id.etYear);
        bRegister = findViewById(R.id.bRegsiter);
        bCreateGymAccount = findViewById(R.id.bCreateGymAccount);
        isOwner = i.getBooleanExtra("owner", false);
        idGym = i.getIntExtra("gymID", 0);

        if (isOwner) bCreateGymAccount.setVisibility(View.INVISIBLE);

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
                if (!DniValidator.dni_validator(etDni)) {
                    etDni.setError(DNIerror);
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
                if (ComFunctions.is_not_correct(etName, 3, 20)) {
                    etName.setError(NameError);
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
                if (ComFunctions.is_not_correct(etSurname, 5, 20)) {
                    etSurname.setError(SurnameError);
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
                if (ComFunctions.is_not_correct(etUserName, 8, 30) || !etUserName.getText().toString().contains("@") && !etUserName.getText().toString().contains(".")) {

                    etUserName.setError(UserNameError);
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
                if (ComFunctions.is_not_correct(etPwd, 5, 20)) {
                    etPwd.setError(PwdError);
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
                try {
                    if (etYear.getText().toString().isEmpty()) {
                        year = 0;
                    } else {
                        year = Integer.parseInt(etYear.getText().toString());
                    }
                    if (ComFunctions.is_not_correct_year(year, etYear)) {
                        etYear.setError("El año no esta entre los valores " + (ComFunctions.actYear - 100) + " - " + ComFunctions.actYear);
                    }
                    bRegister.setEnabled(setRegisterEnabled());
                } catch (Exception e) {
                    etYear.setError("El año no tiene el formato correcto");
                }
            }
        });
    }

    public void register(View view) throws Exception {
        //TODO guardar en la base de datos, si no se puede porque hay un usario on el mismo nombre te salta un error
        PojosClass.getUsuarioDAO().getUsuario(etUserName.getText().toString(), (usr -> {
            if (usr != null) {
                Toast.makeText(getApplicationContext(), "Ya existe un usuario con el correo " + usr.getUser(), Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Usuario u1 = new Usuario(etName.getText().toString(), etSurname.getText().toString(), etDni.getText().toString(), Integer.parseInt(etYear.getText().toString()), etUserName.getText().toString(), Encript.encriptar(etPwd.getText().toString()), idGym, isOwner);
                    db.collection("users").document(u1.getUser()).set(u1);
                    UserSession.setUsuario(u1);
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        }), (e -> {
            try {
                Usuario u1 = new Usuario(etName.getText().toString(), etSurname.getText().toString(), etDni.getText().toString(), Integer.parseInt(etYear.getText().toString()), etUserName.getText().toString(), Encript.encriptar(etPwd.getText().toString()));
                db.collection("users").document(u1.getUser()).set(u1);
                UserSession.setUsuario(u1);
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } catch (Exception exception) {
                exception.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error al crear el usuario", Toast.LENGTH_SHORT).show();
            }
        }));

        firebaseAuth.createUserWithEmailAndPassword(etUserName.getText().toString(), etPwd.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                user.sendEmailVerification();
            }
        });
    }

    public boolean setRegisterEnabled() {
        boolean correctLogin = true;
        if (ComFunctions.is_not_correct(etName, 3, 20)) {
            correctLogin = false;
        };
        if (ComFunctions.is_not_correct(etSurname, 5, 20)) {
            correctLogin = false;
        }
        if (ComFunctions.is_not_correct_year(year, etYear)) correctLogin = false;
        if (ComFunctions.is_not_correct(etUserName, 5, 30)) correctLogin = false;
        if (ComFunctions.is_not_correct(etPwd, 6, 30)) correctLogin = false;
        if (!etCfnPwd.getText().toString().equals(etPwd.getText().toString())) correctLogin = false;
        if (ComFunctions.is_not_correct(etDni, 9)) correctLogin = false;
        return correctLogin;
    }

    public void bCreateGymAccount (View v) {
        Intent i = new Intent(this, RegisterGymActivity.class);
        startActivity(i);
    }
}