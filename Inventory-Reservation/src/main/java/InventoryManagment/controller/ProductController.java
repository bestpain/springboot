package InventoryManagment.controller;

import InventoryManagment.dto.ProductResponse;
import InventoryManagment.dto.Response;
import InventoryManagment.entity.Product;
import InventoryManagment.error.GenericError;
import InventoryManagment.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/inventory/{productId}/reserve")
    public ResponseEntity<ProductResponse> reserve(@PathVariable UUID productId, @RequestParam(defaultValue = "1") int quantity){
        int stockCount = productService.decrementProductQuantity(productId, quantity);
        return ResponseEntity.ok(new ProductResponse(HttpStatus.ACCEPTED,"product booked", stockCount));
    }

    @PostMapping("/v2/inventory/{productId}/reserve")
    public ResponseEntity<Integer> reserveProductByIdV2(@PathVariable UUID productId, @RequestParam(defaultValue = "1") int quantity){
        return ResponseEntity.ok(productService.decrementProductQuantityV2(productId, quantity));
    }

    @GetMapping("product")
    public ResponseEntity<List<Product>> viewAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
