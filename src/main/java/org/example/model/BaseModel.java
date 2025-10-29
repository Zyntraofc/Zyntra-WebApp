package org.example.model;

/// Classe criada com objetivo de obrigar todas as classes Model a declararem parametros que tem em comum e método toString

//Abertura da classe abstrata
public abstract class BaseModel {

    //Declaração de atributo ID (todas as tabelas do banco de dados tem)
    protected int id;

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