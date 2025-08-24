package be.pxl.services.controller;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);


    @GetMapping
    public ResponseEntity getAllProducts() {
        log.info("Get all products");
        return new ResponseEntity(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest product) {
        log.info("Create product: {}", product);
        productService.CreateProduct(product);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void updateProduct(@RequestBody ProductRequest productRequest) {
        log.info("Update product: {}", productRequest);
        productService.UpdateProduct(productRequest);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity getProductsByCategory(@PathVariable Long categoryId) {
        log.info("Get products by category: {}", categoryId);
        return new ResponseEntity(productService.getProductsByCategory(categoryId), HttpStatus.OK);
    }

    @PutMapping("/{productId}/category/{categoryId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void AddCategoryToProduct(@PathVariable Long productId, @PathVariable Long categoryId) {
        log.info("Add category to product: {}", productId);
        productService.AddProductToCategory(productId, categoryId);
    }



}
