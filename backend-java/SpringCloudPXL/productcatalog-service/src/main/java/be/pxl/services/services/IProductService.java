package be.pxl.services.services;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.domain.dto.ProductResponse;

import java.util.List;

public interface IProductService {
    List<ProductResponse> getAllProducts();

    void addProduct(ProductRequest productRequest);

    void updateProduct(ProductRequest productRequest);
}
