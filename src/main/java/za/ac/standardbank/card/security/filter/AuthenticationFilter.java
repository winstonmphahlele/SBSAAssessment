package za.ac.standardbank.card.security.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import za.ac.standardbank.card.security.Auth;
import za.ac.standardbank.card.util.JwtUtil;

import java.io.IOException;
import java.lang.reflect.Method;

@Provider
@Auth
public class AuthenticationFilter implements ContainerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Method method = resourceInfo.getResourceMethod();

        if (method.isAnnotationPresent(Auth.class)) {
            String token = requestContext.getHeaderString("Authorization");
            if (!JwtUtil.validateToken(token)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            Auth authAnnotation = method.getAnnotation(Auth.class);
            String requiredRole = authAnnotation.role();
            if (!requiredRole.isEmpty() && !JwtUtil.hasRole(token, requiredRole)) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            }
        }

    }

}