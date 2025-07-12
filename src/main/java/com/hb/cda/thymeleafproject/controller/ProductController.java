package com.hb.cda.thymeleafproject.controller;

import com.hb.cda.thymeleafproject.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String ListProduct(Model model, @RequestParam(defaultValue = "1") int pageNumber){
        model.addAttribute("productPage", repo.findAll(PageRequest.of(pageNumber -1, 5)));
        model.addAttribute("pageNumber", pageNumber);
        return "list-product";
    }
}
