package be.pxl.services.service;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.ShoppingCart;
import be.pxl.services.domain.ShoppingCartProduct;
import be.pxl.services.domain.dto.ShoppingCartRequest;
import be.pxl.services.domain.dto.ShoppingCartResponse;

import java.util.List;

public interface IShoppingCartService {
    List<ShoppingCartResponse> getAllShoppingCarts();

    void createShoppingCart(String userId);

    void addProductToCart(Long cartId, ShoppingCartProduct product);

    void removeProductFromCart(Long cartId, Long productId);

    ShoppingCartResponse getShoppingCartById(String userId);

    void checkoutCart(Long cartId);
}
