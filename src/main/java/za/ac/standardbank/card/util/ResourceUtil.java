package za.ac.standardbank.card.util;

import jakarta.ws.rs.core.Response;
import za.ac.standardbank.card.dto.ResourceServiceErrorDTO;
import za.ac.standardbank.card.exception.BaseResourceException;

public class ResourceUtil {

    public static Response resourceExceptionResponse(BaseResourceException se, int responseCode) {
        ResourceServiceErrorDTO resourceServiceExceptionDTO = new ResourceServiceErrorDTO();
        resourceServiceExceptionDTO.setErrorCode(se.getErrorCode());
        resourceServiceExceptionDTO.setMessage(se.getMessage());
        return Response.status(responseCode).entity(resourceServiceExceptionDTO).build();
    }

}
