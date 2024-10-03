package za.ac.standardbank.card.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.standardbank.card.dto.ProductDto;
import za.ac.standardbank.card.exception.ResourceNotFoundException;
import za.ac.standardbank.card.model.Product;
import za.ac.standardbank.card.repository.implementation.ProductRepository;

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
    private ProductDto productDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setId(1L);
        product.setPrice(29.99);

        productDto = new ProductDto();
        productDto.setPrice(29.99);
    }

    @Test
    public void testFindAll() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        List<ProductDto> productDtos = productService.findAll();
        assertEquals(1, productDtos.size());
        assertEquals(product.getName(), productDtos.get(0).getName());
        assertEquals(product.getPrice(), productDtos.get(0).getPrice());
        verify(productRepository).findAll();
    }

    @Test
    public void testFindById_productExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        ProductDto foundproductDto = productService.findById(1L);
        assertEquals(product.getName(), foundproductDto.getName());
        assertEquals(product.getPrice(), foundproductDto.getPrice());
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
    public void testSave() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDto savedproductDto = productService.save(productDto);

        assertNotNull(savedproductDto, "Saved ProductDto should not be null");

        assertEquals(productDto.getName(), savedproductDto.getName());
        assertEquals(productDto.getPrice(), savedproductDto.getPrice());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void testUpdate() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        productService.update(productDto);
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
