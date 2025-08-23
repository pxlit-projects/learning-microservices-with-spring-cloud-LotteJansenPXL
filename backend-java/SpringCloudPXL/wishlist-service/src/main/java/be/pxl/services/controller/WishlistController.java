package be.pxl.services.controller;

import be.pxl.services.domain.dto.WishlistRequest;
import be.pxl.services.service.IWishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final IWishlistService wishlistService;

    @GetMapping(":{listId}")
    public ResponseEntity getWishlistById(@PathVariable Long listId) {
        return new ResponseEntity(wishlistService.getWishlistById(listId), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createWishlist(@RequestBody WishlistRequest wishlistRequest) {
        wishlistService.createWishlist(wishlistRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void updateWishlist(@RequestBody WishlistRequest wishlistRequest) {
        wishlistService.updateWishlist(wishlistRequest);
    }
}
