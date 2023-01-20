






     package com.example.lectorqr;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

     public class MainActivity extends AppCompatActivity  implements LocationListener {
         private final static String TAG = MainActivity.class.getSimpleName();
         private static final int PERMISSION_REQUEST = 255;
         public static final int FLAG_GRANT_READ_URI_PERMISSION = 0x00000001;
         protected LocationManager locationManager;
         private TextView textoCodigoQRnombre, textoCodigoQRapellido, textoCodigoQRcomunidad, textoCodigoQRDireccion,textoEdificio,textoentrada;
         private Button boton_QR, boton_entrada, boton_salida,botonP,botonC,botonE,botonS;
         private ArrayList<trabajador> listado;
         private String nombreBD;
         private ListView lista;
         private final String pathPDF = "/data/data/com.example.lectorqr/files/PDF/";
         private String archivoPDF;
         private File PDFFile;
         SQLiteDatabase bd;
         SharedPreferences preferencias;
         @SuppressLint("MissingPermission")
         private Switch switch2,switch1;

         @Override
         protected void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_main);

             StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
             preferencias = PreferenceManager.getDefaultSharedPreferences(this);
             nombreBD = preferencias.getString("nombreBD", "basedatostrabajadores");
             textoCodigoQRnombre = (TextView) findViewById(R.id.textQRnombre);
             textoCodigoQRapellido = (TextView) findViewById(R.id.textQRapellido);
             textoCodigoQRcomunidad = (TextView) findViewById(R.id.textQRcomunidad);
             textoCodigoQRDireccion = (TextView) findViewById(R.id.txt_direccion);
             textoEdificio= (TextView) findViewById(R.id.txt_edificio);
             textoentrada= (TextView) findViewById(R.id.txt_entrada);

             setTitle("Aplicacion eurobrik");

             listado = new ArrayList<>();
             archivoPDF = preferencias.getString("nombrepdf", "pdfeurobrik");
             PDFFile = new File(pathPDF + archivoPDF + ".pdf");
             boton_QR = findViewById(R.id.btn_QR);
             boton_entrada = findViewById(R.id.btn_entrada);
             boton_salida = findViewById(R.id.button);
             botonC= findViewById(R.id.btn_edificio);
             botonP=findViewById(R.id.btn_portal);
             botonE=findViewById(R.id.btn_emtrada);
             botonS=findViewById(R.id.btn_salida);

             //switch1 = findViewById(R.id.switch1);
            // switch2 = findViewById(R.id.switch2);


             if (Build.VERSION.SDK_INT >= 23) {
                 String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                 if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
                     ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST);
                 } else {

                     checkFile(PDFFile);
                 }
             } else {

                 checkFile(PDFFile);
             }


          /*
             switch1.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (switch1.isChecked()) {
                         switch1.setText("Entrada");
                     } else {
                         switch1.setText("Salida");
                     }
                 }
             });


             switch2.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (switch2.isChecked()) {
                         switch2.setText("Edificio");
                     } else {
                         switch2.setText("portal");
                     }
                 }
             });
*/

             locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
             if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                 ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
             } else {
                 locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1000, (LocationListener) this);
                 locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, this);

             }

            botonP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textoEdificio.setText("PORTAL");
                }
            });


             botonC.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     textoEdificio.setText("COMPLETO");
                 }
             });



             botonE.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     textoentrada.setText("ENTRADA");
                 }
             });



             botonS.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     textoentrada.setText("SALIDA");
                 }
             });


             boton_QR.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     escanearQR();

                 }
             });


             boton_entrada.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     consultarentradas(null);
                     alta(null);

                     crearDisplayPdf(listado);


                 }


             });
             //este es evento para compartir pdf
             boton_salida.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {


                     shareFile(PDFFile);

                 }
             });



         }

         public boolean onCreateOptionsMenu(Menu menu) {
             getMenuInflater().inflate(R.menu.main, menu);
             return true;
         }

         public boolean onOptionsItemSelected(MenuItem item) {
             switch (item.getItemId()) {
                 case R.id.ajustes:
                     Intent intente = new Intent(this, Ajustes.class);
                     startActivity(intente);
                     return true;
                 case R.id.salir:
                     finish();
                     return true;
                 default:
                     return super.onOptionsItemSelected(item);
             }
         }

             @Override
         public void onLocationChanged(@NonNull Location location) {

             Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
             try {
                 List<Address> direccion = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                 textoCodigoQRDireccion.setText(direccion.get(0).getAddressLine(0));

             } catch (IOException e) {
                 e.printStackTrace();


             }
         }


         public void onProviderDisabled(String provider) {
             Log.d("Latitude", "disable");
         }

         public void onProviderEnabled(String provider) {
             Log.d("Latitude", "enable");

         }

         public void onStatusChanged(String provider, int status, Bundle extras) {
             Log.d("Latitude", "status");
         }

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


         @Override
         protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
             super.onActivityResult(requestCode, resultCode, data);

             IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

             if (result != null) {
                 if (result.getContents() == null) {
                     Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
                 } else {
                     String cadena = result.getContents();
                     String[] bar = cadena.split("\n");
                     textoCodigoQRnombre.setText(bar[0]);
                     textoCodigoQRapellido.setText(bar[1]);
                     textoCodigoQRcomunidad.setText(bar[2]);
             


                 }
             } else {
                 super.onActivityResult(requestCode, resultCode, data);
                 //  String datos = result.getContents();
             }
             // txt.setText(datos);


         }

         private void escanearQR() {
             IntentIntegrator integrador = new IntentIntegrator(this);
             integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
             integrador.setPrompt("Escanear Código");
             integrador.setCameraId(0);
             integrador.setBeepEnabled(true);
             integrador.setBarcodeImageEnabled(true);
             integrador.initiateScan();
         }


         public void alta(View v) {
             // listado = new ArrayList<>();
             AdminBD admin = new AdminBD(MainActivity.this, nombreBD, null, 1);
             SQLiteDatabase bd;

             try {
                 bd = admin.getWritableDatabase();
                 if (bd != null) {

                     String nombre = textoCodigoQRnombre.getText().toString();
                     String apellido = textoCodigoQRapellido.getText().toString();
                     String comunidad = textoCodigoQRcomunidad.getText().toString();
                     String direccion = textoCodigoQRDireccion.getText().toString();
                     String trabajo = textoEdificio.getText().toString();
                     String fichaje=textoentrada.getText().toString();
                     SimpleDateFormat simpleFechaFormat = new SimpleDateFormat("dd/MM/yyyy");
                     String fecha = simpleFechaFormat.format(new Date());
                     SimpleDateFormat simpleHoraFormat = new SimpleDateFormat("HH:mm:ss");
                     String hora = simpleHoraFormat.format(new Date());
                     ContentValues registro = new ContentValues();
                     registro.put("nombre", nombre);
                     registro.put("apellidos", apellido);
                     registro.put("comunidad", comunidad);
                     registro.put("localizacion", direccion);
                     registro.put("trabajo", trabajo);
                     registro.put("fichaje",fichaje);
                     registro.put("fecha", fecha);
                     registro.put("hora", hora);
                     listado.add(new trabajador(nombre, apellido, comunidad, direccion, trabajo,fichaje, fecha, hora));
                     bd.insert("entradas", null, registro);

                     // adaptador_trabajador adapter = new adaptador_trabajador(this, listado);

                     // lista.setAdapter(adapter);
                     Toast.makeText(MainActivity.this, "Se grabaron los datos de los trabajadores", Toast.LENGTH_SHORT).show();


                 } else
                     Toast.makeText(this, "Base de datos no creada", Toast.LENGTH_SHORT).show();
             } catch (SQLiteConstraintException ex) {
                 bd = admin.getReadableDatabase();
                 Toast.makeText(this, "No se puede escribir la Base de Datos", Toast.LENGTH_SHORT).show();

             }


             bd.close();


         }






         public void consultarentradas (View view) {
             listado = new ArrayList<>();
             AdminBD admin = new AdminBD(this, nombreBD, null, 1);
             SQLiteDatabase bd = admin.getReadableDatabase();
             Cursor fila = bd.rawQuery("select nombre,apellidos,comunidad,localizacion,trabajo,fichaje,fecha,hora from entradas ", null);
             if ((fila != null) && (checkDataBase("/data/data/com.example.lectorqr/databases/" + nombreBD))) {
                 if (fila.getCount() != 0) {
                     fila.moveToFirst();
                     do{
                         String nombre = fila.getString(fila.getColumnIndexOrThrow("nombre"));
                         String apellido = fila.getString(fila.getColumnIndexOrThrow("apellidos"));
                         String comunidad= fila.getString(fila.getColumnIndexOrThrow("comunidad"));
                         String localizacion= fila.getString(fila.getColumnIndexOrThrow("localizacion"));
                         String trabajo= fila.getString(fila.getColumnIndexOrThrow("trabajo"));
                         String fichaje= fila.getString(fila.getColumnIndexOrThrow("fichaje"));
                         String fecha = fila.getString(fila.getColumnIndexOrThrow("fecha"));
                         String hora = fila.getString(fila.getColumnIndexOrThrow("hora"));
                         listado.add(new trabajador(nombre,apellido,comunidad,localizacion,trabajo,fichaje,fecha,hora));
                     }while(fila.moveToNext());
                     adaptador_trabajador adapater= new adaptador_trabajador(this,listado);

                 }else{
                     Toast.makeText(this, "La Base de datos está vacía. Primero cree los clientes", Toast.LENGTH_SHORT).show();

                 }
                 fila.close();

                 }
             bd.close();
             }


         private void checkFile(File Archivo) {

//se comprueba que exista el documento y si no esta creado se vuelve a crear
             File documentsPath = new File(pathPDF + "/");

             //si eldocumento ya existe se mete en la carpeta
             if (!documentsPath.exists()) {

                 documentsPath.mkdir();
             } else {

             }
             File file = new File(Archivo.toString());


         }

         private void shareFile(File file) {


             Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);

             Intent intent = ShareCompat.IntentBuilder.from(this)
                     .setType("application/pdf")
                     .setStream(uri)
                     .setChooserTitle("Choose bar")
                     .createChooserIntent()
                     .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
             startActivity(intent);
         }


         private boolean checkDataBase(String Database_path) {
             SQLiteDatabase checkDB = null;
             try {
                 checkDB = SQLiteDatabase.openDatabase(Database_path, null, SQLiteDatabase.OPEN_READONLY);
                 checkDB.close();
             } catch (SQLiteException e) {
                 Toast.makeText(this, "Error. No existe la base de datos", Toast.LENGTH_SHORT).show();
             }
             return checkDB != null;
         }


         public void crearDisplayPdf(ArrayList<trabajador> list) {
             if (list != null) {

                 Document doc = new Document();
                 File dir = new File(pathPDF);
                 try {
                     if (dir.exists())
                         dir = new File(pathPDF);
                     if (dir != null) {
                         if (!dir.mkdirs()) {
                             if (!dir.exists()) {
                                 dir.mkdirs();
                             }
                         }
                     }

                     File file = new File(this.getFilesDir(), "PDF/" + archivoPDF + ".pdf");


                     FileOutputStream fOut = new FileOutputStream(file);
                     PdfWriter.getInstance(doc, fOut);
                     //open the document
                     doc.open();
                     Paragraph p = new Paragraph("LISTADO FICHAJES" + "\n");
                     Font paraFont = new Font(Font.FontFamily.COURIER);
                     p.setAlignment(Paragraph.ALIGN_CENTER);
                     doc.add(p);
                     for (trabajador trabajador : list) {
                         p = new Paragraph("\n"+"\n" + trabajador.getNombre()
                                 + "\n" + trabajador.getApellidos() + "\n" + trabajador.getComunidad() +
                                 "\n" + trabajador.getLocalizacion() + "\n" + trabajador.getTrabajo() + "\n" + trabajador.getFichaje() + "\n" + trabajador.getFecha() + "\n" + trabajador.getHora());
                         paraFont = new Font(Font.FontFamily.COURIER);
                         p.setAlignment(Paragraph.ALIGN_LEFT);
                         p.setFont(paraFont);
                         //add paragraph to document
                         // Toast.makeText(this, "Archivo PDF creado", Toast.LENGTH_SHORT).show();
                         doc.add(p);
                     }
                     Toast.makeText(this, "Archivo PDF creado", Toast.LENGTH_SHORT).show();
                 } catch (IOException | DocumentException de) {
                     Log.e("PDFCreator", "DocumentException:" + de);
                 } finally {
                     doc.close();
                 }
             } else {
                 if (checkDataBase("/data/data/com.example.lectorqr/databases/" + nombreBD)) {
                     //aqui es onde da el fallo pq llama al metodo dentro del mismo metodo
                     crearDisplayPdf(listado);

                 } else {
                     Toast.makeText(this, "NO EXISTE BASE DE DATOS", Toast.LENGTH_SHORT).show();
                 }

             }
         }




    }










