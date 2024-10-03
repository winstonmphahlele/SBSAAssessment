package za.ac.standardbank.card.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import za.ac.standardbank.card.dto.UserDto;
import za.ac.standardbank.card.service.UserService;

import java.util.List;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserDto userDto) {
         UserDto response = userService.save(userDto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(UserDto userDto) {
         userService.update(userDto);
        return Response.status(Response.Status.OK).entity(userDto).build();
    }

    @DELETE()
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam(value = "id") Long id) {
        userService.delete(id);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam(value = "id") Long id){
        UserDto userDto = userService.findById(id);
        return Response.status(Response.Status.OK).entity(userDto).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(){
        List<UserDto> users = userService.findAll();
        return Response.status(Response.Status.OK).entity(users).build();
    }
}
