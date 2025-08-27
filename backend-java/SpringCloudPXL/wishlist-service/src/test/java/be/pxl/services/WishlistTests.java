package be.pxl.services;

import be.pxl.services.client.ProductServiceClient;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.Wishlist;
import be.pxl.services.domain.WishlistItem;
import be.pxl.services.domain.dto.WishlistItemRequest;
import be.pxl.services.repository.WishlistRepository;
import be.pxl.services.service.WishlistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Transactional
public class WishlistTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WishlistRepository wishlistRepository;
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
    void cleanDatabase() {
        wishlistRepository.deleteAll();
    }

    @Test
    void testCreateWishlist() throws Exception {
        mockMvc.perform(post("/api/wishlist/testUser"))
                .andExpect(status().isCreated());

        assertEquals(1, wishlistRepository.findAll().size());
    }

    @Test
    void testAddAndRemoveProductFromWishlist() throws Exception {
        // given
        Wishlist wishlist = Wishlist.builder()
                .userId("user1")
                .items(new ArrayList<>())
                .build();
        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        Product mockProduct = Product.builder().id(1L).name("Product1").price(100.0).build();
        when(productServiceClient.getProductById(1L)).thenReturn(mockProduct);

        WishlistItemRequest request = new WishlistItemRequest();
        request.setProduct(mockProduct);
        request.setProductId(mockProduct.getId());

        String requestJson = objectMapper.writeValueAsString(request);

        // when add product
        mockMvc.perform(put("/api/wishlist/" + savedWishlist.getId() + "/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        assertEquals(1, wishlistRepository.findById(savedWishlist.getId()).get().getItems().size());

        // when remove product
        mockMvc.perform(put("/api/wishlist/" + savedWishlist.getId() + "/removeProduct/" + mockProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(0, wishlistRepository.findById(savedWishlist.getId()).get().getItems().size());
    }

    @Test
    void testGetWishlistByUserId() throws Exception {
        Wishlist wishlist = Wishlist.builder()
                .userId("user2")
                .items(new ArrayList<>())
                .build();
        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        Product mockProduct = Product.builder().id(2L).name("Product2").price(200.0).build();
        WishlistItem item = WishlistItem.builder()
                .productId(mockProduct.getId())
                .wishlist(savedWishlist)
                .build();
        savedWishlist.getItems().add(item);
        wishlistRepository.save(savedWishlist);

        when(productServiceClient.getProductById(mockProduct.getId())).thenReturn(mockProduct);

        mockMvc.perform(get("/api/wishlist/" + savedWishlist.getUserId()))
                .andExpect(status().isOk());
    }
}

