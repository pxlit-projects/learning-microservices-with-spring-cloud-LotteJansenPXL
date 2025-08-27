package be.pxl.services.service;

import be.pxl.services.domain.dto.WishlistItemRequest;
import be.pxl.services.domain.dto.WishlistRequest;
import be.pxl.services.domain.dto.WishlistResponse;

public interface IWishlistService {
    WishlistResponse getWishlistById(String userId);

    void createWishlist(String userId);

    void addProductToWishlist(Long listId, WishlistItemRequest product);

    void removeProductFromWishlist(Long listId, Long itemId);
}
