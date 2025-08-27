package be.pxl.services.service;

import be.pxl.services.domain.Product;
import be.pxl.services.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductUpdateListener {

    private final ShoppingCartRepository shoppingCartRepository;

    @RabbitListener(queues = "productUpdateQueue")
    public void handleProductUpdate(Product updatedProduct) {
        shoppingCartRepository.findAll().forEach(cart -> {
            cart.getProducts().forEach(item -> {
                if (item.getProductId().equals(updatedProduct.getId())) {
                    item.setProduct(updatedProduct);
                }
            });
        });
    }
}