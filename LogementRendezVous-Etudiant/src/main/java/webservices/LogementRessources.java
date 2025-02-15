package webservices;

import entities.Logement;
import metiers.LogementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/logement")
public class LogementRessources {
    private final LogementBusiness logementBusiness = new LogementBusiness();

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.status(Response.Status.OK).entity(logementBusiness.getLogements()).build();
    }

    @GET
    @Path("/{reference}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogementByReference(@PathParam("reference") int reference) {
        Logement logement = logementBusiness.getLogementsByReference(reference);
        return logement != null
                ? Response.status(Response.Status.OK).entity(logement).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

//    @GET
//    @Path("/delegation/{delegation}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getLogementsByDelegation(@PathParam("delegation") String delegation) {
//        List<Logement> logements = logementBusiness.getLogementsByDelegation(delegation);
//        return Response.status(Response.Status.OK).entity(logements).build();
//    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addLogement(Logement logement) {
        return logementBusiness.addLogement(logement)
                ? Response.status(Response.Status.CREATED).entity(logement).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{reference}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLogement(@PathParam("reference") int reference, Logement logement) {
        return logementBusiness.updateLogement(reference, logement)
                ? Response.status(Response.Status.OK).entity(logement).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{reference}")
    public Response deleteLogement(@PathParam("reference") int reference) {
        return logementBusiness.deleteLogement(reference)
                ? Response.status(Response.Status.OK).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
