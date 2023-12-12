package dev.davimirandagoncalves.entity;


import java.time.LocalDate;

public class Transacao {

    private String valor;
    private String descricao;
    private String moeda;
    private LocalDate data;

    public Transacao() {
    }

    public Transacao(String valor, String descricao, String moeda, LocalDate data) {
        this.valor = valor;
        this.descricao = descricao;
        this.moeda = moeda;
        this.data = data;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "valor='" + valor + '\'' +
                ", descricao='" + descricao + '\'' +
                ", moeda='" + moeda + '\'' +
                ", data=" + data +
                '}';
    }
}
