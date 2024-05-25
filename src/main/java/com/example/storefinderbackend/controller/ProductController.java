package com.example.storefinderbackend.controller;

import com.example.storefinderbackend.entity.Product;
import com.example.storefinderbackend.entity.User;
import com.example.storefinderbackend.exception.AdminAccessRequiredException;
import com.example.storefinderbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public Product addProduct(@AuthenticationPrincipal User user, @RequestBody Product product) {
        if (user.getRole().equals("admin")) {
            return productService.addProduct(product);
        } else {
            throw new AdminAccessRequiredException();
        }
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
}
