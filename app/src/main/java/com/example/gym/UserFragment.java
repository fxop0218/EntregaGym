package com.example.gym;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.lang.UScript;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.RadioAccessSpecifier;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gym.data.Encript;
import com.example.gym.pojos.PojosClass;
import com.example.gym.pojos.UsersDAO;
import com.example.gym.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button bSetGym, bEliminarUsuario, bCambaiarContraseña;
    private TextView tvUser;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btCerrarSesion;
    private String gola;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }

    }

    /**
     * Creación del fragmento de administración del usuario, en el cual
     * puedes cambiar el ID de gimnasio, contraseña y salir de la sesión.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return v
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        bSetGym = v.findViewById(R.id.bAltaGym);
        btCerrarSesion = v.findViewById(R.id.cerrar_sesion);
        bEliminarUsuario = v.findViewById(R.id.bEliminarCuenta);
        bCambaiarContraseña = v.findViewById(R.id.bCambiarContra);
        tvUser = v.findViewById(R.id.tvUsername);

        tvUser.setText("PERFIL DE " + UserSession.getUsuario().getUser());

        btCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
            }
        });

        bSetGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(v.getContext());
                alertBuilder.setTitle(R.string.adjunta_id_gym);
                final EditText etGymID = new EditText(v.getContext());
                etGymID.setInputType(InputType.TYPE_CLASS_NUMBER);
                etGymID.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8) }); // Se añade un maximo de numeros al edit text
                alertBuilder.setView(etGymID);
                alertBuilder.setCancelable(true);
                alertBuilder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!etGymID.getText().toString().isEmpty() && etGymID.getText().toString().length() == 8) {
                            try {
                                PojosClass.getGymDAO().getGym(Integer.parseInt(etGymID.getText().toString()), (gym ->{
                                    try {
                                        PojosClass.getUsuarioDAO().addGym(Integer.parseInt(etGymID.getText().toString()));
                                        Toast.makeText(v.getContext(), getString(R.string.insctito_exito) + gym.getIdGym(), Toast.LENGTH_SHORT).show();
                                        UserSession.getUsuario().setIdGimnasios(Integer.parseInt(etGymID.getText().toString())); // Se añade la nueva id de gimnasion en la session de usuario actual

                                    } catch (Exception e) {
                                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }), (failure -> {
                                    Toast.makeText(v.getContext(), failure.getMessage(), Toast.LENGTH_SHORT).show();
                                }));
                            } catch (Exception e) {
                                Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), R.string.id_correcta, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cerrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(v.getContext(), R.string.cancelado_operacion, Toast.LENGTH_SHORT).show();
                    }
                });
                alertBuilder.show();
            }
        });

        bCambaiarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean[] correct = {false};
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(v.getContext());
                alertBuilder.setTitle("Intorduce tu constraseña actual");
                final EditText etPwd = new EditText(v.getContext());
                etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)}); // Se añade un maximo de numeros al edit text
                alertBuilder.setView(etPwd);
                alertBuilder.setCancelable(true);
                alertBuilder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            if (Encript.encriptar(etPwd.getText().toString()).equals(UserSession.getUsuario().getPassword())) {
                                correct[0] = true;
                            } else {
                                Toast.makeText(v.getContext(), "La contraseña no coincide", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                alertBuilder.show();
                if (correct[0]) {
                    AlertDialog.Builder alertBuilder2 = new AlertDialog.Builder(v.getContext());
                    final EditText etNewPwd = new EditText(v.getContext());
                    etNewPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etNewPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                    alertBuilder2.setView(etNewPwd);
                    alertBuilder2.setCancelable(true);

                    alertBuilder2.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (etNewPwd.getText().toString().length() < 7) {
                                Toast.makeText(v.getContext(), "La contraseña tiene que tener 6 caracteres minimo", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    UserSession.getUsuario().setPassword(Encript.encriptar(etNewPwd.getText().toString()));
                                    PojosClass.getUsuarioDAO().setUsuario(UserSession.getUsuario());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    alertBuilder2.show();
                }
            }
        });

        bEliminarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(v.getContext());
                final EditText etPwd = new EditText(v.getContext());
                etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                alertBuilder.setView(etPwd);
                alertBuilder.setCancelable(true);
                alertBuilder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            if (Encript.encriptar(etPwd.getText().toString()).equals(UserSession.getUsuario().getPassword())) {
                                PojosClass.getUsuarioDAO().deleteUser(UserSession.getUsuario().getUser());
                                Toast.makeText(v.getContext(), "Usuario eliminado con exito", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                                startActivity(intent);
                                FirebaseAuth.getInstance().signOut();
                            } else {
                                Toast.makeText(v.getContext(), "La contraseña no coincide", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                alertBuilder.show();
            }
        });

        return v;
    }
}