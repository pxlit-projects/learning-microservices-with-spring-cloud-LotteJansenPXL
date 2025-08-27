package be.pxl.services.controller;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.ShoppingCart;
import be.pxl.services.domain.ShoppingCartProduct;
import be.pxl.services.domain.dto.ShoppingCartRequest;
import be.pxl.services.service.IShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shoppingcart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final IShoppingCartService shoppingCartService;
    private static final Logger log = LoggerFactory.getLogger(ShoppingCartController.class);


    @GetMapping
    public ResponseEntity getAllShoppingCarts() {
        return new ResponseEntity(shoppingCartService.getAllShoppingCarts(), HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createShoppingCart(@PathVariable String userId) {
        log.info("Create shoppingCart for: {}", userId);
        shoppingCartService.createShoppingCart(userId);
    }

    @PutMapping("/{cartId}/addProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProductToCart(@RequestBody ShoppingCartProduct product, @PathVariable Long cartId) {
        log.info("Add product to cart: {}, and cartId: {}", product, cartId);
        shoppingCartService.addProductToCart(cartId, product);
    }

    @PutMapping("/{cartId}/removeProduct/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        log.info("Remove product from cart: {}", productId);
        shoppingCartService.removeProductFromCart(cartId, productId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity getShoppingCartByUserId(@PathVariable String userId) {
        log.info("Get shoppingCart by userId: {}", userId);
        return new ResponseEntity(shoppingCartService.getShoppingCartById(userId), HttpStatus.OK);
    }

    @PutMapping("/checkout/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public void checkoutCart(@PathVariable Long cartId) {
        shoppingCartService.checkoutCart(cartId);
    }

    //todo: order cart

}
