package be.pxl.services.controller;

import be.pxl.services.domain.dto.WishlistItemRequest;
import be.pxl.services.domain.dto.WishlistRequest;
import be.pxl.services.service.IWishlistService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final IWishlistService wishlistService;
    private static final Logger log = LoggerFactory.getLogger(WishlistController.class);


    @GetMapping("/{userId}")
    public ResponseEntity getWishlistById(@PathVariable String userId) {
        return new ResponseEntity(wishlistService.getWishlistById(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createWishlist(@PathVariable String userId) {
        wishlistService.createWishlist(userId);
    }

    @PutMapping("/{listId}/addProduct")
    public void addProductToWishlist(@PathVariable Long listId, @RequestBody WishlistItemRequest product) {
        log.info("Adding product to wishlist: {}", product);
        wishlistService.addProductToWishlist(listId, product);
    }

    @PutMapping("/{listId}/removeProduct/{productId}")
    public void removeProductFromWishlist(@PathVariable Long listId, @PathVariable Long productId) {
        log.info("Removing product from wishlist: {}", productId);
        wishlistService.removeProductFromWishlist(listId, productId);
    }
}
