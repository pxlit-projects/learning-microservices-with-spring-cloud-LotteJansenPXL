package be.pxl.services.service;

import be.pxl.services.client.ProductServiceClient;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.ShoppingCart;
import be.pxl.services.domain.ShoppingCartProduct;
import be.pxl.services.domain.dto.ShoppingCartRequest;
import be.pxl.services.domain.dto.ShoppingCartResponse;
import be.pxl.services.domain.dto.ShoppingcartProductResponse;
import be.pxl.services.repository.ShoppingCartProductRepository;
import be.pxl.services.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService implements IShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductServiceClient productServiceClient;
    private final ShoppingCartProductRepository shoppingCartProductRepository;

    @Override
    public List<ShoppingCartResponse> getAllShoppingCarts() {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll();
        return shoppingCarts.stream().map(this::mapToShoppingCartResponse).toList();
    }

    @Override
    public void createShoppingCart(String userId) {
        boolean exists = shoppingCartRepository.existsByUserId(userId);
        if (exists) {
            return;
        }
        ShoppingCart shoppingCart = new ShoppingCart(null, userId, 0.0, false, new ArrayList<>());
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void addProductToCart(Long cartId, ShoppingCartProduct product) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found: " + cartId));
        product.setShoppingCart(cart);
        product.setProductId(product.getProduct().getId());
        cart.getProducts().add(product);
        cart.setTotalPrice(cart.getTotalPrice() + product.getProduct().getPrice());
        shoppingCartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(Long cartId, Long productId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found: " + cartId));

        ShoppingCartProduct cartProduct = cart.getProducts().stream()
                .filter(p -> p.getProductId().equals(productId)) // compare with Product ID
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart: " + productId));

        // Fetch latest product info from ProductService
        Product product = productServiceClient.getProductById(cartProduct.getProductId());
        cartProduct.setProduct(product);

        if (cart.getProducts().remove(cartProduct)) {
            cart.setTotalPrice(cart.getTotalPrice() - product.getPrice());
            shoppingCartRepository.save(cart);
        }

        // Also delete from the repository
        shoppingCartProductRepository.delete(cartProduct);
    }

    @Override
    public ShoppingCartResponse getShoppingCartById(String userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found: " + userId));
        var totalPrice = 0.0;
        for(ShoppingCartProduct p : cart.getProducts()) {
            var product = productServiceClient.getProductById(p.getProductId());
            p.setProduct(product);
            totalPrice += product.getPrice();
        }
        cart.setTotalPrice(totalPrice);
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
                .products(
                        shoppingCart.getProducts().stream()
                                .map(this::mapToShoppingCartProductResponse)
                                .toList()
                )
                .build();
    }
    private ShoppingcartProductResponse mapToShoppingCartProductResponse(ShoppingCartProduct product) {
        return ShoppingcartProductResponse.builder()
                .id(product.getId())
                .productId(product.getProductId())
                .product(product.getProduct())
                .build();
    }

}
