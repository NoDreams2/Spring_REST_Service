package com.example.Spring_web_application.controller;

import com.example.Spring_web_application.everythingYouNeed.description.Product;
import com.example.Spring_web_application.payload.UpdateProductPayload;
import com.example.Spring_web_application.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products/{productId:\\d+}")
public class ProductsController {

  private final ProductService productService;

  @ModelAttribute("product")
  public Product product(@PathVariable("productId") int productId) {
    return this.productService.findProduct(productId)
            .orElseThrow(() -> new NoSuchElementException("Ничего не нашлось"));
  }

  @GetMapping()
  public String getProduct() {
    return "catalogue/products/product";
  }

  @GetMapping("edit")
  public String getProductEditPage() {
    return "catalogue/products/edit";
  }

  @PostMapping("edit")
  public String updateProduct(@ModelAttribute("product") Product product, UpdateProductPayload payload) {
    this.productService.updateProduct(product.getId(), payload.title(), payload.details());
    return "redirect:/catalogue/products/%d".formatted(product.getId());
  }

  @PostMapping("delete")
  public String deleteProduct(@ModelAttribute("product") Product product) {
    this.productService.deleteProduct(product.getId());
    return "redirect:/catalogue/products/list";
  }

  @ExceptionHandler(NoSuchElementException.class)
  public String handleNoSuchElementException (NoSuchElementException exception, Model model,
                                              HttpServletResponse response) {
    response.setStatus(HttpStatus.NOT_FOUND.value());
    model.addAttribute("error", exception.getMessage());
    return "errors/404";
  }
}
