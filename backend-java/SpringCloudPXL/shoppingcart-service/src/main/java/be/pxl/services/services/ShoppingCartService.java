package be.pxl.services.services;

import be.pxl.services.client.LogbookClient;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.ShoppingCart;
import be.pxl.services.domain.dto.LogbookRequest;
import be.pxl.services.domain.dto.ShoppingCartResponse;
import be.pxl.services.repository.ProductRepository;
import be.pxl.services.repository.ShoppingCartRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ShoppingCartService implements IShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final LogbookClient logbookClient;

    @Override
    public ShoppingCartResponse getShoppingCart(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElse(null);
        ShoppingCartResponse shoppingCartResponse = ShoppingCartResponse.builder()
                .customerId(shoppingCart.getCustomerId())
                .id(shoppingCart.getId())
                .products(shoppingCart.getProducts())
                .build();
        return shoppingCartResponse;
    }

    @Override
    public void addProduct(Long shoppingcartId, Product product) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingcartId).orElse(null);
        shoppingCart.addProductId(product);
        shoppingCartRepository.save(shoppingCart);
        LogbookRequest logbookRequest = LogbookRequest.builder()
                .message("Shoppingcart added")
                .sender("shoppingcart-service")
                .timestamp(LocalDateTime.now())
                .build();
        logbookClient.sendNotification(logbookRequest);
    }

    @Override
    public void removeProduct(Long shoppingcartId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingcartId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        if(shoppingCart != null) {
            List<Product> products = shoppingCart.getProducts();
            if(products.contains(product)) {
                products.remove(product);
                shoppingCartRepository.save(shoppingCart);
                LogbookRequest logbookRequest = LogbookRequest.builder()
                        .message("Removed product from shoppingcart")
                        .sender("shoppingcart-service")
                        .timestamp(LocalDateTime.now())
                        .build();
                logbookClient.sendNotification(logbookRequest);
            }
        }
    }
}
