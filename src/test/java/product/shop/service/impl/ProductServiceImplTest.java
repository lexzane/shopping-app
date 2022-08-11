package product.shop.service.impl;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import product.shop.model.Product;
import product.shop.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    public static final String REGEX = "^S.*$";
    public static final String COMPONENT_REGEX = "^.*[ugp].*$";
    private static List<Product> products;
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;

    @BeforeAll
    static void beforeAll() {
        Product lotion = new Product(1L, "Lotion", "Skin care");
        Product eauDeToilette = new Product(2L, "Eau De Toilette", "Perfumes");
        Product eauDeParfum = new Product(3L, "Eau De Parfum", "Perfumes");
        Product shavingGel = new Product(4L, "Shaving Gel", "Shaving cosmetics");
        Product shampoo = new Product(5L, "Shampoo", "Hair care");
        Product soap = new Product(6L, "Soap", "Skin care");
        Product showerGel = new Product(7L, "Shower Gel", "Skin care");
        products = List.of(lotion, eauDeToilette, eauDeParfum, shavingGel, shampoo, soap, showerGel);
    }

    @Test
    public void shouldFilterValuesWithRegex() {
        Mockito.when(productRepository.findAll()).thenReturn(products);
        List<Product> actual = productService.findAllByNameNotContaining(REGEX);
        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals("Lotion", actual.get(0).getName());
        Assertions.assertEquals("Eau De Toilette", actual.get(1).getName());
        Assertions.assertEquals("Eau De Parfum", actual.get(2).getName());
    }

    @Test
    public void shouldFilterValuesWithComponentRegex() {
        Mockito.when(productRepository.findAll()).thenReturn(products);
        List<Product> actual = productService.findAllByNameNotContaining(COMPONENT_REGEX);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals("Lotion", actual.get(0).getName());
        Assertions.assertEquals("Shower Gel", actual.get(1).getName());
    }
}