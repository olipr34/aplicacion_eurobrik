package com.example.lectorqr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;

import java.io.File;


public class VisorPdf extends AppCompatActivity {
   // private WebView webView;
   private final static String TAG  = MainActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST = 255;
    public static final int FLAG_GRANT_READ_URI_PERMISSION = 0x00000001;
    private final String pathPDF = "/data/data/com.example.lectorqr/files/PDF/";
    private String archivoPDF;
    private File PDFFile;
    private SharedPreferences preferencias;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferencias = PreferenceManager.getDefaultSharedPreferences(this);
       archivoPDF = preferencias.getString("nombrePDF", "pdfolalla");
        PDFFile = new File(pathPDF+archivoPDF+".pdf");
        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST );
            } else {

                checkFile(PDFFile);
            }
        } else {

            checkFile(PDFFile);
        }
    }
//este es metodo para crear los directorios para meter el pdf

    private void checkFile(File Archivo){

//se comprueba que exista el documento y si no esta creado se vuelve a crear
        File documentsPath = new File(pathPDF +"/");

       //si eldocumento ya existe se mete en la carpeta
        if (!documentsPath.exists()) {

            documentsPath.mkdir();
        }else{

        }
        File file = new File(Archivo.toString());

       if(file.exists()) {

            shareFile(file);
        }else{



        }
    }
//este es el metodo para compartir el pdf
    private void shareFile(File file){


        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);

        Intent intent = ShareCompat.IntentBuilder.from(this)
                .setType("application/pdf")
                .setStream(uri)
                .setChooserTitle("Choose bar")
                .createChooserIntent()
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }


//y estos son los metodos para comprobar los permisos
    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    checkFile(PDFFile);
                } else {
                    Toast.makeText(getApplicationContext(), "La aplicación no tiene permiso problemas con el alamacenamiento", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}

