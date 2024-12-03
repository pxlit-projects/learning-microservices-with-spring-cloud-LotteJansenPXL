package be.pxl.services.controller;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.ShoppingCart;
import be.pxl.services.domain.dto.ShoppingCartResponse;
import be.pxl.services.services.IShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shoppingcart")
public class ShoppingCartController {
    private final IShoppingCartService shoppingCartService;

    @GetMapping("/{shoppingcartId}")
    public ResponseEntity<ShoppingCartResponse> getShoppingCart(@PathVariable Long shoppingcartId) {
        return new ResponseEntity<>(shoppingCartService.getShoppingCart(shoppingcartId), HttpStatus.OK);
    }

    @PostMapping("/{shoppingcartId}/add")
    public void addProductToShoppingCart(@PathVariable Long shoppingcartId, @RequestBody Product product) {
        shoppingCartService.addProduct(shoppingcartId, product);
    }

    @PostMapping({"/{shoppingcartId}/{productId}/remove"})
    public void removeProductFromShoppingCart(@PathVariable Long shoppingcartId, @PathVariable Long productId) {
        shoppingCartService.removeProduct(shoppingcartId, productId);
    }


}
