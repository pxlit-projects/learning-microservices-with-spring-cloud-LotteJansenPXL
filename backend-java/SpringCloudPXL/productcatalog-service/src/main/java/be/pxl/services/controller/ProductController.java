package be.pxl.services.controller;

import be.pxl.services.domain.dto.CategoryRequest;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.domain.dto.ProductResponse;
import be.pxl.services.services.IProductService;
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
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return new ResponseEntity(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody ProductRequest productRequest) {
        productService.addProduct(productRequest);
    }

    @PostMapping("/{productId}/removeproduct")
    @ResponseStatus(HttpStatus.OK)
    public void removeProduct(@PathVariable Long productId) {
        productService.removeProduct(productId);
    }

    @PutMapping("/{productId}/editproduct")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable Long productId, @RequestBody ProductRequest productRequest) {
        productService.updateProduct(productId, productRequest);
    }

//    @PutMapping("/{productId}/addcategory")
//    @ResponseStatus(HttpStatus.OK)
//    public void addCategory(@PathVariable Long productId, @RequestBody CategoryRequest categoryRequest) {
//        productService.addCategory(productId, categoryRequest);
//    }
//
//    @PutMapping("/{productId}/removecategory")
//    @ResponseStatus(HttpStatus.OK)
//    public void removeCategory(@PathVariable Long productId, @RequestBody CategoryRequest categoryRequest) {
//        productService.removeCategory(productId, categoryRequest);
//    }
}