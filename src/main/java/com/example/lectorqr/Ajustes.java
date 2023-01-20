package com.example.lectorqr;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Ajustes extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ajustes);
        addPreferencesFromResource(R.xml.preferencias);

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
      String nombreBD = preferencias.getString("nombreBD", "basedatostrabajadores");

        SharedPreferences.Editor editorc = preferencias.edit();

        editorc.apply();

       // EditTextPreference nbd=findViewById(R.id.editprefBd);

    //   EditTextPreference campo=(EditTextPreference) findViewById(R.id.editpref);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        // Actualizar visibilidad de miniaturas
       String nombreBD = sharedPref.getString("nombreBD", "basedatostrabajadores");

        SharedPreferences.Editor editorc = sharedPref.edit();

        editorc.apply();


    }
}