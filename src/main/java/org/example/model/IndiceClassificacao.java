package org.example.model;

public class IndiceClassificacao extends BaseModel{//Abertura da classe

    //Declaração de atributos
    private String recomendacao;
    private String preocupacao;
    private double porcentagemMinima;
    private double porcentagemMaxima;

    //Metodo construtor
    public IndiceClassificacao(String recomendacao, String preocupacao, double porcentagemMinima, double preocupacaoMaxima) {
        this.recomendacao = recomendacao;
        this.preocupacao = preocupacao;
        this.porcentagemMinima = porcentagemMinima;
        this.porcentagemMaxima = preocupacaoMaxima;
    }

    //Metodos getters
    public String getRecomendacao() {
        return this.recomendacao;
    }

    public String getPreocupacao() {
        return this.preocupacao;
    }

    public double getPorcentagemMinima() {
        return this.porcentagemMinima;
    }

    public double getPorcentagemMaxima() {
        return this.porcentagemMaxima;
    }

    //Métodos setters
    public void setRecomendacao(String recomendacao) {
        this.recomendacao = recomendacao;
    }

    public void setPreocupacao(String preocupacao) {
        this.preocupacao = preocupacao;
    }

    public void setPorcentagemMinima(double porcentagemMinima) {
        this.porcentagemMinima = porcentagemMinima;
    }

    public void setPorcentagemMaxima(double porcentagemMaxima) {
        this.porcentagemMaxima = porcentagemMaxima;
    }

    //Metodo toString
    public String toString() {
        return "Recomendação: "+this.recomendacao+
                "\nPreocupação: "+this.preocupacao+
                "\nPorcentagem mínima de absenteísmo: "+this.porcentagemMinima+
                "\nPorcentagem máxima de absenteísmo: "+this.porcentagemMaxima;
    }
}
