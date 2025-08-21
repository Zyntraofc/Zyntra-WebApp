package org.example.model;

public abstract class BaseModel {
    //Declaração de atributos
    protected int id;

    //Métodos getters
    public Integer getId(){
        return this.id;
    }
    //Métodos setters
    public void setId(int id){
        this.id = id;
    }

    //Método abstrato toString (Obrigar declaração de método)
    public abstract String toString();
}
