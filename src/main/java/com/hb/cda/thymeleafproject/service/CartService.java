package com.hb.cda.thymeleafproject.service;

import com.hb.cda.thymeleafproject.dto.CartItemDTO;
import com.hb.cda.thymeleafproject.entity.Product;
import com.hb.cda.thymeleafproject.exception.ProductNotFoundException;
import com.hb.cda.thymeleafproject.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {

    private ProductRepository productRepository;
    private List<CartItemDTO> cartItemDTOS = new ArrayList<>();

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<CartItemDTO> getCartItem () {
        return new ArrayList<>(cartItemDTOS);
    }

    public void addProduct(String productId, int quantity) throws ProductNotFoundException {
        if(quantity <= 0) {
            return;
        }

        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isEmpty()) {
            throw new ProductNotFoundException("Produit non trouvé avec l'ID :" + productId);
        }

        Product product = productOptional.get();

        Boolean foundProduct = false;
        for (CartItemDTO item : cartItemDTOS){
            if(item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                foundProduct = true;
                break;
            }
        }
        if(!foundProduct) {
            cartItemDTOS.add(new CartItemDTO(product, quantity));
        }
        System.out.println("Produit ajouté au panier: " + product.getName() + ", Quantité: " + quantity);
    }

    public void removeQuantityProduct(String productId, int quantity) {
        cartItemDTOS.removeIf(item -> {
            if(item.getProduct().getId().equals(productId)) {
                int newQuantity = item.getQuantity() - quantity;
                if(newQuantity <= 0) {
                    System.out.println("produit retiré du panier: " + item.getProduct().getName());
                    return true;
                }else {
                    item.setQuantity(newQuantity);
                    System.out.println("Quantité mise à jour pour " + item.getProduct().getName() + " à " + newQuantity);
                    return false;
                }
            }
            return false;
        });
    }

    public void deleteProduct(String productId) {
        cartItemDTOS.removeIf(item -> item.getProduct().getId().equals(productId));
        System.out.println("Produit supprimé du panier: ID" + productId);
    }

    public double getTotalPrice() {
        double total = 0.0;

        for(CartItemDTO item : cartItemDTOS) {
            total+= item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    @Transactional
    public void validateCart() {
        if(cartItemDTOS.isEmpty()) {
            System.out.println("Le panier est vide");
            return;
        }

        for (CartItemDTO item : cartItemDTOS) {
            Product product = item.getProduct();
            int orderedQantity = item.getQuantity();

            Optional<Product> currentProductOptional = productRepository.findById(product.getId());
            if(currentProductOptional.isEmpty()) {
                throw new ProductNotFoundException("Erreur de validation: produit" + product.getName() + " non trouvé.");
            }

            Product currentProduct = currentProductOptional.get();
            if (currentProduct.getStock() < orderedQantity) {
                throw new IllegalStateException("Stock insuffisant pour le produit: " + currentProduct.getName() +
                        ". Demande: " + orderedQantity + ", Disponible: " + currentProduct.getStock());
            }

            //Gestion du stock
            currentProduct.setStock(currentProduct.getStock() - orderedQantity);
            productRepository.save(currentProduct);
            System.out.println("Stock de " + currentProduct.getName() + " mise à jour. Nouveau stock: " + currentProduct.getStock());
        }

        cleanCart();
        System.out.println("Panier validé et vidé.");
    }

    public void cleanCart(){
        cartItemDTOS.clear();
        System.out.println("Panier vidé.");
    }
}
