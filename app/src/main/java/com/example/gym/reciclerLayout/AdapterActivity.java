package com.example.gym.reciclerLayout;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Clases.Actividad;
import com.example.gym.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterActivity extends ArrayAdapter {
    private List<String> activityID;
    private List<String> nombreAct;
    private List<String> hora_inicio;
    private List<String> hora_fin;
    private Activity context;
    private DocumentReference docRef;
    FirebaseFirestore db;


    public AdapterActivity(@NonNull Activity context, List<String> activityID, List<String> nombreAct, List<String> hora_inicio, List<String> hora_fin) {
        super(context, R.layout.row_actividades, nombreAct);
        db = FirebaseFirestore.getInstance();
        this.activityID = activityID;
        this.nombreAct = nombreAct;
        this.context = context;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null) {
            row = inflater.inflate(R.layout.row_actividades, null, true);
        }

        TextView tvNombre = (TextView) row.findViewById(R.id.activityName);
        TextView tvHoraInicio = (TextView) row.findViewById(R.id.activityHoraInicio);
        TextView tvHoraFin = (TextView) row.findViewById(R.id.activityHoraFin);


        tvNombre.setText(nombreAct.get(position));
        tvHoraInicio.setText(hora_inicio.get(position));
        tvHoraFin.setText(hora_fin.get(position));

        return row;
    }

    public void removeData(int position, String colectionPath, String nombreUsuario, String idCancion) {
        this.activityID.remove(position);
        this.nombreAct.remove(position);
        this.hora_inicio.remove(position);
        this.hora_fin.remove(position);

        docRef = db.collection(colectionPath).document(nombreUsuario);
        Map<String,Object> deleted = new HashMap<>();
        deleted.put(idCancion, FieldValue.delete());
        docRef.update(deleted);
        AdapterActivity.this.notifyDataSetChanged();
    }
}
