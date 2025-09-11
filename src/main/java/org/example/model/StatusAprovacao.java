package org.example.model;

//Importação do LocalDate para atribuir datas
import java.time.LocalDate;

public class StatusAprovacao extends BaseModel{//abertura da classe

    //Declaração de atributos
    private String motivoRejeicao = null;
    private char status;
    private LocalDate dataSolicitacao;
    private LocalDate dataAprovacao;

    //Métodos construtores
    public StatusAprovacao(String motivoRejeicao, char status, LocalDate dataSolicitacao, LocalDate dataAprovaca) {//Metodo caso houver rejeição
        this.motivoRejeicao = motivoRejeicao;
        this.status = status;
        this.dataSolicitacao = dataSolicitacao;
        this.dataAprovacao = dataAprovacao;
    }

    public StatusAprovacao(char status, LocalDate dataSolicitacao, LocalDate dataAprovacao) {//Metodo se não houver rejeição
        this.status = status;
        this.dataSolicitacao = dataSolicitacao;
        this.dataAprovacao = dataAprovacao;
    }

    //Métodos getters e setters
    public String getMotivoRejeicao() {
        return this.motivoRejeicao != null ? this.motivoRejeicao : "";
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

    //Metodo toString
    public String toString(){
        return (this.motivoRejeicao != null? "Motivo de rejeição: "+this.motivoRejeicao: "") +
                "\nStatus: "+this.status +
                "\nData de solicitação: "+this.dataSolicitacao+
                (this.dataAprovacao != null ? "\nData de aprovacao: "+this.dataAprovacao : "");
    }

}
