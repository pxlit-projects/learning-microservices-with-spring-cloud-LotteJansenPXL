package be.pxl.services.controller;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.ShoppingCart;
import be.pxl.services.domain.ShoppingCartProduct;
import be.pxl.services.domain.dto.ShoppingCartRequest;
import be.pxl.services.service.IShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shoppingcart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final IShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity getAllShoppingCarts() {
        return new ResponseEntity(shoppingCartService.getAllShoppingCarts(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createShoppingCart(@RequestBody ShoppingCartRequest shoppingCartRequest) {
        shoppingCartService.createShoppingCart(shoppingCartRequest);
    }

    @PutMapping("/addProduct/{cartId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProductToCart(@RequestBody ShoppingCartProduct product, @PathVariable Long cartId) {
        shoppingCartService.addProductToCart(cartId, product);
    }

    @PutMapping("/removeProduct/{cartId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void removeProductFromCart(@RequestBody ShoppingCartProduct product, @PathVariable Long cartId) {
        shoppingCartService.removeProductFromCart(cartId, product);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity getShoppingCartById(@PathVariable Long cartId) {
        return new ResponseEntity(shoppingCartService.getShoppingCartById(cartId), HttpStatus.OK);
    }

    @PutMapping("/checkout/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public void checkoutCart(@PathVariable Long cartId) {
        shoppingCartService.checkoutCart(cartId);
    }

    //todo: order cart

}
