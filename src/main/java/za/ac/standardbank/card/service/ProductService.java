package za.ac.standardbank.card.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import za.ac.standardbank.card.dto.ProductDto;
import za.ac.standardbank.card.exception.ResourceNotFoundException;
import za.ac.standardbank.card.mapper.ProductMapper;
import za.ac.standardbank.card.model.Product;
import za.ac.standardbank.card.repository.implementation.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ProductService {
    @Inject
    private ProductRepository productRepository;

    private static final ProductMapper PRODUCT_MAPPER = ProductMapper.instance;

    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream().map(p -> PRODUCT_MAPPER.mapProductToProductDto(p)).collect(Collectors.toList());
        return productDtos;
    }

    private Product findProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(400, "Product with id " + id + " not found"));
        return product;
    }

    public ProductDto findById(Long id) {
        Product product = findProductById(id);
        ProductDto productDto = PRODUCT_MAPPER.mapProductToProductDto(product);
        return productDto;
    }

    public ProductDto save(ProductDto productDto) {
        Product product = productRepository.save(PRODUCT_MAPPER.mapProductDtoToProduct(productDto));
        return PRODUCT_MAPPER.mapProductToProductDto(product);
    }

    public void update(ProductDto productDto) {
        Product product = productRepository.update(PRODUCT_MAPPER.mapProductDtoToProduct(productDto));
        productRepository.update(product);
    }

    public void delete(Long id) {
        Product productToDelete = findProductById(id);
        productRepository.delete(productToDelete);
    }


}
