package be.pxl.services;

import be.pxl.services.domain.Product;
import be.pxl.services.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= ProductCatalogServiceApplication.class)
@Testcontainers
@AutoConfigureMockMvc
public class ProductTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;
    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:5.7.37");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product product = Product.builder()
                .name("Test Product")
                .description("This is a test product")
                .price(9.99)
                .build();
        String productString = objectMapper.writeValueAsString(product);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productString))
                .andExpect(status().isCreated());

        assertEquals(1, productRepository.findAll().size());
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Product product = Product.builder()
                .name("Phone")
                .description("Smartphone")
                .price(599.99)
                .build();
        productRepository.save(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetProductById() throws Exception {
        Product product = Product.builder()
                .name("Laptop")
                .description("Gaming laptop")
                .price(1200.00)
                .build();
        Product savedProduct = productRepository.save(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdateProduct() throws Exception {
        Product product = Product.builder()
                .name("Tablet")
                .description("Android tablet")
                .price(250.00)
                .build();
        Product savedProduct = productRepository.save(product);

        savedProduct.setName("Updated Tablet");
        String updatedProductString = objectMapper.writeValueAsString(savedProduct);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/product/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductString))
                .andExpect(status().isOk());

        assertEquals("Updated Tablet", productRepository.findById(savedProduct.getId()).get().getName());
    }
}
