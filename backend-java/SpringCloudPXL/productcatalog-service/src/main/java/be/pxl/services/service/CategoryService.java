package be.pxl.services.service;

import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.dto.CategoryRequest;
import be.pxl.services.domain.dto.CategoryResponse;
import be.pxl.services.domain.dto.ProductResponse;
import be.pxl.services.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public void CreateCategory(CategoryRequest categoryRequest) {
        categoryRepository.save(Category.builder()
                .name(categoryRequest.getName())
                .build());
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> mapToCategoryResponse(category)).toList();
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
