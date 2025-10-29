package org.example.model;

/// Classe criada com objetivo de representar entidade da tabela StatusAprovação do banco de dados

//Importações
import java.time.LocalDate;

//Abertura da classe
public class StatusAprovacao extends BaseModel{

    //Declaração de atributos
    private String motivoRejeicao = null;
    private char status;
    private LocalDate dataSolicitacao;
    private LocalDate dataAprovacao = null;

    //Métodos construtores
    public StatusAprovacao(LocalDate dataSolicitacao) {//Metodo para inserir padrão (pendente)
        this.motivoRejeicao = null;
        this.status = 'p';
        this.dataSolicitacao = dataSolicitacao;
        this.dataAprovacao = null;
    }
    //Metodo construtor para retorno de listagens
    public StatusAprovacao(String motivoRejeicao, char status, LocalDate dataSolicitacao, LocalDate dataAprovacao) {//Metodo caso houver rejeição
        this.motivoRejeicao = motivoRejeicao;
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

    //Metodo toString para representação do objeto
    public String toString(){
        //dataAprovacao e motivoRejeicao podem ser nulas
        return
                (this.motivoRejeicao != null? "Motivo de rejeição: "+this.motivoRejeicao: "") +
                "\nStatus: "+this.status +
                "\nData de solicitação: "+this.dataSolicitacao+
                (this.dataAprovacao != null ? "\nData de aprovacao: "+this.dataAprovacao : "");
    }

}
