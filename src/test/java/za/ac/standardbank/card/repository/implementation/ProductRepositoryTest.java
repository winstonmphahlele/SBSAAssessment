package za.ac.standardbank.card.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.standardbank.card.exception.ResourceNotFoundException;
import za.ac.standardbank.card.model.Product;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ProductRepository  productRepository;

    private Product product;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        product =new Product();
        product.setId(1L);
        product.setName("test produtct");
        product.setPrice(29.99);
    }



    @Test
    public void testSave() {
        productRepository.save(product);
        verify(entityManager).persist(product);
        verifyNoMoreInteractions(entityManager);
    }

    @Test
    public void testFindById_ProductExists() {
        when(entityManager.find(Product.class, 1L)).thenReturn(product);
        Optional<Product> foundProduct = productRepository.findById(1L);
        assertTrue(foundProduct.isPresent());
        assertEquals(product.getName(), foundProduct.get().getName());
        assertEquals(product.getPrice(), foundProduct.get().getPrice());
        verify(entityManager).find(Product.class, 1L);
        verifyNoMoreInteractions(entityManager);
    }


    @Test
    public void testDelete() {
        productRepository.delete(product);
        verify(entityManager).remove(product);
        verifyNoMoreInteractions(entityManager);
    }

    @Test
    public void testUpdate() throws ResourceNotFoundException {
        when(entityManager.find(Product.class, 1L)).thenReturn(product);
        when(entityManager.merge(any(Product.class))).thenReturn(product);

        productRepository.save(product);
        product.setName("Updated Product");

        Product updatedProduct = productRepository.update(product);

        assertEquals(product.getName(), updatedProduct.getName());
        assertEquals(product.getPrice(), updatedProduct.getPrice());

        verify(entityManager).merge(product);

    }

    @Test
    public void testFindAll() {
        List<Product> products = Collections.singletonList(product);
        TypedQuery<Product> query = mock(TypedQuery.class);
        when(entityManager.createQuery("SELECT p FROM Product p", Product.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(products);

        List<Product> foundProducts = productRepository.findAll();
        assertEquals(1, foundProducts.size());
        assertEquals(product.getName(), foundProducts.get(0).getName());
        assertEquals(product.getPrice(), foundProducts.get(0).getPrice());
        verify(entityManager).createQuery("SELECT p FROM Product p", Product.class);
        verify(query).getResultList();
        verifyNoMoreInteractions(entityManager);
    }


}
