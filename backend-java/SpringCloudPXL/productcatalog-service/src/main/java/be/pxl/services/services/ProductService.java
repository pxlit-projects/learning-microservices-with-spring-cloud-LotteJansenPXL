package be.pxl.services.services;

import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.dto.CategoryRequest;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.domain.dto.ProductResponse;
import be.pxl.services.repository.ProductRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(p -> mapToProductResponse(p)).toList();
    }

    @Override
    public void addProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .build();
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElse(null);
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        productRepository.save(product);
    }

    @Override
    public void addCategory(Long productId, CategoryRequest categoryRequest) {
        Product product = productRepository.findById(productId).orElse(null);
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .products(categoryRequest.getProducts())
                .id(categoryRequest.getId())
                .build();
        product.addCategory(category);
    }

    @Override
    public void removeProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            productRepository.delete(product);
        }
    }

    @Override
    public void removeCategory(Long productId, CategoryRequest categoryRequest) {
        Product product = productRepository.findById(productId).orElse(null);
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .products(categoryRequest.getProducts())
                .id(categoryRequest.getId())
                .build();
        if (product != null) {
            List<Category> productCategories = new ArrayList<>();
            if(productCategories.contains(category)) {
                productCategories.remove(category);
                product.setCategories(productCategories);
            }
        }
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .build();
        return productResponse;
    }
}
