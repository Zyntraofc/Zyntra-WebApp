package org.example.model;

/// Classe criada com objetivo de representar a entidade da tabela Empresa do banco de dados

//Abertura da classe
public class Empresa extends BaseModel{

    //Declaração de atributos
    private int idTipoEmpresa;
    private int idIndiceClassificacao;
    private int idStatusAprovacao;
    private String nome;
    private String cnpj;
    private String email;
    private String telefone;

    //Metodo construtor
    public Empresa(int idTipoEmpresa, int idIndiceClassificacao, int idStatusAprovacao, String nome, String cnpj, String email, String telefone) {
        this.idTipoEmpresa = idTipoEmpresa;
        this.idIndiceClassificacao = idIndiceClassificacao;
        this.idStatusAprovacao = idStatusAprovacao;
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
    }

    //Metodos getters
    public int getIdTipoEmpresa() {
        return this.idTipoEmpresa;
    }

    public int getIdIndiceClassificacao() {
        return this.idIndiceClassificacao;
    }

    public int getIdStatusAprovacao() {
        return this.idStatusAprovacao;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public String getEmail() {
        return this.email;
    }

    public String getTelefone() {
        return this.telefone;
    }



    //Métodos setters
    public void setIdIndiceClassificacao(int idIndiceClassificacao) {
        this.idIndiceClassificacao = idIndiceClassificacao;
    }

    public void idStatusAprovacao(int idStatusAprovacao) {
        this.idStatusAprovacao = idStatusAprovacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    //Metodo toString e representação do objeto
    public String toString() {
        return "ID Tipo da empresa: "+this.idTipoEmpresa +
                "\nID índice de classificação: "+this.idIndiceClassificacao+
                "\nID status de aprovação: "+ this.idStatusAprovacao+
                "\nNome da empresa: "+ this.nome +
                "\nCNPJ: "+this.cnpj +
                "\nE-mail: "+this.email +
                "\nTelefone: "+this.telefone;
    }


}
