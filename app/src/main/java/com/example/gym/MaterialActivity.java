package com.example.gym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MaterialActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    /*
    private String urlBolsa = "https://www.decathlon.es/es/p/bolsa-deporte-fitness-plegable-para-ir-al-gym-30l/_/R-p-11233?mc=8200579&c=NEGRO";
    private String urlGuantes = "https://www.decathlon.es/es/p/guantes-gimansio-musculacion-100-negro/_/R-p-326377?mc=8595161&c=NEGRO";
    private String urlBolsa_Asas = "https://www.decathlon.es/es/p/petate-mochila-fitness-con-cuerdas-15-l/_/R-p-324636?mc=8584676&c=NEGRO";
    private String urlToalla = "https://www.decathlon.es/es/p/toalla-gimnasio-fitness-domyos-90x50cm/_/R-p-170251?mc=8379823&c=NEGRO";
    private String urlGorra = "https://www.decathlon.es/es/p/gorra-fitness-negra-adidas/_/R-p-X8623877?mc=8623877";
    */

    private String urlDeca = "https://www.decathlon.es/es/browse/c0-deportes/c1-fitness-cardio/c2-accesorios/_/N-1go8kxh";
    private ImageView ivDeca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        recyclerView = findViewById(R.id.recyclerProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewAdapter(obtenerProductos());
        recyclerView.setAdapter(recyclerViewAdapter);

        ivDeca = findViewById(R.id.ivDecaMat);
        ivDeca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uriDeca = Uri.parse(urlDeca);
                Intent i = new Intent(Intent.ACTION_VIEW, uriDeca);
                startActivity(i);
            }
        });
    }

    public List<Producto> obtenerProductos(){

        List<Producto> productos = new ArrayList<>();

        productos.add(new Producto("Bolsa deporte fitness plegable", R.drawable.bolsa));
        productos.add(new Producto("Petate mochila fitness con cuerdas", R.drawable.bolsa_de_asas));
        productos.add(new Producto("Gorra fitness negra ADIDAS", R.drawable.gorra));
        productos.add(new Producto("Guantes Gimnasio Musculación", R.drawable.guantes));
        productos.add(new Producto("Toalla Gimnasio Fitness Domyos", R.drawable.toalla));

        return productos;
    }
}

