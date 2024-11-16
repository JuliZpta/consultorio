package com.example.reservasalud;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservasalud.db.Database;

import java.util.ArrayList;

public class assign extends AppCompatActivity {

    private RadioButton rbgeneral, rbpediatra, rbodontologia, rb1, rb2, rb3;
    private String id, fecha, hora, cedula, nombremed, especialidad, cedulamed = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assign);

        rbgeneral = (RadioButton) findViewById(R.id.rbgeneral);
        rbodontologia = (RadioButton) findViewById(R.id.rbodontologia);
        rbpediatra = (RadioButton) findViewById(R.id.rbpediatra);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);

        fecha = getIntent().getStringExtra("fecha");
        hora = getIntent().getStringExtra("hora");
        cedula = getIntent().getStringExtra("cedula");

    }

    public void buscarMedico(View view) {

        Database admin = new Database(this, "BaseDato", null, 1);
        SQLiteDatabase BaseDato = admin.getWritableDatabase();
        ArrayList<String> nombresMedicos = new ArrayList<>();

        if (rbgeneral.isChecked()) {
            id = "1";
            especialidad = rbgeneral.getText().toString();

        } else if (rbodontologia.isChecked()) {
            id = "2";
            especialidad = rbodontologia.getText().toString();

        } else if (rbpediatra.isChecked()) {
            id = "3";
            especialidad = rbpediatra.getText().toString();
        }

        Cursor cursor = null;
        try {
            cursor = BaseDato.rawQuery("SELECT nombre FROM medico WHERE id_especialidad = ?", new String[]{id});
            if (cursor.moveToFirst()) {
                do {
                    String nombre = cursor.getString(0);
                    nombresMedicos.add(nombre);
                } while (cursor.moveToNext());

                Log.d("med: ", nombresMedicos.toString());
                rb1.setText(nombresMedicos.get(0));
                rb2.setText(nombresMedicos.get(1));
                rb3.setText(nombresMedicos.get(2));
            } else {
                Toast.makeText(this, "No hay médicos disponibles", Toast.LENGTH_LONG).show();
            }

        } finally {
            if (cursor != null) cursor.close();
            if (BaseDato.isOpen()) BaseDato.close();
        }
    }

    public void agendar(View view) {
        Database admin = new Database(this, "BaseDato", null, 1);
        SQLiteDatabase BaseDato = admin.getWritableDatabase();

        if (rb1.isChecked()) {
            nombremed = rb1.getText().toString();
        } else if (rb2.isChecked()) {
            nombremed = rb2.getText().toString();
        } else if (rb3.isChecked()) {
            nombremed = rb3.getText().toString();
        }

        Cursor cursor = BaseDato.rawQuery("SELECT cedulaMed FROM medico WHERE nombre = ?", new String[]{nombremed});
        if (cursor.moveToFirst()) {
            cedulamed = cursor.getString(0);
        }
        cursor.close();


        ContentValues registro = new ContentValues();

        if (!fecha.isEmpty() && !hora.isEmpty() && !cedula.isEmpty() && !cedulamed.isEmpty()) {

            registro.put("fecha", fecha);
            registro.put("hora", hora);
            registro.put("cedula", cedula);
            registro.put("cedulaMed", cedulamed);

            BaseDato.insert("cita", null, registro);
            BaseDato.close();

            Toast.makeText(this, "Se asigno correctamente la cita", Toast.LENGTH_LONG).show();


            Intent info = new Intent(this, appointmentInformation.class);
            info.putExtra("fecha", fecha);
            info.putExtra("hora", hora);
            info.putExtra("cedulapaciente", cedula);
            info.putExtra("cedulamedico", cedulamed);
            info.putExtra("nombreMed", nombremed);
            info.putExtra("especialidad", especialidad);
            startActivity(info);

        }

    }

    public void volver(View view) {
        Intent i = new Intent(this, home.class);
        startActivity(i);
    }
}