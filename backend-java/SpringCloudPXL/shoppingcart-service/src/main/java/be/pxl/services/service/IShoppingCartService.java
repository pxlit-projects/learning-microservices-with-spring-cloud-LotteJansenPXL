package be.pxl.services.service;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.ShoppingCart;
import be.pxl.services.domain.dto.ShoppingCartRequest;
import be.pxl.services.domain.dto.ShoppingCartResponse;

import java.util.List;

public interface IShoppingCartService {
    List<ShoppingCartResponse> getAllShoppingCarts();

    void createShoppingCart(ShoppingCartRequest shoppingCartRequest);

    void addProductToCart(Long cartId, Product product);

    void removeProductFromCart(Long cartId, Product product);

    ShoppingCartResponse getShoppingCartById(Long cartId);

    void checkoutCart(Long cartId);
}
