package be.pxl.services.services;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.ShoppingCart;
import be.pxl.services.domain.dto.ShoppingCartResponse;
import be.pxl.services.repository.ProductRepository;
import be.pxl.services.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService implements IShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
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
            }
        }
    }
}
