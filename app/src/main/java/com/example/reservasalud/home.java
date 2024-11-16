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

public class home extends AppCompatActivity {

    private TextView tvbienvenido;
    private String nombreUser = "", cedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        tvbienvenido = (TextView) findViewById(R.id.tvbienvenido);

        Database admin = new Database(this, "BaseDato",  null, 1);
        SQLiteDatabase BaseDato = admin.getWritableDatabase();
        cedula =getIntent().getStringExtra("cedula");
        Cursor cursor = BaseDato.rawQuery("SELECT nombre FROM usuario WHERE cedula = ?", new String[]{cedula});

        if (cursor.moveToFirst()) {
            nombreUser = cursor.getString(0);
            tvbienvenido.setText("Bienvenido "+nombreUser);
        }

    }

    public  void asignar (View view){

        Intent asigna = new Intent(this, appointment.class);
        asigna.putExtra("cedula",cedula);
        startActivity(asigna);

    }

    public void consultar (View view){

        Intent consulta = new Intent(this, getData.class);
        consulta.putExtra("cedula",cedula);
        startActivity(consulta);
    }
}