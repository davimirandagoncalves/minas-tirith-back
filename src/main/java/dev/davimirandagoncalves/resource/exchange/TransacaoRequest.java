package dev.davimirandagoncalves.resource.exchange;

import dev.davimirandagoncalves.entity.Transacao;
import dev.davimirandagoncalves.entity.enums.MoedaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.time.LocalDate;

public record TransacaoRequest(
        String id,
        @NotBlank String valor,
        String descricao,
        @NotNull MoedaEnum moeda,
        @NotNull LocalDate data
) {

    public Transacao toModel() {
        ObjectId objectId = null;
        if (id != null) {
            objectId = new ObjectId(id);
        }
        return new Transacao(objectId, valor, descricao, moeda, data);
    }
}
