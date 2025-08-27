package be.pxl.services.service;

import be.pxl.services.client.ProductServiceClient;
import be.pxl.services.domain.Wishlist;
import be.pxl.services.domain.WishlistItem;
import be.pxl.services.domain.dto.WishlistItemRequest;
import be.pxl.services.domain.dto.WishlistItemResponse;
import be.pxl.services.domain.dto.WishlistRequest;
import be.pxl.services.domain.dto.WishlistResponse;
import be.pxl.services.repository.WishlistItemRepository;
import be.pxl.services.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService implements IWishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductServiceClient productServiceClient;
    private final WishlistItemRepository wishlistItemRepository;


    @Override
    public WishlistResponse getWishlistById(String userId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found: " + userId));
        for (WishlistItem i : wishlist.getItems()) {
            var product = productServiceClient.getProductById(i.getProductId());
            i.setProduct(product);
        }
        return mapToWishlistResponse(wishlist);
    }

    @Override
    public void createWishlist(String userId) {
        wishlistRepository.findByUserId(userId).ifPresentOrElse(
                w -> {}, // do nothing if exists
                () -> wishlistRepository.save(new Wishlist(null, userId, null))
        );
    }

    @Override
    public void addProductToWishlist(Long listId, WishlistItemRequest request) {
        Wishlist wishlist = wishlistRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found: " + listId));
        if (wishlist.getItems() == null) {
            wishlist.setItems(new ArrayList<>());
        }
        request.setWishlist(wishlist);
        request.setProductId(request.getProduct().getId());
        boolean exists = wishlist.getItems().stream()
                .anyMatch(item -> item.getProductId().equals(request.getProductId()));
        if (exists) {
            throw new RuntimeException("Product already exists in wishlist: " + request.getProductId());
        }

        WishlistItem newItem = WishlistItem.builder()
                .productId(request.getProductId())
                .product(request.getProduct())
                .wishlist(wishlist)
                .build();

        wishlist.getItems().add(newItem);
        wishlistRepository.save(wishlist);
    }

    @Override
    public void removeProductFromWishlist(Long listId, Long productId) {
        Wishlist wishlist = wishlistRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found: " + listId));

        if (wishlist.getItems() == null || wishlist.getItems().isEmpty()) {
            throw new RuntimeException("Wishlist is empty");
        }

        WishlistItem item = wishlist.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in wishlist: " + productId));

        wishlist.getItems().remove(item);
        wishlistRepository.save(wishlist);
        wishlistItemRepository.delete(item);
    }

    private WishlistResponse mapToWishlistResponse(Wishlist wishlist) {
        List<WishlistItemResponse> itemResponses = new ArrayList<>();
        if (wishlist.getItems() != null) {
            itemResponses = wishlist.getItems().stream()
                    .map(item -> WishlistItemResponse.builder()
                            .id(item.getId())
                            .productId(item.getProductId())
                            .product(item.getProduct())
                            .build())
                    .toList();
        }

        return WishlistResponse.builder()
                .id(wishlist.getId())
                .userId(wishlist.getUserId())
                .items(itemResponses)
                .build();
    }
}
