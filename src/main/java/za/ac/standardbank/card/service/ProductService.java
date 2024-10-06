package za.ac.standardbank.card.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import za.ac.standardbank.card.exception.RepositoryException;
import za.ac.standardbank.card.exception.ResourceAlreadyExistsException;
import za.ac.standardbank.card.exception.ResourceNotFoundException;
import za.ac.standardbank.card.exception.ResourceServiceException;
import za.ac.standardbank.card.mapper.ProductMapper;
import za.ac.standardbank.card.model.Product;
import za.ac.standardbank.card.repository.implementation.ProductRepository;
import za.ac.standardbank.generated.CreateProductRequest;
import za.ac.standardbank.generated.ProductResponse;
import za.ac.standardbank.generated.UpdateProductRequest;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ProductService {
    @Inject
    private ProductRepository productRepository;

    private static final ProductMapper PRODUCT_MAPPER = ProductMapper.instance;

    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = products.stream().map(p -> PRODUCT_MAPPER.mapProductToProductResponse(p)).collect(Collectors.toList());
        return productResponses;
    }

    private Product findProductById(Long id) throws ResourceNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(400, "Product with id " + id + " not found", null));
        return product;
    }

    public ProductResponse findById(Long id) throws ResourceNotFoundException {
        Product product = findProductById(id);
        ProductResponse productResponse = PRODUCT_MAPPER.mapProductToProductResponse(product);
        return productResponse;
    }

    public ProductResponse save(CreateProductRequest productRequest) throws ResourceAlreadyExistsException, ResourceServiceException {
        try {
            Product product = productRepository.save(PRODUCT_MAPPER.mapCreateProductRequestToProduct(productRequest));
            ProductResponse productResponse = PRODUCT_MAPPER.mapProductToProductResponse(product);
            return productResponse;
        } catch (EntityExistsException ex) {
            throw new ResourceAlreadyExistsException(50001, "Product already exits",  ex);
        } catch (RepositoryException ex) {
            throw new ResourceServiceException(50002, String.format("%s %s", "Error saving product : ", ex.getMessage()), ex);
        }
    }

    public ProductResponse update(UpdateProductRequest updateProductRequest) throws ResourceNotFoundException, ResourceServiceException {
        try {
            Product product = productRepository.update(PRODUCT_MAPPER.mapUpdateProductRequestToProduct(updateProductRequest));
            product = productRepository.update(product);
            ProductResponse productResponse = PRODUCT_MAPPER.mapProductToProductResponse(product);
            return productResponse;

        }catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(50003, String.format("Product with ID: %s does not exits", updateProductRequest.getId()),  ex);
        } catch (RepositoryException ex) {
            throw new ResourceServiceException(50003, String.format("%s %s", "Error Updating product : ", ex.getMessage()), ex);
        }
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Product productToDelete = findProductById(id);
        productRepository.delete(productToDelete);
    }


}
