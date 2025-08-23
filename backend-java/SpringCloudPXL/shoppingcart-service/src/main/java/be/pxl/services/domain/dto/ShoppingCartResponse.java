package be.pxl.services.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartResponse {
    private Long id;
    private Long userId;
    private Double totalPrice;
    private Boolean checkedOut;
    private List<Product> products;
}
