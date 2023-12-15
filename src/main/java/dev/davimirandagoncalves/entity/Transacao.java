package dev.davimirandagoncalves.entity;


import dev.davimirandagoncalves.entity.enums.MoedaEnum;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Transacao {

    private ObjectId id;
    private String valor;
    private String descricao;
    private MoedaEnum moeda;
    private LocalDate data;

    public Transacao() {
    }

    public Transacao(ObjectId id, String valor, String descricao, MoedaEnum moeda, LocalDate data) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
        this.moeda = moeda;
        this.data = data;
    }

    @BsonIgnore
    public boolean compareId(ObjectId id) {
        if (this.id == null) {
            return false;
        }

        return this.id.equals(id);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public MoedaEnum getMoeda() {
        return moeda;
    }

    public void setMoeda(MoedaEnum moeda) {
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
                "id=" + id +
                ", valor='" + valor + '\'' +
                ", descricao='" + descricao + '\'' +
                ", moeda=" + moeda +
                ", data=" + data +
                '}';
    }
}
