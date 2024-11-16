package com.example.reservasalud.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    ManagementDatabase managementDatabase;

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE usuario (cedula INT PRIMARY KEY, nombre TEXT, telefono TEXT)");
        db.execSQL("CREATE TABLE especialidad (id INTEGER PRIMARY KEY, nombre TEXT)");
        db.execSQL("CREATE TABLE medico (cedulaMed INT PRIMARY KEY, nombre TEXT, consultorio INT, id_especialidad INTEGER, FOREIGN KEY (id_especialidad) REFERENCES Especialidad (id))");
        db.execSQL("CREATE TABLE cita (idCita INTEGER PRIMARY KEY AUTOINCREMENT, fecha TEXT, hora TEXT, cedula INT, cedulaMed INT, FOREIGN KEY (cedula) REFERENCES Usuario (cedula), FOREIGN KEY (cedulaMed) REFERENCES Medico (cedulaMed))");


     }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Opcional: Lógica para manejar actualizaciones de esquema
    }
}
