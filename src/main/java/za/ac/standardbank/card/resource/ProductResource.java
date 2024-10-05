package za.ac.standardbank.card.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import za.ac.standardbank.card.exception.ResourceAlreadyExistsException;
import za.ac.standardbank.card.exception.ResourceNotFoundException;
import za.ac.standardbank.card.exception.ResourceServiceException;
import za.ac.standardbank.card.service.ProductService;
import za.ac.standardbank.card.util.ResourceUtil;
import za.ac.standardbank.generated.CreateProductRequest;
import za.ac.standardbank.generated.ProductResponse;
import za.ac.standardbank.generated.UpdateProductRequest;

import java.util.List;

@Path("/products")
public class ProductResource {

    @Inject
    private ProductService productService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(@Valid CreateProductRequest productRequest) {
        try {
            ProductResponse response = productService.save(productRequest);
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
    public Response updateProduct(@Valid UpdateProductRequest updateProductRequest) {
        try {
            ProductResponse updateProductResponse = productService.update(updateProductRequest);
            return Response.status(Response.Status.OK).entity(updateProductResponse).build();
        }catch (ResourceNotFoundException ex) {
            return ResourceUtil.resourceExceptionResponse(ex, Response.Status.CONFLICT.getStatusCode());
        } catch (ResourceServiceException ex) {
            return ResourceUtil.resourceExceptionResponse(ex, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }

    @DELETE()
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam(value = "id") Long id) {
       try {
           productService.delete(id);
           return Response.status(Response.Status.OK).build();
        }catch (ResourceNotFoundException ex){
            return ResourceUtil.resourceExceptionResponse(ex, Response.Status.NOT_FOUND.getStatusCode());
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam(value = "id") Long id){
        try {
            ProductResponse productResponse = productService.findById(id);
            return Response.status(Response.Status.OK).entity(productResponse).build();
        } catch (ResourceNotFoundException ex) {
            return ResourceUtil.resourceExceptionResponse(ex, Response.Status.NOT_FOUND.getStatusCode());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts(){
        List<ProductResponse> products = productService.findAll();
        return Response.status(Response.Status.OK).entity(products).build();
    }
}
