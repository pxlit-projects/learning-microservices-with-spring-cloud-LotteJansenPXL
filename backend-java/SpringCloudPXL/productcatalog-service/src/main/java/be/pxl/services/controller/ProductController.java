package be.pxl.services.controller;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public ResponseEntity getAllProducts() {
        return new ResponseEntity(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest product) {
        productService.CreateProduct(product);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void updateProduct(@RequestBody ProductRequest productRequest) {
        productService.UpdateProduct(productRequest);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity getProductsByCategory(@PathVariable Long categoryId) {
        return new ResponseEntity(productService.getProductsByCategory(categoryId), HttpStatus.OK);
    }

    @PutMapping("/{productId}/category/{categoryId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void AddCategoryToProduct(@PathVariable Long productId, @PathVariable Long categoryId) {
        productService.AddProductToCategory(productId, categoryId);
    }



}
