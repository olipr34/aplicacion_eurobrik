package com.example.lectorqr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFconversor extends AppCompatActivity {

    private Button b;
    private PdfPCell cell;
    private String textAnswer;
    private Image bgImage;
    ListView list;
    private String path;
    private File dir;
    private File file;

    //background color


    private final static String NOMBRE_DIRECTORIO = "MiPdf";
    private final static String NOMBRE_DOCUMENTO = "prueba.pdf";
    private final static String ETIQUETA_ERROR = "ERROR";
    Button btnGenerar;
    private final String pathPDF="/data/data/com.example.lectorQR/files";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

//        // Permisos.
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
           ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1000);
       } else {
       }
      // Generaremos el documento al hacer click sobre el boton.



  }
//
//
  public void generarPdf() {
//
//        // Creamos el documento.
      Document documento = new Document();
//
       try {
//
          File f = crearFichero(NOMBRE_DOCUMENTO);
//
           // Creamos el flujo de datos de salida para el fichero donde
//            // guardaremos el pdf.
           FileOutputStream ficheroPdf = new FileOutputStream(
                  f.getAbsolutePath());
//            // Asociamos el flujo que acabamos de crear al documento.
           PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);
//
////
//
//            // Abrimos el documento.
          documento.open();
//
           // Añadimos un titulo con la fuente por defecto.
           documento.add(new Paragraph("Titulo 1"));
//

//
//            // Insertamos una imagen que se encuentra en los recursos de la
//            // aplicacion.
           Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.ic_launcher_background);
           ByteArrayOutputStream stream = new ByteArrayOutputStream();
          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
          Image imagen = Image.getInstance(stream.toByteArray());
          documento.add(imagen);
//
           // Insertamos una tabla.
           PdfPTable tabla = new PdfPTable(5);
           for (int i = 0; i < 15; i++) {
              tabla.addCell("Celda " + i);
           }
           documento.add(tabla);
//
//
//
       } catch (DocumentException e) {
//
            Log.e(ETIQUETA_ERROR, e.getMessage());

       } catch (IOException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());
//
       } finally {
           // Cerramos el documento.
            documento.close();
       }
   }
//
//
   public static File crearFichero(String nombreFichero) throws IOException {
       File ruta = getRuta();
       File fichero = null;
       if (ruta != null)
           fichero = new File(ruta, nombreFichero);
      return fichero;
   }
//
//    /**
//     * Obtenemos la ruta donde vamos a almacenar el fichero.
//     *
//     * @return
//     */
   public static File getRuta() {
//
//        // El fichero sera almacenado en un directorio dentro del directorio
//        // Descargas
       File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
           ruta = new File(
                   Environment
                           .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                  NOMBRE_DIRECTORIO);
//
           if (ruta != null) {
              if (!ruta.mkdirs()) {
                  if (!ruta.exists()) {
                       return null;
                   }
               }
          }
        } else {
       }
//
       return ruta;
   }
