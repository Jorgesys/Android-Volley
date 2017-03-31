package com.jorgesys.volleyforce;

import android.util.Log;

/**
 * Created by jorgesys on 30/03/2017.
 */

public class Post {
    // Atributos
    private String title;
    private String description;
    private String imagen;
    private String category;
    private String date;

    public Post() {
    }

    public Post(String titulo, String descripcion, String imagen, String category, String date) {
        this.title = titulo;
        this.description = descripcion;
        this.imagen = imagen;
        this.category = category;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagen() {
        //Picasso validation to avoid empty image.
        return (imagen == null || imagen.isEmpty())?"http://assets-cdn.github.com/images/modules/open_graph/github-mark.png":imagen;
    }


    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}