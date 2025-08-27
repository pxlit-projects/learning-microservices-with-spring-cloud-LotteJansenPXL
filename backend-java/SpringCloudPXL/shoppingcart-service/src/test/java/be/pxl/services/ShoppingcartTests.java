package be.pxl.services;

import be.pxl.services.client.ProductServiceClient;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.ShoppingCart;
import be.pxl.services.domain.ShoppingCartProduct;
import be.pxl.services.domain.dto.ShoppingCartResponse;
import be.pxl.services.domain.dto.ShoppingcartProductResponse;
import be.pxl.services.repository.ShoppingCartRepository;
import be.pxl.services.service.ShoppingCartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= ShoppingCartServiceApplication.class)
@Testcontainers
@AutoConfigureMockMvc
@Transactional
public class ShoppingcartTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:5.7.37");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @MockBean
    private ProductServiceClient productServiceClient;

    @BeforeEach
    void setup() {
        Product mockProduct = Product.builder().id(1L).name("Product1").price(100.0).build();
        when(productServiceClient.getProductById(1L)).thenReturn(mockProduct);
    }

    @Test
    public void testCreateShoppingCart() throws Exception {
        shoppingCartRepository.deleteAll();
        String userId = "testUser";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/shoppingcart/" + userId))
                .andExpect(status().isCreated());

        assertEquals(1, shoppingCartRepository.findAll().size());
        assertEquals(userId, shoppingCartRepository.findAll().get(0).getUserId());
    }

    @Test
    public void testGetAllShoppingCarts() throws Exception {
        ShoppingCart cart = ShoppingCart.builder()
                .userId("user1")
                .totalPrice(0.0)
                .checkedOut(false)
                .products(new ArrayList<>())
                .build();
        shoppingCartRepository.save(cart);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/shoppingcart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetShoppingCartByUserId() throws Exception {
        ShoppingCart cart = ShoppingCart.builder()
                .userId("user2")
                .totalPrice(0.0)
                .checkedOut(false)
                .products(new ArrayList<>())
                .build();
        shoppingCartRepository.save(cart);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/shoppingcart/" + cart.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddAndRemoveProductInCart() throws Exception {
        // given
        ShoppingCart cart = ShoppingCart.builder()
                .userId("user3")
                .totalPrice(0.0)
                .checkedOut(false)
                .products(new ArrayList<>())
                .build();
        ShoppingCart savedCart = shoppingCartRepository.save(cart);

        Product mockProduct = Product.builder().id(1L).name("Product1").price(100.0).build();
        when(productServiceClient.getProductById(1L)).thenReturn(mockProduct);

        ShoppingCartProduct cartProduct = ShoppingCartProduct.builder()
                .product(mockProduct)
                .productId(mockProduct.getId())
                .build();

        String productString = objectMapper.writeValueAsString(cartProduct);

        // when add product
        mockMvc.perform(MockMvcRequestBuilders.put("/api/shoppingcart/" + savedCart.getId() + "/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productString))
                .andExpect(status().isCreated());

        assertEquals(1, shoppingCartRepository.findById(savedCart.getId()).get().getProducts().size());

        // when remove product
        mockMvc.perform(MockMvcRequestBuilders.put("/api/shoppingcart/" + savedCart.getId() + "/removeProduct/" + mockProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        assertEquals(0, shoppingCartRepository.findById(savedCart.getId()).get().getProducts().size());
    }


    @Test
    public void testCheckoutCart() throws Exception {
        ShoppingCart cart = ShoppingCart.builder()
                .userId("user4")
                .totalPrice(0.0)
                .checkedOut(false)
                .products(new ArrayList<>())
                .build();
        ShoppingCart savedCart = shoppingCartRepository.save(cart);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/shoppingcart/checkout/" + savedCart.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(true, shoppingCartRepository.findById(savedCart.getId()).get().getCheckedOut());
    }
}
