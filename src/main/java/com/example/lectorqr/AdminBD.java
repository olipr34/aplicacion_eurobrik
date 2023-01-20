package com.example.lectorqr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

public class AdminBD extends SQLiteOpenHelper{
    public AdminBD(@Nullable Context context, @Nullable String name,
                   @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }





    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }


//este metodo es para crear las tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table entradas(nombre text,apellidos text,comunidad text,localizacion text,trabajo text,fichaje text,fecha text,hora text )");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {

    }





}
