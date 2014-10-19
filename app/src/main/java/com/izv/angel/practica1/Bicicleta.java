package com.izv.angel.practica1;

import android.graphics.Bitmap;

/**
 * Created by AngelakaMogu on 17/10/2014.
 */
public class Bicicleta {

    private String marca;
    private String modelo;
    private String tipo;
    private Bitmap foto;

    public Bicicleta(String marca, String modelo, Bitmap foto, String tipo) {
        this.marca = marca;
        this.modelo = modelo;
        this.foto = foto;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
