package be.pxl.services.domain.dto;

import be.pxl.services.domain.Product;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItemResponse {
    private Long id;
    private Long productId;
    private Product product;
}
