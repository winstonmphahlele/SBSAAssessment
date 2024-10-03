package za.ac.standardbank.card.mapper;

import org.junit.jupiter.api.Test;
import za.ac.standardbank.card.dto.ProductDto;
import za.ac.standardbank.card.model.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperTest {
    private final ProductMapper productMapper = ProductMapper.instance;

    @Test
    void mapProductDtoToProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Credit Card");
        productDto.setPrice(1000.00);
        Product product = productMapper.mapProductDtoToProduct(productDto);
        assertEquals(productDto.getName(), product.getName());
        assertEquals(productDto.getPrice(), product.getPrice());
    }

    @Test
    void mapProductToProductDto() {
        Product product = new Product();
        product.setName("Loan");
        product.setPrice(1050.00);
        ProductDto productDto = productMapper.mapProductToProductDto(product);
        assertEquals(productDto.getName(), product.getName());
        assertEquals(productDto.getPrice(), product.getPrice());
    }
}