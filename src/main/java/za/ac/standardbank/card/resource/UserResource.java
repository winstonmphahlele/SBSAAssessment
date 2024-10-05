package za.ac.standardbank.card.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import za.ac.standardbank.card.exception.ResourceAlreadyExistsException;
import za.ac.standardbank.card.exception.ResourceNotFoundException;
import za.ac.standardbank.card.exception.ResourceServiceException;
import za.ac.standardbank.card.security.Auth;
import za.ac.standardbank.card.service.UserService;
import za.ac.standardbank.card.util.ResourceUtil;
import za.ac.standardbank.generated.CreateUserRequest;
import za.ac.standardbank.generated.UpdateUserRequest;
import za.ac.standardbank.generated.UserResponse;

import java.util.List;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid CreateUserRequest userRequest) {

        try {
            UserResponse response = userService.save(userRequest);
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (ResourceAlreadyExistsException ex) {
            return ResourceUtil.resourceExceptionResponse(ex, Response.Status.CONFLICT.getStatusCode());
        } catch (ResourceServiceException ex) {
            return ResourceUtil.resourceExceptionResponse(ex, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(UpdateUserRequest updateUserRequest) {
       try {
           UserResponse updatedUserResponse = userService.update(updateUserRequest);
           return Response.status(Response.Status.OK).entity(updatedUserResponse).build();
       }catch (ResourceServiceException ex){
           return ResourceUtil.resourceExceptionResponse(ex, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
       }
    }

    @DELETE()
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Auth(role = "admin")
    public Response deleteUser(@PathParam(value = "id") Long id) {
        try {
            userService.delete(id);
            return Response.status(Response.Status.OK).build();
        }catch (ResourceNotFoundException ex){
            return ResourceUtil.resourceExceptionResponse(ex, Response.Status.NOT_FOUND.getStatusCode());
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam(value = "id") Long id){
        try {
            UserResponse userResponse = userService.findById(id);
            return Response.status(Response.Status.OK).entity(userResponse).build();
        }catch (ResourceNotFoundException ex){
            return ResourceUtil.resourceExceptionResponse(ex, Response.Status.NOT_FOUND.getStatusCode());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(){
        List<UserResponse> users = userService.findAll();
        return Response.status(Response.Status.OK).entity(users).build();
    }


}
