package za.ac.standardbank.card.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import za.ac.standardbank.card.dto.ProductDto;
import za.ac.standardbank.card.model.Product;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

    ProductMapper instance = Mappers.getMapper(ProductMapper.class);
    Product mapProductDtoToProduct(ProductDto productDto);
    ProductDto mapProductToProductDto(Product Product);
}
