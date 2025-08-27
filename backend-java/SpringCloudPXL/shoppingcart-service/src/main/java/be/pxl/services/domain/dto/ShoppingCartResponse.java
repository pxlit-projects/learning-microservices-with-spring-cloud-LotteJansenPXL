package be.pxl.services.domain.dto;

import be.pxl.services.domain.ShoppingCartProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartResponse {
    private Long id;
    private String userId;
    private Double totalPrice;
    private Boolean checkedOut;
    private List<ShoppingcartProductResponse> products;
}
