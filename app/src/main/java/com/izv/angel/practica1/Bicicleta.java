package com.izv.angel.practica1;

import android.graphics.Bitmap;

/**
 * Created by AngelakaMogu on 17/10/2014.
 */
public class Bicicleta {

    private String marca;
    private String modelo;
    private String anio;
    private Bitmap foto;

    public Bicicleta(String marca, String modelo, Bitmap foto, String anio) {
        this.marca = marca;
        this.modelo = modelo;
        this.foto = foto;
        this.anio=anio;
    }

    public Bicicleta() {
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }
}
