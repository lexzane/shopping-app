package product.shop.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import product.shop.model.Product;
import product.shop.repository.ProductRepository;
import product.shop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllByNameNotContaining(String regex) {
        return productRepository.findAll().stream()
                .parallel()
                .filter(e -> !e.getName().matches(regex))
                .collect(Collectors.toList());
    }
}
