package be.pxl.services.controller;

import be.pxl.services.domain.dto.CategoryRequest;
import be.pxl.services.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void CreateCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.CreateCategory(categoryRequest);
    }

    @GetMapping
    public ResponseEntity getAllCategories() {
        return new ResponseEntity(categoryService.getAllCategories(), HttpStatus.OK);
    }

}
