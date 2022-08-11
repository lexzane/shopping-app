package product.shop.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import product.shop.dto.ProductRequestDto;
import product.shop.dto.ProductResponseDto;
import product.shop.model.Product;
import product.shop.service.ProductService;
import product.shop.service.mapper.RequestDtoMapper;
import product.shop.service.mapper.ResponseDtoMapper;

@RestController
@RequestMapping("/shop")
public class ProductController {
    private final ProductService productService;
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;

    public ProductController(ProductService productService,
                             RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper,
                             ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper) {
        this.productService = productService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
    }

    @PostMapping("/products")
    public ProductResponseDto save(@RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productService.save(requestDtoMapper.mapToModel(requestDto));
        return responseDtoMapper.mapToDto(product);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> findAllByNameContainingRegex(@RequestParam String nameFilter) {
        return productService.findAllByNameNotContaining(nameFilter)
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
