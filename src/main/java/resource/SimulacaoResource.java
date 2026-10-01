package resource;

import entity.Simulacao;
import jakarta.ws.rs.*;
import service.SimulacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/simulacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    @Inject
    SimulacaoService service;

    @POST
    public Response criar(Simulacao simulacao){
        // Validações básicas de entrada
        if (simulacao == null ||
                simulacao.valorInicial == null ||
                simulacao.valorInicial.compareTo(BigDecimal.ZERO) <= 0 ||
                simulacao.taxaJurosMensal == null ||
                simulacao.taxaJurosMensal.compareTo(BigDecimal.ZERO) <= 0 ||
                simulacao.prazoMeses == null ||
                simulacao.prazoMeses <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Entradas inválidas").build();
        }

        Simulacao resultado = service.calcularSimulacao(simulacao);
        return Response.status(201).entity(resultado).build();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") String id){
        Long longId;
        try {
            longId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID inválido").build();
        }

        Simulacao simulacao = service.buscarPorId(longId);

        if (simulacao == null){
            return Response.status(404).build();
        }
        return Response.ok(simulacao).build();
    }

}