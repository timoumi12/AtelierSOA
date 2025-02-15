package webservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/")
public class StaticResource {
    @GET
    @Path("/index.html")
    public Response getIndexPage() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\ihebt\\AtelierSOA\\LogementRendezVous-Etudiant\\src\\main\\java\\webservices\\index.html")));
            return Response.ok(content).header("Content-Type", "text/html").build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}


