package be.pxl.services.domain;

import jakarta.persistence.ManyToOne;

public class Product {
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    private ShoppingCart shoppingCart;
}
