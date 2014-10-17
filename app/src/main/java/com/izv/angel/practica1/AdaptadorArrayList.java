package com.izv.angel.practica1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class AdaptadorArrayList extends ArrayAdapter <Bicicleta> {

    private Context contexto;
    private ArrayList <Bicicleta> lista;
    private int recurso;
    private Random rd = new Random();
    private static LayoutInflater i;

    public class ViewHolder {
        public TextView tv1, tv2;
        public ImageView iv;
        public ImageButton bt1,bt2;
        public int posicion;
    }

    public AdaptadorArrayList(Context context, int resource, ArrayList <Bicicleta> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.lista = objects;
        this.recurso = resource;
        this.i = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = i.inflate(recurso, null);
            vh = new ViewHolder();
            vh.tv1 = (TextView) convertView.findViewById(R.id.tvMarca);
            vh.tv2 = (TextView) convertView.findViewById(R.id.tvModelo);
            vh.iv = (ImageView) convertView.findViewById(R.id.ivFoto);
            vh.bt1 = (ImageButton) convertView.findViewById(R.id.btEditar);
            vh.bt2 = (ImageButton) convertView.findViewById(R.id.btEliminar);
            vh.bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int elemento;
                    elemento = (Integer) v.getTag();
                    borrar(elemento);
                }
            });
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv1.setText(lista.get(position).getMarca());
        vh.tv2.setText(lista.get(position).getModelo());
        vh.iv.setTag(position);
        return convertView;
    }

    private void borrar(final int pos) {
        AlertDialog.Builder alert = new AlertDialog.Builder(contexto);
        alert.setTitle(R.string.borrar);
        LayoutInflater inflater = LayoutInflater.from(contexto);
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                lista.remove(pos);
                notifyDataSetChanged();
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();
    }
}