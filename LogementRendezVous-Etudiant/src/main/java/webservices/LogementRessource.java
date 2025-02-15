package webservices;

import com.google.common.net.HttpHeaders;
import metiers.LogementBusiness;

import javax.ws.rs.Path;

import entities.Logement;
import metiers.LogementBusiness;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@CrossOrigin(origins = "*")
@Path("/logement")
public class LogementRessource {

    LogementBusiness help =new LogementBusiness();




        private static LogementBusiness logementBusiness = new LogementBusiness();

        @GET
        @Path("/{ref}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getLogementByReference(@PathParam("ref") int reference) {
            Logement logement = logementBusiness.getLogementsByReference(reference);
            if (logement != null) {
                return Response.status(200)  .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")  // Permet toutes les origines
                        .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE")  // Permet les méthodes HTTP
                        .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type")  // Permet les en-têtes spécifiques
                        .entity(logement)
                        .build();
            } else {
                return Response.status(404).entity("Logement n'existe  pas").build();
            }
        }

        @POST
        @Path("/add")
        @Produces(MediaType.TEXT_PLAIN)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response addLogement(Logement logement) {
            boolean added = logementBusiness.addLogement(logement);
            if (added) {
                return Response.status(200).header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")  // Permet toutes les origines
                        .entity("Logement ajoutee: " + logement.getDelegation()).build();
            } else {
                return Response.status(400).entity("Error").build();
            }
        }

        @GET
        @Path("/all")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getAllLogement() {
            try {
                return Response.status(200).entity(logementBusiness.getLogements()).build();
            } catch (RuntimeException e) {
                return Response.status(500).entity("Error").build();
            }
        }

        @GET
        @Path("/delegation/{deleguation}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getLogementsByDeleguation(@PathParam("deleguation") String deleguation) {
            return Response.status(200).entity(logementBusiness.getLogementsByDeleguation(deleguation)).build();
        }

        @DELETE
        @Path("/delete/{ref}")
        @Produces(MediaType.TEXT_PLAIN)
        public Response deleteLogement(@PathParam("ref") int reference) {
            boolean deleted = logementBusiness.deleteLogement(reference);
            if (deleted) {
                return Response.status(200).entity("Logement deleted").build();
            } else {
                return Response.status(404).entity("Logemnt not found").build();
            }
        }

        @PUT
        @Path("/update/{ref}")
        @Produces(MediaType.TEXT_PLAIN)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateLogement(@PathParam("ref") int reference, Logement logement) {
            boolean updated = logementBusiness.updateLogement(reference, logement);
            if (updated) {
                return Response.status(200).entity("Logement updated").build();
            } else {
                return Response.status(404).entity("Logement not found for update").build();
            }
        }
    }

