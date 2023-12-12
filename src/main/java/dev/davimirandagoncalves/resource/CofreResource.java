package dev.davimirandagoncalves.resource;

import dev.davimirandagoncalves.entity.Cofre;
import dev.davimirandagoncalves.resource.exchange.CofreRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;


import java.net.URI;

@Path("/cofre")
public class CofreResource {

    private static final Logger LOG = Logger.getLogger(CofreResource.class);

    @GET
    @Path("/{id}")
    @Operation(summary = "Procura um cofre existente")
    @APIResponse(
            responseCode = "200",
            description = "Cofre encontrado"
    )
    @APIResponse(
            responseCode = "404",
            description = "Cofre não encontrado"
    )
    public Uni<Response> find(@Parameter(name = "id", required = true) @PathParam("id") String id) {
        LOG.info("Procurando cofre com id: " + id);

        validateObjectId(id);

        return Cofre.findById(new ObjectId(id))
                .onItem().ifNotNull().transform(cofre -> {
                    LOG.info("Cofre com id: " + id + " encontrado!");
                    return Response.ok(cofre).build();
                })
                .onItem().ifNull().continueWith(() -> {
                    LOG.info("Cofre com id: " + id + " não foi encontrado!");
                    return Response.status(Response.Status.NOT_FOUND).build();
                });
    }

    @PUT
    @Operation(summary = "Atualiza um cofre existente")
    @APIResponse(
            responseCode = "200",
            description = "Cofre atualizado"
    )
    public Uni<Response> update(@Valid CofreRequest cofreRequest) {
        LOG.info("Atualizar cofre");

        return saveCofre(cofreRequest);
    }

    @POST
    @Operation(summary = "Cria um cofre")
    @APIResponse(
            responseCode = "201",
            description = "Cofre criado"
    )
    public Uni<Response> save(@Valid CofreRequest cofreRequest) {
        LOG.info("Salvar cofre");

        return saveCofre(cofreRequest);
    }

    private static Uni<Response> saveCofre(CofreRequest cofreRequest) {
        final var isUpdate = cofreRequest.id() != null;
        return Uni.createFrom()
                .item(cofreRequest)
                .map(CofreRequest::toModel)
                .flatMap(Cofre::salvar)
                .onItem().ifNotNull().transform(cofre -> {
                    LOG.info("Cofre salvo com sucesso!");
                    return isUpdate ? Response.ok() : Response.created(URI.create("/cofre/" + cofre.id));
                })
                .onItem().transform(Response.ResponseBuilder::build)
                .log("cofre");
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Remove um cofre existente")
    @APIResponse(
            responseCode = "204",
            description = "Remove um cofre"
    )
    public Uni<Response> delete(@Parameter(name = "id", required = true) @PathParam("id") String id) {
        LOG.info("Remover cofre: " + id);

        validateObjectId(id);

        return Cofre.deleteById(new ObjectId(id))
                .onItem().transform(removed -> {
                    if (removed) {
                        LOG.info("Cofre com id: " + id + " removido com sucesso!");
                    } else {
                        LOG.info("Cofre com id: " + id + " não encontrado!");
                    }

                    return removed ? Response.Status.NO_CONTENT : Response.Status.NOT_FOUND;
                })
                .onItem().transform(status -> Response.status(status).build());
    }

    private static void validateObjectId(String id) {
        if (!ObjectId.isValid(id)) {
            throw new RuntimeException("Id Inválido!");
        }
    }
}
