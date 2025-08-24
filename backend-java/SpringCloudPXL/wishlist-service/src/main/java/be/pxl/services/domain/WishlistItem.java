package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wishlistitem")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long productId;
    @Transient
    private Product product;
    @ManyToOne
    @JoinColumn(name="wishlist_id", nullable=false)
    private Wishlist wishlist;
}
