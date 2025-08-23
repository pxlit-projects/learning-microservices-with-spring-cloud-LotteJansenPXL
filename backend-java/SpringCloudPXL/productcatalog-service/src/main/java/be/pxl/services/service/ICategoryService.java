package be.pxl.services.service;

import be.pxl.services.domain.dto.CategoryRequest;
import be.pxl.services.domain.dto.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    void CreateCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> getAllCategories();
}
