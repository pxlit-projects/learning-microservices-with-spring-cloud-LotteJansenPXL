package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shoppingcartproduct")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long productId;
    @Transient
    private Product product;
    private int quantity;
    @ManyToOne
    @JoinColumn(name="shoppingcart_id")
    private ShoppingCart shoppingCart;
}
