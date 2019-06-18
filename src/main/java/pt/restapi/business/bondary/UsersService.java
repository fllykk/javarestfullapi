package pt.restapi.business.bondary;

import pt.restapi.business.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

// @RequestScoped no implement transaction so we use Stateless
@Stateless
@Path("user")
public class UsersService {

    @Inject
    private UsersManager usersManager;

    @GET
    @Produces({"application/json"})
    public List<User> getAll() {
        List<User> userlist = new ArrayList<>();
        userlist.add(new User(1, "Filipe", "filiper@gmail.com", 999999999));
        userlist.add(new User(2, "Another User", "test@gmail.com", 888888888));
        return userlist;
    }

    /**
     * FIND ID GET /id
     * SAVE  POST (objeto para guardar)
     * UPDATE PUT /id (objecto para alterar)
     * DELETE ID  DELETE /id
     */

    @GET
    @Path("/{id}")
    @Produces({"application/json"})
    public User find(@PathParam("id") int id) {
        return usersManager.get(id);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response save(@Valid  User user, @Context UriInfo uriInfo) {
        user = usersManager.save(user);
        URI uri = uriInfo.getAbsolutePathBuilder().path("/" + user.getId()).build();
        return Response.created(uri).entity(user).build();
    }

    @PUT
    @Path("{id}")
    @Produces("application/json")
    public User update(@PathParam("id") int id, @Valid User user) {
        user.setId(id);
        return usersManager.save(user);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            usersManager.remove(id);
            return Response.ok().header("status", "deleted").build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NO_CONTENT).header("reason", e.getMessage()).build();
        }
    }

}
