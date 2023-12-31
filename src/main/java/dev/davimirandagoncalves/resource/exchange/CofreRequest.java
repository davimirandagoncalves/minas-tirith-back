package dev.davimirandagoncalves.resource.exchange;

import dev.davimirandagoncalves.entity.Cofre;
import dev.davimirandagoncalves.entity.enums.MoedaEnum;
import dev.davimirandagoncalves.entity.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

public record CofreRequest(
        String id,
        @NotBlank String nome,
        @NotNull MoedaEnum moeda,
        @NotNull StatusEnum status,
        @NotBlank String objetivo
) {
    public Cofre toModel() {
        ObjectId objectId = null;
        if (id != null) {
            objectId = new ObjectId(id);
        }
        return new Cofre(objectId, nome, moeda, status, objetivo);
    }
}
