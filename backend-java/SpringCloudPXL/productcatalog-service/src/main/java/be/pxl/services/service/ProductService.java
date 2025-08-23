package be.pxl.services.service;

import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.domain.dto.ProductResponse;
import be.pxl.services.repository.CategoryRepository;
import be.pxl.services.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements  IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
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
        productRepository.save(product);
    }

    @Override
    public void UpdateProduct(ProductRequest productRequest) {
        Product product = productRepository.findById(productRequest.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategories(productRequest.getCategories());
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategories_Id(categoryId);
        return products.stream().map(product -> mapToProductResponse(product)).toList();
    }

    @Override
    public void AddProductToCategory(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));

        product.getCategories().add(category);
        productRepository.save(product);
        category.getProducts().add(product);
        categoryRepository.save(category);
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
