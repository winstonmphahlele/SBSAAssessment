package za.ac.standardbank.card.exception.mapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class EntityNotFoundExceptionMapper  implements ExceptionMapper<EntityNotFoundException> {
    @Override
    public Response toResponse(EntityNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Resource not found: " + exception.getMessage())
                .build();
    }
}
