package be.pxl.services.services;

import be.pxl.services.domain.dto.CategoryRequest;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.domain.dto.ProductResponse;

import java.util.List;

public interface IProductService {
    List<ProductResponse> getAllProducts();

    void addProduct(ProductRequest productRequest);

    void updateProduct(Long id, ProductRequest productRequest);

    void addCategory(Long productId, CategoryRequest categoryRequest);

    void removeProduct(Long productId);

    void removeCategory(Long productId, CategoryRequest categoryRequest);
}
