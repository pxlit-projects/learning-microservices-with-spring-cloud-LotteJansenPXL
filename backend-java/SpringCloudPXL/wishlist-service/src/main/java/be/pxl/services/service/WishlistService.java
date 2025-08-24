package be.pxl.services.service;

import be.pxl.services.domain.Wishlist;
import be.pxl.services.domain.dto.WishlistRequest;
import be.pxl.services.domain.dto.WishlistResponse;
import be.pxl.services.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistService implements IWishlistService {
    private final WishlistRepository wishlistRepository;
    @Override
    public WishlistResponse getWishlistById(Long listId) {
        Wishlist wishlist = wishlistRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found: " + listId));
        return mapToWishlistResponse(wishlist);
    }

    @Override
    public void createWishlist(WishlistRequest wishlistRequest) {
        Wishlist wishlist = Wishlist.builder()
                .userId(wishlistRequest.getUserId())
                .items(wishlistRequest.getItems())
                .build();
        wishlistRepository.save(wishlist);
    }

    @Override
    public void updateWishlist(WishlistRequest wishlistRequest) {
        Wishlist wishlist = wishlistRepository.findById(wishlistRequest.getId())
                .orElseThrow(() -> new RuntimeException("Wishlist not found: " + wishlistRequest.getId()));
        wishlist.setUserId(wishlistRequest.getUserId());
        wishlist.setItems(wishlistRequest.getItems());
        wishlistRepository.save(wishlist);
    }

    private WishlistResponse mapToWishlistResponse(Wishlist wishlist) {
        return WishlistResponse.builder()
                .id(wishlist.getId())
                .userId(wishlist.getUserId())
                .items(wishlist.getItems())
                .build();
    }
}
