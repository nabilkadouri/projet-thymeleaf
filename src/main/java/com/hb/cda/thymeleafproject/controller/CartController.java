package com.hb.cda.thymeleafproject.controller;

import com.hb.cda.thymeleafproject.exception.ProductNotFoundException;
import com.hb.cda.thymeleafproject.repository.ProductRepository;
import com.hb.cda.thymeleafproject.service.CartService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;
    private ProductRepository repo;

    public CartController(CartService cartService, ProductRepository repo) {
        this.cartService = cartService;
        this.repo = repo;
    }

    @GetMapping
    public String viewCart (Model model) {
        model.addAttribute("cartItems", cartService.getCartItem());
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "cart-view";
    }

    @PostMapping("/add")
    public String addProductToCart(@Valid @RequestParam String productId, @RequestParam(value = "quantity", defaultValue = "1") int quantity, RedirectAttributes redirectAttributes) {
        try {
            cartService.addProduct(productId,quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Produit ajouté au panier !");
        } catch (ProductNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "La quantité doit être supérieur à 0");
        }
        return "redirect:/cart";
    }

    @PostMapping("/delete")
    private String deleteProductFromCart(@Valid @RequestParam String productId, RedirectAttributes redirectAttributes) {
            cartService.deleteProduct(productId);
            redirectAttributes.addFlashAttribute("infoMessage", "Produit retiré du panier");
            return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeProductFromCart(@Valid @RequestParam String productId, @RequestParam(value = "quantity", defaultValue = "1") int quantity, RedirectAttributes redirectAttributes) {
        cartService.removeQuantityProduct(productId, quantity);
        redirectAttributes.addFlashAttribute("infoMessage", "Quantité du produit mise à jour");
        return "redirect:/cart";
    }

    @PostMapping("/validate")
    private String validateCart(Model model,RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("confirmedItems", cartService.getCartItem());
            model.addAttribute("confirmedTotalPrice", cartService.getTotalPrice());
            cartService.validateCart();
            redirectAttributes.addFlashAttribute("successMessage", "Panier vadlité avec succès !");
            return "order-confirmation";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/clear")
    public String clearCart(RedirectAttributes redirectAttributes) {
        cartService.cleanCart();
        redirectAttributes.addFlashAttribute("infoMessage", "Le panier a été vidé.");
        return "redirect:/cart";
    }

}
