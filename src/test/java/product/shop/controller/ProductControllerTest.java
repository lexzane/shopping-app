package product.shop.controller;

import java.util.Collections;
import java.util.List;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import product.shop.dto.ProductRequestDto;
import product.shop.model.Product;
import product.shop.service.ProductService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    public static final String REGEX = "^S.*$";
    public static final String COMPONENT_REGEX = "^.*[ugp].*$";
    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void shouldShowFilteredProducts() {
        Product lotion = new Product(1L, "Lotion", "Skin care");
        Product eauDeToilette = new Product(2L, "Eau De Toilette", "Perfumes");
        Product eauDeParfum = new Product(3L, "Eau De Parfum", "Perfumes");
        List<Product> mockProducts = List.of(lotion, eauDeToilette, eauDeParfum);
        Mockito.when(productService.findAllByNameNotContaining(REGEX)).thenReturn(mockProducts);

        RestAssuredMockMvc
                .given()
                .queryParam("nameFilter", REGEX)
                .when()
                .get("/shop/products")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(3))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].name", Matchers.equalTo("Lotion"))
                .body("[0].description", Matchers.equalTo("Skin care"))
                .body("[1].id", Matchers.equalTo(2))
                .body("[1].name", Matchers.equalTo("Eau De Toilette"))
                .body("[1].description", Matchers.equalTo("Perfumes"))
                .body("[2].id", Matchers.equalTo(3))
                .body("[2].name", Matchers.equalTo("Eau De Parfum"))
                .body("[2].description", Matchers.equalTo("Perfumes"));
    }

    @Test
    public void shouldShowFilteredProductsWithComponentRegex() {
        Product lotion = new Product(1L, "Lotion", "Skin care");
        Product showerGel = new Product(7L, "Shower Gel", "Skin care");
        List<Product> mockProducts = List.of(lotion, showerGel);
        Mockito.when(productService.findAllByNameNotContaining(COMPONENT_REGEX)).thenReturn(mockProducts);

        RestAssuredMockMvc
                .given()
                .queryParam("nameFilter", COMPONENT_REGEX)
                .when()
                .get("/shop/products")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(2))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].name", Matchers.equalTo("Lotion"))
                .body("[0].description", Matchers.equalTo("Skin care"))
                .body("[1].id", Matchers.equalTo(7))
                .body("[1].name", Matchers.equalTo("Shower Gel"))
                .body("[1].description", Matchers.equalTo("Skin care"));
    }

    @Test
    public void saveProduct() {
        Product product = new Product("Shampoo", "Hair care");
        Mockito.when(productService.save(product))
                .thenReturn(new Product(5L, "Shampoo", "Hair care"));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto(product.getName(), product.getDescription()))
                .when()
                .post("/shop/products")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(5))
                .body("name", Matchers.equalTo("Shampoo"))
                .body("description", Matchers.equalTo("Hair care"));
    }

    @Test
    public void saveProductWithNullName() {
        Product product = new Product(null, "Hair care");
        Mockito.when(productService.save(product))
                .thenReturn(new Product(5L, product.getName(), product.getDescription()));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto(product.getName(), product.getDescription()))
                .when()
                .post("/shop/products")
                .then()
                .statusCode(400);
    }

    @Test
    public void saveProductWithNullDescription() {
        Product product = new Product("Lotion", null);
        Mockito.when(productService.save(product))
                .thenReturn(new Product(5L, product.getName(), product.getDescription()));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto(product.getName(), product.getDescription()))
                .when()
                .post("/shop/products")
                .then()
                .statusCode(400);
    }

    @Test
    public void saveProductWithBlankName() {
        Product product = new Product(" ", "Hair care");
        Mockito.when(productService.save(product))
                .thenReturn(new Product(5L, product.getName(), product.getDescription()));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto(product.getName(), product.getDescription()))
                .when()
                .post("/shop/products")
                .then()
                .statusCode(400);
    }

    @Test
    public void saveProductWithBlankDescription() {
        Product product = new Product("Lotion", " ");
        Mockito.when(productService.save(product))
                .thenReturn(new Product(5L, product.getName(), product.getDescription()));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto(product.getName(), product.getDescription()))
                .when()
                .post("/shop/products")
                .then()
                .statusCode(400);
    }

    @Test
    public void saveProductWithNameHasMaxLength() {
        String validName = String.join("", Collections.nCopies(100, "a"));
        Product productWithNameOfMaxSize = new Product(validName, "Some description");
        Mockito.when(productService.save(productWithNameOfMaxSize))
                .thenReturn(new Product(5L, productWithNameOfMaxSize.getName(),
                        productWithNameOfMaxSize.getDescription()));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto(productWithNameOfMaxSize.getName(),
                        productWithNameOfMaxSize.getDescription()))
                .when()
                .post("/shop/products")
                .then()
                .statusCode(200);
    }

    @Test
    public void saveProductWithNameAboveMaxLength() {
        String notValidName = String.join("", Collections.nCopies(101, "a"));
        Product productWithNameAboveMaxSize = new Product(notValidName, "Some description");
        Mockito.when(productService.save(productWithNameAboveMaxSize))
                .thenReturn(new Product(5L, productWithNameAboveMaxSize.getName(),
                        productWithNameAboveMaxSize.getDescription()));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto(productWithNameAboveMaxSize.getName(),
                        productWithNameAboveMaxSize.getDescription()))
                .when()
                .post("/shop/products")
                .then()
                .statusCode(400);
    }

    @Test
    public void createProductWithDescriptionHasMaxLength() {
        String validDescription = String.join("", Collections.nCopies(1000, "a"));
        Product productWithDescriptionOfMaxSize = new Product("Some name", validDescription);
        Mockito.when(productService.save(productWithDescriptionOfMaxSize))
                .thenReturn(new Product(5L, productWithDescriptionOfMaxSize.getName(),
                        productWithDescriptionOfMaxSize.getDescription()));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto(productWithDescriptionOfMaxSize.getName(),
                        productWithDescriptionOfMaxSize.getDescription()))
                .when()
                .post("/shop/products")
                .then()
                .statusCode(200);
    }

    @Test
    public void createProductWithDescriptionAboveMaxLength() {
        String notValidDescription = String.join("", Collections.nCopies(1001, "a"));
        Product productWithDescriptionAboveMaxSize = new Product("Some name", notValidDescription);
        Mockito.when(productService.save(productWithDescriptionAboveMaxSize))
                .thenReturn(new Product(5L, productWithDescriptionAboveMaxSize.getName(),
                        productWithDescriptionAboveMaxSize.getDescription()));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto(productWithDescriptionAboveMaxSize.getName(),
                        productWithDescriptionAboveMaxSize.getDescription()))
                .when()
                .post("/shop/products")
                .then()
                .statusCode(400);
    }
}
