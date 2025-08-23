package org.example.model;

//Importação do LocalDate para atribuir datas
import java.time.LocalDate;

public class TipoEmpresa extends BaseModel{//Abertura da classe

    //Declaração de atributos
    private String nome;
    private char status;
    private LocalDate ultimaAtualizacao;
    private String descricao;

    //Métodos construtores
    public TipoEmpresa(String nome, char status, LocalDate ultimaAtualizacao) {//Metodo caso a descrição não seja inicialmente definida
        this.nome = nome;
        this.status = status;
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public TipoEmpresa(String nome, char status, LocalDate ultimaAtualizacao, String descricao) {//Metodo caso a descrição seja devidamente definida
        this.nome = nome;
        this.status = status;
        this.ultimaAtualizacao = ultimaAtualizacao;
        this.descricao = descricao;
    }

    //Métodos getters
    public String getNome() {
        return this.nome;
    }

    public char getStatus() {
        return this.status;
    }

    public LocalDate getUltimaAtualizacao() {
        return this.ultimaAtualizacao;
    }

    public String getDescricao() {
        return (this.descricao == null) ? "Tipo de empresa sem descrição" : this.descricao;
    }

    //Métodos setters
    public void setStatus(char status) {
        this.status = status;
    }

    public void setUltimaAtualizacao(LocalDate ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    //Metodo toString
    public String toString(){
        return "Nome: "+this.nome +
                "\nStatus: " + this.status+
                "\nUltima atualização: "+this.ultimaAtualizacao+
                "\nDescricao: "+(this.descricao != null? this.descricao : "Sem descrição");
    }



}
