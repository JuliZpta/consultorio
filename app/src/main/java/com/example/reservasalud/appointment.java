package com.example.reservasalud;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservasalud.db.Database;

public class appointment extends AppCompatActivity {
    private EditText etfecha, ethora;
    private String fechauser = "", horauser = "",cedula,id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appointment);

        etfecha = (EditText) findViewById(R.id.etfecha);
        ethora = (EditText) findViewById(R.id.ethora);

        cedula = getIntent().getStringExtra("cedula");


    }

    public void disponibilidad (View view) {

        Database admin = new Database(this, "BaseDato", null, 1);
        SQLiteDatabase BaseDato = admin.getReadableDatabase();

        id=getIntent().getStringExtra("id");
        fechauser = etfecha.getText().toString();
        horauser = ethora.getText().toString();

        if(!fechauser.isEmpty() && !horauser.isEmpty() ) {

            Cursor fechas = BaseDato.rawQuery("SELECT fecha, hora FROM cita WHERE fecha = ? AND hora=?", new String[]{fechauser, horauser});
            boolean ExistFecha = fechas.moveToFirst();

            if (ExistFecha) {

                Toast.makeText(this, "Ya existe una cita para esa fecha y hora", Toast.LENGTH_LONG).show();

            } else {


                    Intent info = new Intent(this, assign.class);
                    info.putExtra("fecha", fechauser);
                    info.putExtra("hora", horauser);
                    info.putExtra("cedula", cedula);
                    startActivity(info);
            }
        }
        else{

            Toast.makeText(this,"Hay campos vacios", Toast.LENGTH_LONG).show();
        }
    }



    public void volver(View view){

        Intent inicio = new Intent(this, home.class);
        startActivity(inicio);
    }
}