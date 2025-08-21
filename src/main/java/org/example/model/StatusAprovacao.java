package org.example.model;

import java.time.LocalDate;

public class StatusAprovacao extends BaseModel{
    //Declaração de atributos
    private String motivoRejeicao = null;
    private char status;
    private LocalDate dataSolicitacao;
    private LocalDate dataAprovacao;

    //Métodos construtores

    //Método caso houver rejeição
    public StatusAprovacao(String motivoRejeicao, char status, LocalDate dataSolicitacao) {
        this.motivoRejeicao = motivoRejeicao;
        this.status = status;
        this.dataSolicitacao = dataSolicitacao;
    }

    //Método se não houver rejeição
    public StatusAprovacao(char status, LocalDate dataSolicitacao, LocalDate dataAprovacao) {
        this.status = status;
        this.dataSolicitacao = dataSolicitacao;
    }

    //Métodos getters e setters
    public String getMotivoRejeicao() {
        return this.motivoRejeicao;
    }
    public char getStatus() {
        return this.status;
    }
    public LocalDate getDataSolicitacao() {
        return this.dataSolicitacao;
    }
    public LocalDate getDataAprovacao() {
        return this.dataAprovacao;
    }

    //Métodos setters
    public void setMotivoRejeicao(String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }
    public void setStatus(char status) {
        this.status = status;
    }
    public void setDataAprovacao(LocalDate dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    //Método toString
    public String toString(){
        return (this.motivoRejeicao != null? "Motivo de rejeição: "+this.motivoRejeicao: "") +
                "\nStatus: "+this.status +
                "\nData de solicitação: "+this.dataSolicitacao+
                "\nData de aprovacao: "+this.dataAprovacao;
    }

}
