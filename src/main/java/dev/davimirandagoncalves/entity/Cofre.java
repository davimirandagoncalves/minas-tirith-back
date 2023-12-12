package dev.davimirandagoncalves.entity;

import dev.davimirandagoncalves.entity.enums.StatusEnum;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.smallrye.mutiny.Uni;
import org.bson.types.ObjectId;

import java.util.List;

@MongoEntity(collection="cofres")
public class Cofre extends ReactivePanacheMongoEntity {

    private String nome;

    private String moeda;

    private StatusEnum status;

    private String objetivo;

    private Integer total;

    private Integer progresso;

    private List<Transacao> transacoes;

    public Cofre() {
    }

    public Cofre(ObjectId id, String nome, String moeda, StatusEnum status, String objetivo) {
        this.id = id;
        this.nome = nome;
        this.moeda = moeda;
        this.status = status;
        this.objetivo = objetivo;
    }

    public Uni<Cofre> salvar() {
        if (this.isUpdate()) {

        }

        return this.persist();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getProgresso() {
        return progresso;
    }

    public void setProgresso(Integer progresso) {
        this.progresso = progresso;
    }

    public boolean isUpdate() {
        return this.id != null;
    }

    @Override
    public String toString() {
        return "Cofre{" +
                "nome='" + nome + '\'' +
                ", moeda='" + moeda + '\'' +
                ", status='" + status + '\'' +
                ", objetivo=" + objetivo +
                ", total=" + total +
                ", progresso=" + progresso +
                ", transacoes=" + transacoes +
                '}';
    }
}
