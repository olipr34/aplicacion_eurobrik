package com.example.lectorqr;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptador_trabajador extends BaseAdapter {
    private Context context;
    private ArrayList<trabajador> listaTrabajadores;
    private LayoutInflater inflater;

    public adaptador_trabajador(Activity context, ArrayList<trabajador>
            listaTrabajadores) {
        this.context = context;
        this.listaTrabajadores = listaTrabajadores;
        inflater = LayoutInflater.from(context);

    }

    static class ViewHolder {

        TextView nombre;
        TextView apellidos;
        TextView comunidad;
        TextView localizacion;
        TextView fecha;
        TextView hora;

    }


    @Override
    public int getCount() {
        return listaTrabajadores.size();
    }

    @Override
    public Object getItem(int posicion) {
        return listaTrabajadores.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
// Si la View es null se crea de nuevo
        if (convertView == null) {
            convertView =LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.plantilla, null);
            holder = new ViewHolder();

            holder.nombre = (TextView)
                    convertView.findViewById(R.id.tvNombre);
            holder.apellidos=(TextView)
                    convertView.findViewById(R.id.tvApellidos);
            holder.comunidad=(TextView)
                    convertView.findViewById(R.id.tvcomunidad);
            holder.localizacion = (TextView)
                    convertView.findViewById(R.id.tvTrabajo);

            holder.fecha = (TextView)
                    convertView.findViewById(R.id.tvFecha);
            holder.hora = (TextView)
                    convertView.findViewById(R.id.tvHora);


            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
    trabajador trabajador = listaTrabajadores.get(position);

        holder.nombre.setText(trabajador.getNombre());
        holder.apellidos.setText(trabajador.getApellidos());
        holder.comunidad.setText(trabajador.getComunidad());
        holder.localizacion.setText(trabajador.getLocalizacion());
        holder.fecha.setText(trabajador.getFecha());
        holder.hora.setText(trabajador.getHora());

        return convertView;
    }
}
