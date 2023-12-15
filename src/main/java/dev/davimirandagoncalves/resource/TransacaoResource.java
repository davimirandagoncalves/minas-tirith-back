package dev.davimirandagoncalves.resource;

import dev.davimirandagoncalves.entity.Cofre;
import dev.davimirandagoncalves.resource.exchange.TransacaoRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import java.net.URI;

@Path("/transacao")
public class TransacaoResource {

    private static final Logger LOG = Logger.getLogger(TransacaoResource.class);

    @GET
    @Path("/cofre/{cofreId}")
    @Operation(summary = "Consulta as transações de um cofre específico")
    @APIResponse(
            responseCode = "200",
            description = "Lista de transações por cofre"
    )
    public Uni<Response> listTransacoesPorCofre(@Parameter(required = true) @NotBlank @PathParam("cofreId") String cofreId) {
        LOG.info("Listando transações por cofre: " + cofreId);
        if (!ObjectId.isValid(cofreId)) {
            throw new IllegalArgumentException("CofreId invalido");
        }
        return Cofre.findById(new ObjectId(cofreId))
                .onItem().ifNotNull().transform(cofre -> {
                    LOG.info("Transações encontradas!");
                    return ((Cofre) cofre).getTransacoes();
                })
                .onItem().ifNotNull().transform(transacoes -> Response.ok(transacoes).build())
                .onItem().ifNull().failWith(new NotFoundException("Cofre não encontrado"));
    }

    @POST
    @Operation(summary = "Cria uma transação")
    @APIResponse(
            responseCode = "201",
            description = "Transação salva"
    )
    public Uni<Response> save(@Valid TransacaoRequest transacaoRequest) {
        LOG.info("Salvando a transação " + transacaoRequest.toString() + " no cofre: " + transacaoRequest.cofreId());
        return saveTransacao(transacaoRequest);
    }

    @PUT
    @Operation(summary = "Atualiza uma transação existente")
    @APIResponse(
            responseCode = "200",
            description = "Transação atualizada"
    )
    public Uni<Response> update(@Valid TransacaoRequest transacaoRequest) {
        LOG.info("Atualizando a transação " + transacaoRequest.toString() + " do cofre: " + transacaoRequest.cofreId());
        return save(transacaoRequest);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Remove uma transação existente")
    @APIResponse(
            responseCode = "204",
            description = "Transação removida"
    )
    @APIResponse(
            responseCode = "404",
            description = "Transação não encontrada"
    )
    public Uni<Response> delete(@PathParam("id") String id) {
        LOG.info("Removendo a transação: " + id);
        return Cofre.removeTransacaoById(new ObjectId(id))
                .invoke(removido -> LOG.info("Transação removida: " + removido))
                .onItem().ifNotNull().transform(removido -> removido ? Response.noContent() : Response.status(Response.Status.NOT_FOUND))
                .onItem().ifNotNull().transform(Response.ResponseBuilder::build);
    }

    private static Uni<Response> saveTransacao(TransacaoRequest transacaoRequest) {
        var isUpdate = transacaoRequest.id() != null;
        return Cofre.findById(new ObjectId(transacaoRequest.cofreId()))
                .onItem().ifNull().failWith(new NotFoundException("Cofre não encontrado."))
                .onItem().ifNotNull().transformToUni(cofre -> ((Cofre) cofre).salvaTransacao(transacaoRequest))
                .onItem().ifNull().failWith(new RuntimeException("Transação não salva."))
                .onItem().ifNotNull().transform((Cofre cofre) -> {
                    LOG.info("Transação salva com sucesso.");
                    var id = cofre.getTransacoes().get(cofre.getTransacoes().size() - 1).getId();
                    var response = isUpdate ? Response.ok() : Response.created(URI.create("/transacao/" + id));
                    return response.build();
                });
    }
}
