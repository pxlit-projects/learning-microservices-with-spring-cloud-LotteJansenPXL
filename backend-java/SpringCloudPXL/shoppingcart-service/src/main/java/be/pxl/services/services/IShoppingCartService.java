package be.pxl.services.services;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.ShoppingCart;
import be.pxl.services.domain.dto.ShoppingCartResponse;
import org.springframework.web.bind.annotation.PathVariable;

public interface IShoppingCartService {
    ShoppingCartResponse getShoppingCart(Long id);
    void addProduct(Long shoppingcartId, Product product);
    void removeProduct(Long shoppingcartId, Long productid);
}
