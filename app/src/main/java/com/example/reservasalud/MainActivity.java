package com.example.reservasalud;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservasalud.db.Database;
import com.example.reservasalud.db.ManagementDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText etcedula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etcedula = (EditText) findViewById(R.id.etcedula);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences prede= getPreferences(MODE_PRIVATE);
        etcedula.setText(prede.getString("cedula",""));


        Database database = new Database(this, "BaseDato", null, 1);
        ManagementDatabase managementDatabase = new ManagementDatabase(database);
        managementDatabase.insertarDatosEjemplo();

    }

    public void iniciosesion (View view) {

        Database admin = new Database(this, "BaseDato", null, 1);
        SQLiteDatabase BaseDato = admin.getReadableDatabase();
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();

        String cedula = etcedula.getText().toString();
        editor.putString("cedula",cedula);
        editor.commit();

        Cursor cursor = BaseDato.rawQuery("SELECT cedula FROM usuario WHERE cedula = ?", new String[]{cedula});
        boolean existe = cursor.moveToFirst();

        if(existe){

            Intent inicio = new Intent(this, home.class);
            inicio.putExtra("cedula",cedula);
            startActivity(inicio);

        }
        else{
            Toast.makeText(this,"Cedula incorrecta o no existe", Toast.LENGTH_LONG).show();
        }
    }

    public void crearcuenta (View view){
        Intent crear =new Intent(this, signup.class);
        startActivity(crear);
    }
}