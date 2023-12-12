package dev.davimirandagoncalves.entity;

import dev.davimirandagoncalves.entity.enums.MoedaEnum;
import dev.davimirandagoncalves.entity.enums.StatusEnum;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.smallrye.mutiny.Uni;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.List;

@MongoEntity(collection="cofres")
public class Cofre extends ReactivePanacheMongoEntity {

    private String nome;

    private MoedaEnum moeda;

    private StatusEnum status;

    private String objetivo;

    private String total;

    private String progresso;

    private List<Transacao> transacoes;

    public Cofre() {
    }

    public Cofre(ObjectId id, String nome, MoedaEnum moeda, StatusEnum status, String objetivo) {
        this.id = id;
        this.nome = nome;
        this.moeda = moeda;
        this.status = status;
        this.objetivo = objetivo;
    }

    public Uni<Cofre> salvar() {
        if (this.isUpdate()) {
            return findById(id)
                    .onItem().ifNotNull().transformToUni(cofreSalvo -> {
                        this.transacoes = ((Cofre) cofreSalvo).getTransacoes();
                        atualizaTotalEProgresso();
                        return this.persistOrUpdate();
                    });
        }

        return this.persist();
    }

    private void atualizaTotalEProgresso() {
        if (this.transacoes == null || this.transacoes.isEmpty()) {
            return;
        }

        var somaTransacoes = this.transacoes.stream()
                .map(transacao -> new BigDecimal(transacao.getValor()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.total = somaTransacoes.toPlainString();

        var porcentagem = new BigDecimal(this.objetivo).multiply(somaTransacoes).divide(BigDecimal.valueOf(100));

        this.progresso = porcentagem.toPlainString();

        System.out.println(total + "  " + progresso);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public MoedaEnum getMoeda() {
        return moeda;
    }

    public void setMoeda(MoedaEnum moeda) {
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getProgresso() {
        return progresso;
    }

    public void setProgresso(String progresso) {
        this.progresso = progresso;
    }

    @BsonIgnore
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
