package com.example.gym.reciclerLayout;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gym.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterActivityOwner extends ArrayAdapter {
    private List<String> activityID;
    private List<String> nombreAct;
    private List<String> hora_inicio;
    private List<String> hora_fin;
    private List<String> dia;
    private Activity context;
    private DocumentReference docRef;
    FirebaseFirestore db;


    public AdapterActivityOwner(@NonNull Activity context, List<String> activityID, List<String> nombreAct, List<String> hora_inicio, List<String> hora_fin, List<String> dia) {
        super(context, R.layout.row_actividades, nombreAct);
        db = FirebaseFirestore.getInstance();
        this.activityID = activityID;
        this.nombreAct = nombreAct;
        this.context = context;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.dia = dia;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null) {
            row = inflater.inflate(R.layout.row_actividades_owner, null, true);
        }

        TextView tvNombre = (TextView) row.findViewById(R.id.activityName);
        TextView tvHoraInicio = (TextView) row.findViewById(R.id.activityHoraInicio);
        TextView tvHoraFin = (TextView) row.findViewById(R.id.activityHoraFin);
        TextView tvDia = (TextView) row.findViewById(R.id.tvDia);


        tvNombre.setText(context.getString(R.string.nombre) + ": " + nombreAct.get(position));
        tvHoraInicio.setText(hora_inicio.get(position));
        tvHoraFin.setText("to \t" + hora_fin.get(position));
        tvDia.setText(context.getString(R.string.dia) + ": " + dia.get(position));

        return row;
    }

    public void removeData(int position, String colectionPath, String nombreUsuario, String idCancion) {
        this.activityID.remove(position);
        this.nombreAct.remove(position);
        this.hora_inicio.remove(position);
        this.hora_fin.remove(position);
        this.dia.remove(position);

        docRef = db.collection(colectionPath).document(nombreUsuario);
        Map<String,Object> deleted = new HashMap<>();
        deleted.put(idCancion, FieldValue.delete());
        docRef.update(deleted);
        AdapterActivityOwner.this.notifyDataSetChanged();
    }
}
