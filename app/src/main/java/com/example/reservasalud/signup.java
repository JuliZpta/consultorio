package com.example.reservasalud;

import android.content.ContentValues;
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

public class signup extends AppCompatActivity {
    private EditText etcedula, etusuario, ettelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        etcedula = (EditText) findViewById(R.id.etcedula);
        etusuario = (EditText) findViewById(R.id.etusuario);
        ettelefono = (EditText) findViewById(R.id.ettelefono);

    }

    public void crear (View view){

        Database admin = new Database(this, "BaseDato",null,1);
        SQLiteDatabase BaseDato = admin.getWritableDatabase();

        String cedula = etcedula.getText().toString();
        String usuario = etusuario.getText().toString();
        String telefono = ettelefono.getText().toString();

        if(!cedula.isEmpty() && !usuario.isEmpty() && !telefono.isEmpty()){

            Cursor cursor = BaseDato.rawQuery("SELECT cedula FROM usuario WHERE cedula = ?", new String[]{cedula});
            boolean existe = cursor.moveToFirst(); // Devuelve true si existe un registro con esa cédula

            if(!existe) {
                ContentValues registro = new ContentValues();

                registro.put("cedula", cedula);
                registro.put("nombre", usuario);
                registro.put("telefono", telefono);

                BaseDato.insert("usuario", null, registro);
                BaseDato.close();

                etcedula.setText("");
                etusuario.setText("");
                ettelefono.setText("");

                //ventana o mensaje en pantalla
                Toast.makeText(this, "Registro almacenado exitosamente", Toast.LENGTH_LONG).show();
            }
            else{

                Toast.makeText(this,"El usuario ya existe",Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "Hay campos vacios", Toast.LENGTH_LONG).show();
        }

    }

    public void volver (View view){

        Intent main =new Intent(this, MainActivity.class);
        startActivity(main);
    }


}