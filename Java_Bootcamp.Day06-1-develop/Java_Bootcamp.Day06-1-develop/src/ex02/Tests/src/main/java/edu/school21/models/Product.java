package edu.school21.models;

import java.util.Objects;

public class Product {
    private final Long identifier;
    private String name;
    private Double price;

    public Product(Long identifier, String name, Double price) {
        this.identifier = identifier;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(identifier, product.identifier) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, name, price);
    }
}
