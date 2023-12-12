package dev.davimirandagoncalves.resource;

import dev.davimirandagoncalves.resource.exchange.TransacaoRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

@Path("/transacao")
public class TransacaoResource {

    private static final Logger LOG = Logger.getLogger(TransacaoResource.class);

    @GET
    @Path("/{id}/")
    @Operation(summary = "Procura uma transação existente")
    @APIResponse(
            responseCode = "200",
            description = "Transação encontrada"
    )
    @APIResponse(
            responseCode = "404",
            description = "Transação não encontrada"
    )
    public Uni<Response> find(@PathParam("id") String id) {
        LOG.info("Procurando transacação com ID: " + id);
        return Uni.createFrom().nullItem();
    }

    @POST
    @Operation(summary = "Cria uma transação")
    @APIResponse(
            responseCode = "201",
            description = "Transação salva"
    )
    public Uni<Response> save(@Valid TransacaoRequest transacaoRequest) {
        LOG.info("Salvando uma transação.");
        return Uni.createFrom().nullItem();
    }

    @PUT
    @Operation(summary = "Atualiza uma transação existente")
    @APIResponse(
            responseCode = "200",
            description = "Transação atualizada"
    )
    public Uni<Response> update() {
        return Uni.createFrom().nullItem();
    }

    @DELETE
    @Operation(summary = "Remove uma transação existente")
    @APIResponse(
            responseCode = "204",
            description = "Transação removida"
    )
    public Uni<Response> delete() {
        return Uni.createFrom().nullItem();
    }
}
