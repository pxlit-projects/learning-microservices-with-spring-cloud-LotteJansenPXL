package be.pxl.services;

import be.pxl.services.domain.Category;
import be.pxl.services.domain.dto.CategoryRequest;
import be.pxl.services.repository.CategoryRepository;
import be.pxl.services.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class CategoryTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:5.7.37");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @Test
    public void testCreateCategory() throws Exception {
        categoryRepository.deleteAll();
        CategoryRequest request = CategoryRequest.builder()
                .name("Test Category")
                .build();

        String requestString = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestString))
                .andExpect(status().isCreated());

        assertEquals(1, categoryRepository.findAll().size());
        Category savedCategory = categoryRepository.findAll().get(0);
        assertEquals("Test Category", savedCategory.getName());
    }

    @Test
    public void testGetAllCategories() throws Exception {
        // Seed database
        Category category1 = Category.builder().name("Cat1").build();
        Category category2 = Category.builder().name("Cat2").build();
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
