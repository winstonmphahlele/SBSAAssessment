package za.ac.standardbank.card.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.standardbank.card.exception.ResourceAlreadyExistsException;
import za.ac.standardbank.card.exception.ResourceNotFoundException;
import za.ac.standardbank.card.exception.ResourceServiceException;
import za.ac.standardbank.card.model.Product;
import za.ac.standardbank.card.repository.implementation.ProductRepository;
import za.ac.standardbank.generated.CreateProductRequest;
import za.ac.standardbank.generated.ProductResponse;
import za.ac.standardbank.generated.UpdateProductRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;
    private CreateProductRequest createProductRequest;
    private UpdateProductRequest updateProductRequest;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setId(1L);
        product.setPrice(29.99);

        createProductRequest = new CreateProductRequest();
        createProductRequest.setPrice(29.99);

        updateProductRequest = new UpdateProductRequest();
        product.setId(1L);
        updateProductRequest.setPrice(59.99);
    }

    @Test
    public void testFindAll() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        List<ProductResponse> foundProducts = productService.findAll();
        assertEquals(1, foundProducts.size());
        assertEquals(product.getName(), foundProducts.get(0).getName());
        assertEquals(product.getPrice(), foundProducts.get(0).getPrice());
        verify(productRepository).findAll();
    }

    @Test
    public void testFindById_productExists() throws ResourceNotFoundException {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        ProductResponse foundproduct = productService.findById(1L);
        assertEquals(product.getName(), foundproduct.getName());
        assertEquals(product.getPrice(), foundproduct.getPrice());
        verify(productRepository).findById(1L);
    }

    @Test
    public void testFindById_productDoesNotExist() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> productService.findById(2L));
        assertEquals("Product with id 2 not found", exception.getMessage());
        verify(productRepository).findById(2L);
    }

    @Test
    public void testSave() throws ResourceServiceException, ResourceAlreadyExistsException {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse savedproduct = productService.save(createProductRequest);

        assertNotNull(savedproduct, "Saved ProductDto should not be null");

        assertEquals(createProductRequest.getName(), savedproduct.getName());
        assertEquals(createProductRequest.getPrice(), savedproduct.getPrice());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void testUpdate() throws ResourceServiceException {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        ProductResponse productResponse = productService.update(updateProductRequest);
        verify(productRepository).update(any(Product.class));
    }

    @Test
    public void testDelete_productDoesNotExist() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> productService.delete(2L));
        assertEquals("Product with id 2 not found", exception.getMessage());
        verify(productRepository).findById(2L);
        verify(productRepository, never()).delete(any());
    }
}
