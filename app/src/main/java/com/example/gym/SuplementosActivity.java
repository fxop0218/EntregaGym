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

public class SuplementosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    /*
    private String urlBolsa = "https://www.decathlon.es/es/p/bolsa-deporte-fitness-plegable-para-ir-al-gym-30l/_/R-p-11233?mc=8200579&c=NEGRO";
    private String urlGuantes = "https://www.decathlon.es/es/p/guantes-gimansio-musculacion-100-negro/_/R-p-326377?mc=8595161&c=NEGRO";
    private String urlBolsa_Asas = "https://www.decathlon.es/es/p/petate-mochila-fitness-con-cuerdas-15-l/_/R-p-324636?mc=8584676&c=NEGRO";
    private String urlToalla = "https://www.decathlon.es/es/p/toalla-gimnasio-fitness-domyos-90x50cm/_/R-p-170251?mc=8379823&c=NEGRO";
    private String urlGorra = "https://www.decathlon.es/es/p/gorra-fitness-negra-adidas/_/R-p-X8623877?mc=8623877";
    */

    private String urlDeca = "https://www.decathlon.es/es/browse/c0-salud-y-bienestar/c1-alimentacion-saludable/_/N-1mfwa2e";
    private ImageView ivDeca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suplementos);

        recyclerView = findViewById(R.id.recyclerProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewAdapter(obtenerProductos());
        recyclerView.setAdapter(recyclerViewAdapter);

        ivDeca = findViewById(R.id.ivDecaSup);
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

        productos.add(new Producto("BARRITA DEPORTE PASTA DE DÁTILES CACAO BIO Y SIN GLUTEN", R.drawable.barritas_deportivas));
        productos.add(new Producto("Proteína Whey 23g Chocolate Foodspring", R.drawable.chocolate));
        productos.add(new Producto("Agua mineral natural Limón 500 ml", R.drawable.agua_limon));
        productos.add(new Producto("Harina Avena Brownie", R.drawable.harina_avena));
        productos.add(new Producto("Sirope de chocolate", R.drawable.sirope));

        return productos;
    }
}