package com.izv.angel.practica1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;


public class Principal extends Activity {

    private ArrayList <Bicicleta> bicicletas;
    private AdaptadorArrayList ad;
    private static final int SELECT_PHOTO = 100;
    private int posicion;


    /*****************************************************/
    /*                 metodos on                        */
    /*****************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        initComponents(); // iniciamos componentes
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_aniadir) {
            return aniadir();
        }

        return super.onOptionsItemSelected(item);
    }

    /*****************************************************/
    /*                     auxiliares                    */
    /*****************************************************/

    public void initComponents(){
        bicicletas = new ArrayList <Bicicleta> ();
        //Bicicleta para probar la aplicacion
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/drawable/bike");
        Bicicleta bici = new Bicicleta("Orbea","Alma",uri ,"Montaña");
        bicicletas.add(bici);
        ad = new AdaptadorArrayList(this, R.layout.detalle_lista, bicicletas);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(ad);
        registerForContextMenu(lv);
    }

    private void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /*****************************************************/
    /*                 metodos manejo de la lista        */
    /*****************************************************/


    public boolean aniadir(){
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle(R.string.alta);
        LayoutInflater inflater= LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_alta, null);
        alert.setView(vista);
        alert.setPositiveButton(R.string.alta,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText et1, et2;
                        String tipo;
                        RadioGroup rg = (RadioGroup) vista.findViewById(R.id.radioGroup);
                        RadioButton rb = (RadioButton) vista.findViewById(rg.getCheckedRadioButtonId());
                        tipo = rb.getText().toString();
                        et1 = (EditText) vista.findViewById(R.id.etMarca);
                        et2 = (EditText) vista.findViewById(R.id.etModelo);
                        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/drawable/bike");
                        Bicicleta bc = new Bicicleta(et1.getText().toString(),et2.getText().toString(),uri,tipo);
                        if(!bicicletas.contains(bc)) {
                            bicicletas.add(bc);
                            tostada(getString(R.string.elAniadido));
                        }else{
                            tostada(getString(R.string.existe));
                            aniadir();
                        }
                        Collections.sort(bicicletas);
                        ad.notifyDataSetChanged();

                    }
                });
        alert.setNegativeButton(R.string.cancelar,null);
        alert.show();
        return true;
    }



    public void borrar(View view){
        final int elemento;
        elemento = (Integer)view.getTag();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.borrar) +" "+ bicicletas.get(elemento).getMarca() +" "+bicicletas.get(elemento).getModelo()+" "+getString(R.string.exclamacion) );
        LayoutInflater inflater = LayoutInflater.from(this);
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                bicicletas.remove(elemento);
                Collections.sort(bicicletas);
                ad.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton(android.R.string.no,null);
        alert.show();
    }

    public void editar(View view){
        final int elemento;
        elemento = (Integer)view.getTag();
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle(R.string.editar);
        LayoutInflater inflater= LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_alta, null);
        alert.setView(vista);
        final EditText et1, et2;
        et1 = (EditText) vista.findViewById(R.id.etMarca);
        et1.setText(bicicletas.get(elemento).getMarca());
        et2 = (EditText) vista.findViewById(R.id.etModelo);
        et2.setText(bicicletas.get(elemento).getModelo());
        alert.setPositiveButton(R.string.editar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText et1, et2;
                        String tipo;
                        RadioGroup rg = (RadioGroup) vista.findViewById(R.id.radioGroup);
                        RadioButton rb = (RadioButton) vista.findViewById(rg.getCheckedRadioButtonId());
                        tipo = rb.getText().toString();
                        et1 = (EditText) vista.findViewById(R.id.etMarca);
                        et2 = (EditText) vista.findViewById(R.id.etModelo);
                        bicicletas.get(elemento).setMarca(et1.getText().toString());
                        bicicletas.get(elemento).setModelo(et2.getText().toString());
                        bicicletas.get(elemento).setTipo(tipo);
                        Collections.sort(bicicletas);
                        ad.notifyDataSetChanged();
                    }
                });
        alert.setNegativeButton(R.string.cancelar,null);
        alert.show();

    }



    /*****************************************************/
    /*                 metodos cambiar foto              */
    /*****************************************************/


    public void cambiarFoto(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        posicion = (Integer)view.getTag();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    boolean guardar =true;
                    for (int i=0;i<bicicletas.size();i++){
                        if(bicicletas.get(i).getFoto().compareTo(selectedImage)==0){
                            tostada(getString(R.string.utilizada));
                            guardar=false;
                        }
                    }
                    if (guardar){
                        bicicletas.get(posicion).setFoto(selectedImage);
                    }
                    ad.notifyDataSetChanged();
                }
        }
    }

}
