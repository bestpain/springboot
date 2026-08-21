package InventoryManagment.service;

import InventoryManagment.entity.Product;
import InventoryManagment.error.GenericError;
import InventoryManagment.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public int decrementProductQuantity(UUID productId, int quantity) {
        if (quantity <= 0) throw new GenericError("Quantity must be greater than zero");

        Product product = productRepository
                .findByUuid(productId)
                .orElseThrow(() ->
                        new GenericError("Product not found with given id"));

        System.out.println(
                Thread.currentThread().getName() +
                        " READ stock = " +
                        product.getAvailableQuantity() + "Requested Quantity " + quantity
        );

        int stockLeft = product.getAvailableQuantity() - quantity;

        if (stockLeft >= 0) {
            product.setAvailableQuantity(stockLeft);
            productRepository.save(product);
        } else {
            throw new GenericError("Product out of stock");
        }

        return stockLeft;
    }

    @Transactional 
    public void decrementProductQuantityV2(UUID productId, int quantity) {
        if (quantity <= 0) throw new GenericError("Quantity must be greater than zero");
        int updated = productRepository.updateQuantity(productId, quantity);
        if (updated == 0) throw new GenericError("Product out of stock");
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
