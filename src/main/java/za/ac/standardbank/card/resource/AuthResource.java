package za.ac.standardbank.card.resource;


import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import za.ac.standardbank.card.util.JwtUtil;
import za.ac.standardbank.generated.LoginRequest;
import za.ac.standardbank.generated.LoginResponse;

import java.util.ArrayList;
import java.util.Arrays;

@Path("/auth")
public class AuthResource {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest credentials) {

        //WM: need to auth against db

        if ("user".equals(credentials.getUsername()) && "password".equals(credentials.getPassword())) {
            String token = JwtUtil.generateToken(credentials.getUsername(), new ArrayList<>(Arrays.asList("user")));
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            return Response.ok(response).build();
        }

        if ("admin".equals(credentials.getUsername()) && "password".equals(credentials.getPassword())) {
            String token = JwtUtil.generateToken(credentials.getUsername(), new ArrayList<>(Arrays.asList("admin")));
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            return Response.ok(response).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}
