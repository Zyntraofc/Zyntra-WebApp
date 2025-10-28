package org.example.model;

/// Classe criada com objetivo de representar a entidade da tabela MotivoFalta do banco de dados

//Abertura da classe
public class MotivoFalta extends BaseModel{

    //Declaração de atributos
    private String motivo;

    //Metodo construtor
    public MotivoFalta(String motivo){
        this.motivo = motivo;
    }

    //Metodo getter
    public String getMotivo() {
        return this.motivo;
    }

    //Metodo setter
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    //Metodo toString para representação do objeto
    public String toString(){
        return "Motivo: "+this.motivo;
    }

}
