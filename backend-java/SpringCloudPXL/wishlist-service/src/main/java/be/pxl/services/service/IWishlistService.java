package be.pxl.services.service;

import be.pxl.services.domain.dto.WishlistRequest;
import be.pxl.services.domain.dto.WishlistResponse;

public interface IWishlistService {
    WishlistResponse getWishlistById(Long listId);

    void createWishlist(WishlistRequest wishlistRequest);

    void updateWishlist(WishlistRequest wishlistRequest);
}
