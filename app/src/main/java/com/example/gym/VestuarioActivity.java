package com.example.gym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class VestuarioActivity extends AppCompatActivity {

    private String urlDecaM = "https://www.decathlon.es/es/browse/c0-deportes/c1-fitness-cardio/c2-ropa-mujer-fitness-gym/_/N-uh595a";
    private String urlDecaH = "https://www.decathlon.es/es/browse/c0-deportes/c1-fitness-cardio/c2-ropa-hombre-fitness-gym/_/N-16u84zq";
    private ImageView ivMujer, ivHombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vestuario);

        ivHombre = findViewById(R.id.ivHombres);

        ivMujer = findViewById(R.id.ivMujeres);

        ivHombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uriDeca = Uri.parse(urlDecaH);
                Intent i = new Intent(Intent.ACTION_VIEW, uriDeca);
                startActivity(i);
            }
        });

        ivMujer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uriDeca = Uri.parse(urlDecaM);
                Intent i = new Intent(Intent.ACTION_VIEW, uriDeca);
                startActivity(i);
            }
        });
    }
}