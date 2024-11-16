package com.example.reservasalud;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservasalud.db.Database;

public class appointmentInformation extends AppCompatActivity {

    private TextView tvfecha, tvhora, tvcedpaciente,tvnompaciente, tvespecialidad, tvnommedico, tvconsultorio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appointment_information);
        tvfecha = (TextView) findViewById(R.id.tvfecha);
        tvhora = (TextView) findViewById(R.id.tvhora);
        tvcedpaciente = (TextView) findViewById(R.id.tvcedpaciente);
        tvnompaciente = (TextView) findViewById(R.id.tvnompaciente);
        tvespecialidad = (TextView) findViewById(R.id.tvespecialidad);
        tvnommedico = (TextView) findViewById(R.id.tvnommedico);
        tvconsultorio = (TextView)findViewById(R.id.tvconsultorio);

        Database admin = new Database(this, "BaseDato", null,1);
        SQLiteDatabase BaseDato = admin.getWritableDatabase();

        String fecha = getIntent().getStringExtra("fecha");
        String hora = getIntent().getStringExtra("hora");
        String cedulapaciente = getIntent().getStringExtra("cedulapaciente");
        String cedulamedico = getIntent().getStringExtra("cedulamedico");
        String nombremed = getIntent().getStringExtra("nombreMed");
        String especialidad = getIntent().getStringExtra("especialidad");
        String nombrepaciente= "";

        Cursor ced = BaseDato.rawQuery("SELECT nombre FROM usuario WHERE cedula = ?", new String[]{cedulapaciente});

        if(ced.moveToFirst()){
            nombrepaciente = ced.getString(0);
        }
        ced.close();

        Cursor consultorio = BaseDato.rawQuery("SELECT consultorio FROM medico WHERE cedulaMed = ?", new String[]{cedulamedico});
        String consult = "";
        if(consultorio.moveToFirst()){
            consult = consultorio.getString(0);
        }
        consultorio.close();

        tvfecha.setText("Fecha: "+fecha);
        tvhora.setText("Hora: "+hora);
        tvcedpaciente.setText("Cedula paciente: "+cedulapaciente);
        tvnompaciente.setText("Nombre del paciente: "+nombrepaciente);
        tvespecialidad.setText("Especialidad: "+especialidad);
        tvnommedico.setText("Nombre del medico: "+nombremed);
        tvconsultorio.setText("Consultorio: "+consult);
    }

    public void volver (View view){
        Intent inicio = new Intent(this, home.class);
        startActivity(inicio);
    }
}