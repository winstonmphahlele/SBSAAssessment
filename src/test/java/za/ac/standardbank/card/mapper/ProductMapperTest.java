package za.ac.standardbank.card.mapper;

import org.junit.jupiter.api.Test;
import za.ac.standardbank.card.model.Product;
import za.ac.standardbank.generated.CreateProductRequest;
import za.ac.standardbank.generated.ProductResponse;
import za.ac.standardbank.generated.UpdateProductRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperTest {
    private final ProductMapper productMapper = ProductMapper.instance;

    @Test
    void mapCreateProductRequestToProduct() {
        CreateProductRequest createProduct = new CreateProductRequest();
        createProduct.setName("Credit Card");
        createProduct.setPrice(1000.00);
        Product product = productMapper.mapCreateProductRequestToProduct(createProduct);
        assertEquals(createProduct.getName(), product.getName());
        assertEquals(createProduct.getPrice(), product.getPrice());
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
}