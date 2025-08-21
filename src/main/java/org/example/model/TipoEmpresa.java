package org.example.model;

import java.time.LocalDate;
public class TipoEmpresa extends BaseModel{
    //Declaração de atributos
    private String nome;
    private char status;
    private LocalDate ultimaAtualizacao;
    private String descricao;

    //Métodos construtores

    //Método caso a descrição não seja inicialmente definida
    public TipoEmpresa(String nome, char status, LocalDate ultimaAtualizacao) {
        this.nome = nome;
        this.status = status;
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    //Método caso a descrição seja devidamente definida
    public TipoEmpresa(String nome, char status, LocalDate ultimaAtualizacao, String descricao) {
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

    //Método toString
    public String toString(){
        return "Nome: "+this.nome +
                "\nStatus: " + this.status+
                "\nUltima atualização: "+this.ultimaAtualizacao+
                "\nDescricao: "+(this.descricao != null? this.descricao : "Sem descrição");
    }



}
