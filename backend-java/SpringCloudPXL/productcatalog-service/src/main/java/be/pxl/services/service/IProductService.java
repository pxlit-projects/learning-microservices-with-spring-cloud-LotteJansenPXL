package be.pxl.services.service;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.domain.dto.ProductResponse;

import java.util.List;

public interface IProductService {

    List<ProductResponse> getAllProducts();

    void CreateProduct(ProductRequest productRequest);

    void UpdateProduct(ProductRequest productRequest);

    List<ProductResponse> getProductsByCategory(Long categoryId);

    void AddProductToCategory(Long productId, Long categoryId);
}
