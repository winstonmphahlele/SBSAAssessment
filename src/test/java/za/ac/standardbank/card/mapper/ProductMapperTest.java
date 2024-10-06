package za.ac.standardbank.card.mapper;

import org.junit.jupiter.api.Test;
import za.ac.standardbank.card.model.Product;
import za.ac.standardbank.generated.CreateProductRequest;
import za.ac.standardbank.generated.ProductResponse;
import za.ac.standardbank.generated.UpdateProductRequest;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {
    private final ProductMapper productMapper = ProductMapper.instance;

    @Test
    void mapCreateProductRequestToProduct() {
        CreateProductRequest createProduct = new CreateProductRequest();
        createProduct.setName("Credit Card");
        createProduct.setPrice(1000.00);
        Product product = productMapper.mapCreateProductRequestToProduct(createProduct);
        assertNotNull(product);
        assertEquals(createProduct.getName(), product.getName());
        assertEquals(createProduct.getPrice(), product.getPrice());
    }
    @Test
    void testMapProductToCreateProductRequest_ValidProduct() {

        Product product = new Product();
        product.setName("Credit Card");
        product.setPrice(29.99);

        CreateProductRequest result = productMapper.mapProductToCreateProductRequest(product);

        assertNotNull(result);
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getPrice(), result.getPrice());
    }



    @Test
    void mapUpdateProductRequestToProduct() {
        UpdateProductRequest createProduct = new UpdateProductRequest();
        createProduct.setId(1L);
        createProduct.setName("Credit Card");
        createProduct.setPrice(1000.00);
        Product product = productMapper.mapUpdateProductRequestToProduct(createProduct);
        assertEquals(createProduct.getName(), product.getName());
        assertEquals(createProduct.getPrice(), product.getPrice());
    }

    @Test
    void mapProductToProductResponse() {
        Product product = new Product();
        product.setName("Loan");
        product.setPrice(1050.00);
        ProductResponse productResponse = productMapper.mapProductToProductResponse(product);
        assertEquals(productResponse.getName(), product.getName());
        assertEquals(productResponse.getPrice(), product.getPrice());
    }

    @Test
    void testMapProductToUpdateProductRequest_ValidProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Sample Product");
        product.setPrice(99.99);

        UpdateProductRequest result = productMapper.mapProductToUpdateProductRequest(product);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getPrice(), result.getPrice());
    }

    @Test
    void testMapProductToUpdateProductRequest_NullProduct() {
        UpdateProductRequest result = productMapper.mapProductToUpdateProductRequest(null);
        assertNull(result);
    }

    @Test
    void testMapProductResponseToProduct_ValidProductResponse() {

        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(1L);
        productResponse.setName("Another Product");
        productResponse.setPrice(49.99);

        Product result = productMapper.mapProductResponseToProduct(productResponse);

        assertNotNull(result);
        assertEquals(productResponse.getId(), result.getId());
        assertEquals(productResponse.getName(), result.getName());
        assertEquals(productResponse.getPrice(), result.getPrice());
    }

    @Test
    void testMapProductResponseToProduct_NullProductResponse() {
        Product result = productMapper.mapProductResponseToProduct(null);
        assertNull(result);
    }
}