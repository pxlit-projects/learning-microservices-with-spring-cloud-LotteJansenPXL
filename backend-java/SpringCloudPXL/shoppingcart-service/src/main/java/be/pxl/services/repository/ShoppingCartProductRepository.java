package be.pxl.services.repository;

import be.pxl.services.domain.ShoppingCartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartProductRepository extends JpaRepository<ShoppingCartProduct, Long> {
}
