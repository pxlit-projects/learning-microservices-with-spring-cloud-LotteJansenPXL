package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="wishlist")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL)
    private List<WishlistItem> items;
}
