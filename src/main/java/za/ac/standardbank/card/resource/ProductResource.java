package za.ac.standardbank.card.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import za.ac.standardbank.card.dto.ProductDto;
import za.ac.standardbank.card.service.ProductService;

import java.util.List;

@Path("/products")
public class ProductResource {

    @Inject
    private ProductService productService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(ProductDto productDto) {
        ProductDto response = productService.save(productDto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(ProductDto productDto) {
         productService.update(productDto);
        return Response.status(Response.Status.OK).entity(productDto).build();
    }

    @DELETE()
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam(value = "id") Long id) {
        productService.delete(id);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam(value = "id") Long id){
        ProductDto productDto = productService.findById(id);
        return Response.status(Response.Status.OK).entity(productDto).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts(){
        List<ProductDto> products = productService.findAll();
        return Response.status(Response.Status.OK).entity(products).build();
    }
}
