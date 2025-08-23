package be.pxl.services.service;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.ShoppingCart;
import be.pxl.services.domain.dto.ProductResponse;
import be.pxl.services.domain.dto.ShoppingCartRequest;
import be.pxl.services.domain.dto.ShoppingCartResponse;
import be.pxl.services.repository.ShoppingCartRepository;
import ch.qos.logback.core.hook.ShutdownHook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService implements IShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public List<ShoppingCartResponse> getAllShoppingCarts() {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll();
        return shoppingCarts.stream().map(this::mapToShoppingCartResponse).toList();
    }

    @Override
    public void createShoppingCart(ShoppingCartRequest shoppingCartRequest) {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(shoppingCartRequest.getUserId())
                .totalPrice(shoppingCartRequest.getTotalPrice())
                .checkedOut(shoppingCartRequest.getCheckedOut())
                .products(shoppingCartRequest.getProducts())
                .build();
        shoppingCartRepository.save(shoppingCart);

    }

    @Override
    public void addProductToCart(Long cartId, Product product) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found: " + cartId));
        cart.getProducts().add(product);
        cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
        shoppingCartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(Long cartId, Product product) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found: " + cartId));
        if(cart.getProducts().removeIf(p -> p.getId().equals(product.getId()))) {
            cart.setTotalPrice(cart.getTotalPrice() - product.getPrice());
            shoppingCartRepository.save(cart);
        } else {
            throw new RuntimeException("Product not found in cart: " + product.getId());
        }
    }

    @Override
    public ShoppingCartResponse getShoppingCartById(Long cartId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found: " + cartId));
        return mapToShoppingCartResponse(cart);
    }

    @Override
    public void checkoutCart(Long cartId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found: " + cartId));
        cart.setCheckedOut(true);
        shoppingCartRepository.save(cart);
    }



    private ShoppingCartResponse mapToShoppingCartResponse(ShoppingCart shoppingCart) {
        return ShoppingCartResponse.builder()
                .id(shoppingCart.getId())
                .userId(shoppingCart.getUserId())
                .totalPrice(shoppingCart.getTotalPrice())
                .checkedOut(shoppingCart.getCheckedOut())
                .products(shoppingCart.getProducts())
                .build();
    }


}
