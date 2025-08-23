package be.pxl.services.domain.dto;

import be.pxl.services.domain.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponse {
    private Long id;
    private Long userId;
    private List<Product> products;
}
