package org.example.model;

///  Classe criada com objetivo de representar entidade da tabela TipoEmpresa do banco de dados

//Importações
import java.time.LocalDate;

//Abertura da classe
public class TipoEmpresa extends BaseModel{

    //Declaração de atributos
    private String nome;
    private char status;
    private LocalDate ultimaAtualizacao;
    private String descricao;

    //Metodo construtor padrão com recebimento de nome e descrição
    public TipoEmpresa(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    //Metodo construtor que recebe somente nome, caso descrção não seja atribuida já que é default
    public TipoEmpresa(String nome) {
        this.nome = nome;
    }

    //Metodo construtor caso todos os atributos sejam devidamente inseridos
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

    public String getDescricao() {return this.descricao;}

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

    //Metodo toString para representação do objeto
    public String toString(){
        //Descrição pode ser nula
        return "Nome: "+this.nome +
                "\nStatus: " + this.status+
                "\nUltima atualização: "+this.ultimaAtualizacao+
                "\nDescricao: "+(this.descricao != null? this.descricao : "Sem descrição");
    }


}
