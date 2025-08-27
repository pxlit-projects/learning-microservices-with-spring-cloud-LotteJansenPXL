package be.pxl.services.domain.dto;

import be.pxl.services.domain.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Category category;
}
