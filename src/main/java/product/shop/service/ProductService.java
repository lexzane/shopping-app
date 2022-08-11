package product.shop.service;

import java.util.List;
import product.shop.model.Product;

public interface ProductService {
    Product save(Product product);

    List<Product> findAllByNameNotContaining(String regex);
}
