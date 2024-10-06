package za.ac.standardbank.card.resource;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.standardbank.card.exception.ResourceAlreadyExistsException;
import za.ac.standardbank.card.exception.ResourceNotFoundException;
import za.ac.standardbank.card.exception.ResourceServiceException;
import za.ac.standardbank.card.service.ProductService;
import za.ac.standardbank.generated.CreateProductRequest;
import za.ac.standardbank.generated.ProductResponse;
import za.ac.standardbank.generated.UpdateProductRequest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ProductResourceTest {

    @InjectMocks
    private ProductResource productResource;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct_Success() throws ResourceServiceException, ResourceAlreadyExistsException {
        CreateProductRequest request = new CreateProductRequest();
        ProductResponse productResponse = new ProductResponse();

        when(productService.save(request)).thenReturn(productResponse);

        Response response = productResource.createProduct(request);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(productResponse, response.getEntity());
    }


    @Test
    void testUpdateProduct_Success() throws ResourceServiceException, ResourceNotFoundException {
        UpdateProductRequest request = new UpdateProductRequest();
        ProductResponse updatedProductResponse = new ProductResponse();

        when(productService.update(request)).thenReturn(updatedProductResponse);

        Response response = productResource.updateProduct(request);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(updatedProductResponse, response.getEntity());
    }

    @Test
    void testUpdateProduct_NotFound() throws ResourceServiceException, ResourceNotFoundException {
        UpdateProductRequest request = new UpdateProductRequest();

        when(productService.update(request)).thenThrow(new ResourceNotFoundException(50003, String.format("Product with ID: %s does not exits", request.getId()), null));

        Response response = productResource.updateProduct(request);

        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteProduct_Success() throws ResourceNotFoundException {
        Long productId = 1L;

        doNothing().when(productService).delete(productId);

        Response response = productResource.deleteProduct(productId);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteProduct_NotFound() throws ResourceNotFoundException {
        Long productId = 1L;

        doThrow(new ResourceNotFoundException(400, "Product with id " + productId + " not found",  null)).when(productService).delete(productId);

        Response response = productResource.deleteProduct(productId);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void testGetProductById_Success() throws ResourceNotFoundException {
        Long productId = 1L;
        ProductResponse productResponse = new ProductResponse();

        when(productService.findById(productId)).thenReturn(productResponse);

        Response response = productResource.getProductById(productId);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(productResponse, response.getEntity());
    }

    @Test
    void testGetProductById_NotFound() throws ResourceNotFoundException {
        Long productId = 1L;

        when(productService.findById(productId)).thenThrow(new ResourceNotFoundException(400, "Product with id " + productId + " not found", null));

        Response response = productResource.getProductById(productId);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void testGetAllProducts() {
        ProductResponse productResponse = new ProductResponse();
        when(productService.findAll()).thenReturn(Collections.singletonList(productResponse));

        Response response = productResource.getAllProducts();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity() instanceof List);
        assertEquals(1, ((List<?>) response.getEntity()).size());
    }
}
