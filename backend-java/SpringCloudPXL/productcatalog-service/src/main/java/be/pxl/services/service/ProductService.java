package be.pxl.services.service;

import be.pxl.services.client.NotificationClient;
import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.dto.NotificationRequest;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.domain.dto.ProductResponse;
import be.pxl.services.repository.CategoryRepository;
import be.pxl.services.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements  IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final NotificationClient notificationClient;
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final RabbitTemplate rabbitTemplate;

    @Override
    public List<ProductResponse> getAllProducts() {
        log.info("Get all products");
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> mapToProductResponse(product)).toList();
    }

    @Override
    public void CreateProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .categories(productRequest.getCategories())
                .build();
        log.info("Create product: {}", product);
        productRepository.save(product);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message("New product created: " + product.getName())
                .build();
        log.info("sending notification to notification client");
        notificationClient.sendNotification(notificationRequest);

        rabbitTemplate.convertAndSend("myQueue", "New product created: " + product.getName());
    }

    @Override
    public void UpdateProduct(ProductRequest productRequest) {
        Product product = productRepository.findById(productRequest.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategories(productRequest.getCategories());
        log.info("Update product: {}", product);
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        log.info("Get products by category: {}", categoryId);
        List<Product> products = productRepository.findByCategories_Id(categoryId);
        return products.stream().map(product -> mapToProductResponse(product)).toList();
    }

    @Override
    public void AddProductToCategory(Long productId, Long categoryId) {
        log.info("Add product to category: {}", categoryId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));

        product.getCategories().add(category);
        productRepository.save(product);
        log.info("Category added to product");
        category.getProducts().add(product);
        categoryRepository.save(category);
        log.info("product added to Category");
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .categories(product.getCategories())
                .build();
    }

}
