package pt.restapi.business.entity;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("userslist")
public class UserList {

    @GET
    @Produces("application/json")
    public List<User> getUsers(){
        List<User> userlist = new ArrayList<>();
        userlist.add(new User(1, "Filipe", "filiper@gmail.com", 999999999));
        userlist.add(new User(2, "Another User", "test@gmail.com", 888888888));
        return userlist;
    }

    //@Inject
    //private ShoppingListItemManager shoppingListItemManager;

    /*
    @GET
    @Produces({"application/json"})
    @Path("{id}")
    @Produces({"application/json"})
    public ShoppingListItem find(@PathParam("id") long id) {
        return shoppingListItemManager.get(id);
    }


    @POST
    @Consumes("application/json")
    public Response save(@Valid User user, @Context UriInfo uriInfo) {
        //shoppingListItem = shoppingListItemManager.save(shoppingListItem);
        URI uri = uriInfo.getAbsolutePathBuilder().path("/" + user.getId()).build();
        return Response.created(uri).entity(user).build();
    }


    @PUT
    @Path("{id}")
    public ShoppingListItem update(@PathParam("id") long id, @Valid ShoppingListItem shoppingListItem) {
        shoppingListItem.setId(id);
        return shoppingListItemManager.save(shoppingListItem);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        try {
            shoppingListItemManager.remove(id);
            return Response.ok().header("status", "deleted").build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NO_CONTENT).header("reason", e.getMessage()).build();
        }
    }*/

}