//}
//
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_pdfconversor);
////
////        b = (Button) findViewById(R.id.button1);
////      //  Bundle bund=new Bundle();
////       // list
////        list = (ListView) findViewById(R.id.list);
////
////        //creating new file path
////        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
////        dir = new File(path);
////        if (!dir.exists()) {
////            dir.mkdirs();
////        }
////        b.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                // TODO Auto-generated method stub
////                try {
////                    createPDF();
////                } catch (FileNotFoundException e) {
////                    e.printStackTrace();
////                } catch (DocumentException e) {
////                    e.printStackTrace();
////                }
////            }
////        });
////    }
////
////    @Override
////    protected void onResume() {
////        super.onResume();
//////getting files from directory and display in listview
////        try {
////
////            ArrayList<String> FilesInFolder = GetFiles( Environment.getExternalStorageDirectory().getAbsolutePath() + "/");//"/sdcard/Dani/PDFS");
////            if (FilesInFolder.size() != 0)
////                list.setAdapter(new ArrayAdapter<String>(this,
////                        android.R.layout.simple_list_item_1, FilesInFolder));
////
////            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
////                    // Clicking on items
////                }
////            });
////        } catch (NullPointerException e) {
////            e.printStackTrace();
////        }
////    }
////
////
////    public ArrayList<String> GetFiles(String DirectoryPath) {
////        ArrayList<String> MyFiles = new ArrayList<String>();
////        File f = new File(DirectoryPath);
////
////        f.mkdirs();
////        File[] files = f.listFiles();
////        if (files.length == 0)
////            return null;
////        else {
////            for (int i = 0; i < files.length; i++)
////                MyFiles.add(files[i].getName());
////        }
////
////        return MyFiles;
////    }
////
////
////    public void createPDF() throws FileNotFoundException, DocumentException {
////
////        //create document file
////        Document doc = new Document();
////        try {
////
////            Log.e("PDFCreator", "PDF Path: " + path);
////            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
//////            file = new File(dir, "Trinity PDF" + sdf.format(Calendar.getInstance().getTime()) + ".pdf");
////            file = new File(dir,  "ListaPdf.pdf");
////            FileOutputStream fOut = new FileOutputStream(file);
////            PdfWriter writer = PdfWriter.getInstance(doc, fOut);
////
////            //open the document
////            doc.open();
//////create table
////            PdfPTable pt = new PdfPTable(3);
////            pt.setWidthPercentage(100);
////            float[] fl = new float[]{20, 45, 35};
////            pt.setWidths(fl);
////            cell = new PdfPCell();
////            cell.setBorder(Rectangle.NO_BORDER);
////
////            //set drawable in cell
////            Drawable myImage = PDFconversor.this.getResources().getDrawable(R.drawable.cafe);
////            Bitmap bitmap = ((BitmapDrawable) myImage).getBitmap();
////            ByteArrayOutputStream stream = new ByteArrayOutputStream();
////            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
////            byte[] bitmapdata = stream.toByteArray();
////            try {
////                bgImage = Image.getInstance(bitmapdata);
////                bgImage.setAbsolutePosition(330f, 642f);
////                cell.addElement(bgImage);
////                pt.addCell(cell);
////                cell = new PdfPCell();
////                cell.setBorder(Rectangle.NO_BORDER);
////                cell.addElement(new Paragraph("Listado de clientes PDF"));
////
////                cell.addElement(new Paragraph(""));
////                cell.addElement(new Paragraph(""));
////                pt.addCell(cell);
////                cell = new PdfPCell(new Paragraph(""));
////                cell.setBorder(Rectangle.NO_BORDER);
////                pt.addCell(cell);
////
////                PdfPTable pTable = new PdfPTable(1);
////                pTable.setWidthPercentage(100);
////                cell = new PdfPCell();
////                cell.setColspan(1);
////                cell.addElement(pt);
////                pTable.addCell(cell);
////                PdfPTable table = new PdfPTable(6);
////
////                float[] columnWidth = new float[]{6, 30, 30, 20, 20, 30};
////                table.setWidths(columnWidth);
////
////
////                cell = new PdfPCell();
////
////
////                cell.setBackgroundColor(myColor);
////                cell.setColspan(6);
////                cell.addElement(pTable);
////                table.addCell(cell);
////                cell = new PdfPCell(new Phrase(" "));
////                cell.setColspan(6);
////                table.addCell(cell);
////                cell = new PdfPCell();
////                cell.setColspan(6);
////
////                cell.setBackgroundColor(myColor1);
////
////                cell = new PdfPCell(new Phrase("#"));
////                cell.setBackgroundColor(myColor1);
////                table.addCell(cell);
////                cell = new PdfPCell(new Phrase("Header 1"));
////                cell.setBackgroundColor(myColor1);
////                table.addCell(cell);
////                cell = new PdfPCell(new Phrase("Header 2"));
////                cell.setBackgroundColor(myColor1);
////                table.addCell(cell);
////                cell = new PdfPCell(new Phrase("Header 3"));
////                cell.setBackgroundColor(myColor1);
////                table.addCell(cell);
////                cell = new PdfPCell(new Phrase("Header 4"));
////                cell.setBackgroundColor(myColor1);
////                table.addCell(cell);
////                cell = new PdfPCell(new Phrase("Header 5"));
////                cell.setBackgroundColor(myColor1);
////                table.addCell(cell);
////
////                //table.setHeaderRows(3);
////                cell = new PdfPCell();
////                cell.setColspan(6);
////
////                for (int i = 1; i <= 10; i++) {
////                    table.addCell(String.valueOf(i));
////                    table.addCell("Header 1 row " + i);
////                    table.addCell("Header 2 row " + i);
////                    table.addCell("Header 3 row " + i);
////                    table.addCell("Header 4 row " + i);
////                    table.addCell("Header 5 row " + i);
////
////                }
////
////                PdfPTable ftable = new PdfPTable(6);
////                ftable.setWidthPercentage(100);
////                float[] columnWidthaa = new float[]{30, 10, 30, 10, 30, 10};
////                ftable.setWidths(columnWidthaa);
////                cell = new PdfPCell();
////                cell.setColspan(6);
////                cell.setBackgroundColor(myColor1);
////                cell = new PdfPCell(new Phrase("Total Nunber"));
////                cell.setBorder(Rectangle.NO_BORDER);
////                cell.setBackgroundColor(myColor1);
////                ftable.addCell(cell);
////                cell = new PdfPCell(new Phrase(""));
////                cell.setBorder(Rectangle.NO_BORDER);
////                cell.setBackgroundColor(myColor1);
////                ftable.addCell(cell);
////                cell = new PdfPCell(new Phrase(""));
////                cell.setBorder(Rectangle.NO_BORDER);
////                cell.setBackgroundColor(myColor1);
////                ftable.addCell(cell);
////                cell = new PdfPCell(new Phrase(""));
////                cell.setBorder(Rectangle.NO_BORDER);
////                cell.setBackgroundColor(myColor1);
////                ftable.addCell(cell);
////                cell = new PdfPCell(new Phrase(""));
////                cell.setBorder(Rectangle.NO_BORDER);
////                cell.setBackgroundColor(myColor1);
////                ftable.addCell(cell);
////                cell = new PdfPCell(new Phrase(""));
////                cell.setBorder(Rectangle.NO_BORDER);
////                cell.setBackgroundColor(myColor1);
////                ftable.addCell(cell);
////                cell = new PdfPCell(new Paragraph("Footer"));
////                cell.setColspan(6);
////                ftable.addCell(cell);
////                cell = new PdfPCell();
////                cell.setColspan(6);
////                cell.addElement(ftable);
////                table.addCell(cell);
////                doc.add(table);
////                Toast.makeText(getApplicationContext(), "created PDF", Toast.LENGTH_LONG).show();
////            } catch (DocumentException de) {
////                Log.e("PDFCreator", "DocumentException:" + de);
////            } catch (IOException e) {
////                Log.e("PDFCreator", "ioException:" + e);
////            } finally {
////                doc.close();
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
   }

