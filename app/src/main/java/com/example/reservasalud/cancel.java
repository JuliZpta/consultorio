package com.example.reservasalud;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservasalud.db.Database;

public class cancel extends AppCompatActivity {

    private TextView tvfecha,tvhora, tvnom, tvconsultorio;
    private String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cancel);

        tvfecha = (TextView) findViewById(R.id.tvfecha);
        tvhora = (TextView) findViewById(R.id.tvhora);
        tvnom = (TextView)  findViewById(R.id.tvnom);
        tvconsultorio = (TextView) findViewById(R.id.tvconsultorio);

        id=getIntent().getStringExtra("id");

        Database admin = new Database(this,"BaseDato", null, 1);
        SQLiteDatabase BaseDato = admin.getWritableDatabase();

        Cursor cursor = BaseDato.rawQuery("SELECT cita.fecha, cita.hora, medico.nombre, medico.consultorio " + "FROM cita " + "JOIN medico ON cita.cedulaMed = medico.cedulaMed " + "WHERE cita.idCita = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            // Almacenar cada campo en una variable
            String fecha = cursor.getString(0);
            String hora = cursor.getString(1);
            String nombre = cursor.getString(2);
            int consultorio = cursor.getInt(3); // Consultorio del médico

            // Mostrar en consola o usar en otra parte del código
            tvfecha.setText("Fecha: " + fecha);
            tvhora.setText("Hora: " + hora);
            tvnom.setText("Nombre del medico:  " + nombre);
            tvconsultorio.setText("Consultorio del médico: " + consultorio);

        } else {
            Toast.makeText(this, "No se encontró la cita", Toast.LENGTH_SHORT).show();
        }


    }

    public void aceptar (View view){

        Database admin = new Database(this, "BaseDato",null,1);
        SQLiteDatabase BaseDato = admin.getWritableDatabase();

        int filasAfectadas = BaseDato.delete("cita", "idCita = ?", new String[]{String.valueOf(id)});

        if (filasAfectadas > 0) {

            Toast.makeText(this, "Cita eliminada correctamente", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "No se encontró la cita", Toast.LENGTH_SHORT).show();
        }

        Intent consulta = new Intent(this, getData.class);
        startActivity(consulta);
        BaseDato.close();
    }

    public void volver (View view){

        Intent volver = new Intent(this, getData.class);
        startActivity(volver);
    }
}