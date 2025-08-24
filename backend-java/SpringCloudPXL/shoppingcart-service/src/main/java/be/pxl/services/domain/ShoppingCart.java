package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="shoppingcart")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Double totalPrice;
    private Boolean checkedOut;
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    private List<ShoppingCartProduct> products;
}
