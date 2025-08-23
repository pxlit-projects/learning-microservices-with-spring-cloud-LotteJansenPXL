package be.pxl.services.service;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.Wishlist;
import be.pxl.services.domain.dto.ProductResponse;
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
                .products(wishlistRequest.getProducts())
                .build();
        wishlistRepository.save(wishlist);
    }

    @Override
    public void updateWishlist(WishlistRequest wishlistRequest) {
        Wishlist wishlist = wishlistRepository.findById(wishlistRequest.getId())
                .orElseThrow(() -> new RuntimeException("Wishlist not found: " + wishlistRequest.getId()));
        wishlist.setUserId(wishlistRequest.getUserId());
        wishlist.setProducts(wishlistRequest.getProducts());
        wishlistRepository.save(wishlist);
    }

    private WishlistResponse mapToWishlistResponse(Wishlist wishlist) {
        return WishlistResponse.builder()
                .id(wishlist.getId())
                .userId(wishlist.getUserId())
                .products(wishlist.getProducts().stream().map(this::mapToProductResponse).toList())
                .build();
    }
}
