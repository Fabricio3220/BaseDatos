package com.uce.equipo.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gavilanes on 28/01/2015.
 */
public class BaseDatos extends SQLiteOpenHelper {
    public BaseDatos(Context context, String bnombre, SQLiteDatabase.CursorFactory factory,
                     int version) {
        super(context, bnombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table alumnos(ci integer primary key, nombre text, apellido text, celular integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists alumnos");
        db.execSQL("create table alumnos(ci integer primary key, nombre text, apellido text, celular integer)");
    }

}
