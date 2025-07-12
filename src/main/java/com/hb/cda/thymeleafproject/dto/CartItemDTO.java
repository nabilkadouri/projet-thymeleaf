package com.hb.cda.thymeleafproject.dto;

import com.hb.cda.thymeleafproject.entity.Product;
import java.util.Objects;

public class CartItemDTO {
    private Product product;
    private int quantity;

    public CartItemDTO(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemDTO cartItem = (CartItemDTO) o;
        return Objects.equals(product.getId(), cartItem.product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(product.getId());
    }
}
