package be.pxl.services.controller;

import be.pxl.services.domain.dto.CategoryRequest;
import be.pxl.services.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void CreateCategory(@RequestBody CategoryRequest categoryRequest) {
        log.info("Adding category: {}", categoryRequest);
        categoryService.CreateCategory(categoryRequest);
    }

    @GetMapping
    public ResponseEntity getAllCategories() {
        log.info("Getting all categories");
        return new ResponseEntity(categoryService.getAllCategories(), HttpStatus.OK);
    }

}
