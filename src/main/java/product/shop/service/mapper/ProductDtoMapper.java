package product.shop.service.mapper;

import org.springframework.stereotype.Component;
import product.shop.dto.ProductRequestDto;
import product.shop.dto.ProductResponseDto;
import product.shop.model.Product;

@Component
public class ProductDtoMapper implements RequestDtoMapper<ProductRequestDto, Product>,
        ResponseDtoMapper<ProductResponseDto, Product> {
    @Override
    public Product mapToModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        return product;
    }

    @Override
    public ProductResponseDto mapToDto(Product product) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(product.getId());
        responseDto.setName(product.getName());
        responseDto.setDescription(product.getDescription());
        return responseDto;
    }
}
