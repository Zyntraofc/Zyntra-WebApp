package org.example.model;

public class MotivoFalta extends BaseModel{
    //Declaração de atributos
    private String motivo;

    //Método construtor
    public MotivoFalta(String motivo){
        this.motivo = motivo;
    }

    //Métodos getters
    public String getMotivo() {
        return this.motivo;
    }

    //Métodos setters
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    //Método toString
    public String toString(){
        return "Motivo: "+this.motivo;
    }

}
