package org.example.model;

public abstract class BaseModel {//Abertura de classe abstrata que servirá de base para todas as models

    //Declaração de atributos
    protected int id;//Gerado automáticamente pelo database

    //Métodos getters
    public int getId() {
        return this.id;
    }

    //Métodos setters
    public void setId(int id) {
        this.id = id;
    }

    //Metodo abstrato toString (Obrigar declaração de metodo)
    public abstract String toString();
}