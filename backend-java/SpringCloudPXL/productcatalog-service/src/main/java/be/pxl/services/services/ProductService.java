package be.pxl.services.services;

import be.pxl.services.client.LogbookClient;
import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.dto.CategoryRequest;
import be.pxl.services.domain.dto.LogbookRequest;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.domain.dto.ProductResponse;
import be.pxl.services.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final LogbookClient logbookClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(p -> mapToProductResponse(p)).toList();
    }

    @Override
    public void addProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);

        LogbookRequest logbookRequest = LogbookRequest.builder()
                .message("Product added")
                .sender("product-service")
                .timestamp(LocalDateTime.now())
                .build();
        logbookClient.sendNotification(logbookRequest);
        rabbitTemplate.convertAndSend("messageQueue", productRequest);
        String message = "New product created: " + product.getName();
    }

    @Override
    public void updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElse(null);
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);

        LogbookRequest logbookRequest = LogbookRequest.builder()
                .message("Product updated")
                .sender("product-service")
                .timestamp(LocalDateTime.now())
                .build();
        logbookClient.sendNotification(logbookRequest);
    }

//    @Override
//    public void addCategory(Long productId, CategoryRequest categoryRequest) {
//        Product product = productRepository.findById(productId).orElse(null);
//        Category category = Category.builder()
//                .name(categoryRequest.getName())
//                .products(categoryRequest.getProducts())
//                .id(categoryRequest.getId())
//                .build();
//        product.addCategory(category);
//
//        LogbookRequest logbookRequest = LogbookRequest.builder()
//                .message("Added category to product")
//                .sender("product-service")
//                .timestamp(LocalDateTime.now())
//                .build();
//        logbookClient.sendNotification(logbookRequest);
//    }

    @Override
    public void removeProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            productRepository.delete(product);
            LogbookRequest logbookRequest = LogbookRequest.builder()
                    .message("Removed product")
                    .sender("product-service")
                    .timestamp(LocalDateTime.now())
                    .build();
            logbookClient.sendNotification(logbookRequest);
        }
    }

//    @Override
//    public void removeCategory(Long productId, CategoryRequest categoryRequest) {
//        Product product = productRepository.findById(productId).orElse(null);
//        Category category = Category.builder()
//                .name(categoryRequest.getName())
//                .products(categoryRequest.getProducts())
//                .id(categoryRequest.getId())
//                .build();
//        if (product != null) {
//            List<Category> productCategories = new ArrayList<>();
//            if(productCategories.contains(category)) {
//                productCategories.remove(category);
//                product.setCategories(productCategories);
//                LogbookRequest logbookRequest = LogbookRequest.builder()
//                        .message("Removed category from product")
//                        .sender("product-service")
//                        .timestamp(LocalDateTime.now())
//                        .build();
//                logbookClient.sendNotification(logbookRequest);
//            }
//        }
//    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
        return productResponse;
    }
}
