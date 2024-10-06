package za.ac.standardbank.card.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import za.ac.standardbank.card.model.Product;
import za.ac.standardbank.generated.CreateProductRequest;
import za.ac.standardbank.generated.ProductResponse;
import za.ac.standardbank.generated.UpdateProductRequest;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

    ProductMapper instance = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", ignore = true)
    Product mapCreateProductRequestToProduct (CreateProductRequest createProductRequest);
    CreateProductRequest mapProductToCreateProductRequest(Product Product);

    Product mapUpdateProductRequestToProduct (UpdateProductRequest updateProductRequest);
    UpdateProductRequest mapProductToUpdateProductRequest(Product Product);

    Product mapProductResponseToProduct (ProductResponse productResponse);
    ProductResponse mapProductToProductResponse(Product product);


}
