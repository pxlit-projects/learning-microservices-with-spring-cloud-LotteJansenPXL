package be.pxl.services.repository;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserId(String userId);
    boolean existsByUserId(String userId);
}
