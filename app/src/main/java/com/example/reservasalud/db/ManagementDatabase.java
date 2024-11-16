package com.example.reservasalud.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ManagementDatabase {

    private Database database;
    private SQLiteDatabase db;

    public ManagementDatabase(Database database) {
        this.database = database;
        this.db = database.getWritableDatabase();

        if (db == null || !db.isOpen()) {
            throw new IllegalStateException("La base de datos no está inicializada o no está abierta.");
        }

    }

    public void insertarEspecialidad(int id, String nombre) {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("nombre", nombre);
        db.insert("especialidad", null, values);
    }


    public void insertarMedico(int cedulaMed, String nombre, int consultorio, int idEspecialidad) {
        ContentValues values = new ContentValues();
        values.put("cedulaMed", cedulaMed);
        values.put("nombre", nombre);
        values.put("consultorio", consultorio);
        values.put("id_especialidad", idEspecialidad);
        db.insert("Medico", null, values);
    }

    public void insertarCita(String fecha, String hora, int cedula, int cedulaMed) {
        ContentValues values = new ContentValues();
        values.put("fecha", fecha);
        values.put("hora", hora);
        values.put("cedula", cedula);
        values.put("cedulaMed", cedulaMed);
        db.insert("Cita", null, values);
    }

    public void getMedicos() {
        Cursor cursor = db.rawQuery("SELECT * FROM Medico", null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("Medico", "Cedula: " + cursor.getInt(0) + " Nombre: " + cursor.getString(1) + " Consultorio: " + cursor.getInt(2) + " Especialidad: " + cursor.getInt(3));
            } while (cursor.moveToNext());
        }
    }


    public void insertarUsuario(int cedula, String nombre, String telefono) {
        ContentValues values = new ContentValues();
        values.put("cedula", cedula);
        values.put("nombre", nombre);
        values.put("telefono", telefono);
        db.insert("Usuario", null, values);
    }

    public void insertarDatosEjemplo() {
        insertarEspecialidad(1, "General");
        insertarEspecialidad(2, "Odontología");
        insertarEspecialidad(3, "Pediatra");

        insertarMedico(1, "Dr. Juan Pérez", 101, 1);
        insertarMedico(2, "Dr. Luis García", 102, 1);
        insertarMedico(3, "Dra. Marta Ríos", 103, 1);
        insertarMedico(4, "Dr. Carlos Mendoza", 104, 2);
        insertarMedico(5, "Dra. Ana López", 105, 2);
        insertarMedico(6, "Dr. Pedro Jiménez", 106, 2);
        insertarMedico(7, "Dra. Laura Vargas", 107, 3);
        insertarMedico(8, "Dr. Mario Rivera", 108, 3);
        insertarMedico(9, "Dra. Silvia Cruz", 109, 3);
    }

    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

}
