package com.example.reservasalud;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.reservasalud.db.Database;


public class getData extends AppCompatActivity {

    private TableLayout tlcitas;
    private EditText etid;
    private String id ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_data);

        tlcitas = (TableLayout) findViewById(R.id.tlcitas);
        etid = (EditText)findViewById(R.id.etid);

        String cedula= getIntent().getStringExtra("cedula");

        Database admin = new Database(this, "BaseDato", null, 1);
        SQLiteDatabase BaseDato = admin.getWritableDatabase();

        Cursor cursor = BaseDato.rawQuery("SELECT cita.idCita, cita.fecha, cita.hora, cita.cedulaMed, medico.nombre " + "FROM cita, medico " + "WHERE cita.cedulaMed = medico.cedulaMed and cita.cedula = ?",new String[]{cedula});

        Typeface typeface = ResourcesCompat.getFont(this, R.font.alata);

        if (cursor.moveToFirst()) {
            do {
                TableRow fila = new TableRow(this);

                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    TextView columna= new TextView(this);
                    columna.setText(cursor.getString(i));

                    columna.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    columna.setTextColor(getResources().getColor(android.R.color.system_accent1_500, null));
                    columna.setTypeface(typeface);
                    columna.setPadding(25, 0, 25, 0);

                    fila.addView(columna);
                }

                tlcitas.addView(fila);
            } while (cursor.moveToNext());
        }

        cursor.close();
        BaseDato.close();

    }

    public void cancelar (View view) {

        String id = etid.getText().toString();
        if(!id.isEmpty()) {
            Intent cancelar = new Intent(this, cancel.class);
            cancelar.putExtra("id", id);
            startActivity(cancelar);
        }else{
            Toast.makeText(this,"El campo id esta vacio", Toast.LENGTH_LONG).show();
        }

    }

    public void reagendar (View view)
    {

    }
    public void volver ( View view){

        Intent inicio = new Intent(this, home.class);
        startActivity(inicio);

    }
}