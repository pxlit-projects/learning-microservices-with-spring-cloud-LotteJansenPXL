package be.pxl.services.repository;

import be.pxl.services.domain.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
}
