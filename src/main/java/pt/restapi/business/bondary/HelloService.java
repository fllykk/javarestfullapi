package pt.restapi.business.bondary;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

// @RequestScoped no implement transaction so we use Stateless
@Stateless
@Path("hello")
public class HelloService {

    @Path("/test")
    @GET
    public String Test(){
        return "Test";
    }
}
