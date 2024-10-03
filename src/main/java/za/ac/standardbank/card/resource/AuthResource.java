package za.ac.standardbank.card.resource;


import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import za.ac.standardbank.card.security.util.JwtUtil;

import java.util.ArrayList;
import java.util.Arrays;

@Path("/auth")
public class AuthResource {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserCredentials credentials) {
        // In a real application, you should validate the credentials with a user database
        if ("user".equals(credentials.getUsername()) && "password".equals(credentials.getPassword())) {
            String token = JwtUtil.generateToken(credentials.getUsername(), new ArrayList<>(Arrays.asList("user")));
            return Response.ok(new AuthResponse(token)).build();
        }

        if ("admin".equals(credentials.getUsername()) && "password".equals(credentials.getPassword())) {
            String token = JwtUtil.generateToken(credentials.getUsername(), new ArrayList<>(Arrays.asList("admin")));
            return Response.ok(new AuthResponse(token)).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    public static class UserCredentials {
        private String username;
        private String password;

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class AuthResponse {
        private String token;

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}
