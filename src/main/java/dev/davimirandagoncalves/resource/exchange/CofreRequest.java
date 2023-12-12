package dev.davimirandagoncalves.resource.exchange;

import dev.davimirandagoncalves.entity.Cofre;
import dev.davimirandagoncalves.entity.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

public record CofreRequest(
        String id,
        @NotBlank String nome,
        @NotBlank String moeda,
        @NotNull StatusEnum status,
        @NotBlank String objetivo
) {
    public Cofre toModel() {
        return new Cofre(new ObjectId(id), nome, moeda, status, objetivo);
    }
}
