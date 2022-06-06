package com.example.gym;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

public class TiendaFragment extends Fragment {

    private String urlDecaMat = "https://www.decathlon.es/es/browse/c0-deportes/c1-fitness-cardio/c2-accesorios/_/N-1go8kxh";
    private String urlDecaVM = "https://www.decathlon.es/es/browse/c0-deportes/c1-fitness-cardio/c2-ropa-mujer-fitness-gym/_/N-uh595a";
    private String urlDecaSup = "https://www.decathlon.es/es/browse/c0-salud-y-bienestar/c1-alimentacion-saludable/_/N-1mfwa2e";

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tienda, container, false);

        ImageButton ibMaterial = view.findViewById(R.id.ibMaterial);
        ImageButton ibSuplementos = view.findViewById(R.id.ibSuplementos);
        ImageButton ibVestuario = view.findViewById(R.id.ibVestuario);

        ibMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uriDeca = Uri.parse(urlDecaMat);
                Intent i = new Intent(Intent.ACTION_VIEW, uriDeca);
                startActivity(i);
            }
        });

        ibSuplementos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uriDeca = Uri.parse(urlDecaSup);
                Intent i = new Intent(Intent.ACTION_VIEW, uriDeca);
                startActivity(i);
            }
        });

        ibVestuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uriDeca = Uri.parse(urlDecaVM);
                Intent i = new Intent(Intent.ACTION_VIEW, uriDeca);
                startActivity(i);
            }
        });

        return view;
    }
}