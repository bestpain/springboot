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

    // this is non-atomic and can cause lost update problem, all thread can read initial value before updating
    //and final value will be the last thread which writes
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

    @Transactional // atomic update opn
    // We need to protect the entire read-check-write operation, or make the database update conditional/atomic.
    public int decrementProductQuantityV2(UUID productId, int quantity) {
        if (quantity <= 0) throw new GenericError("Quantity must be greater than zero");
        int updated = productRepository.updateQuantity(productId, quantity);
        if (updated == 0) throw new GenericError("Product out of stock");
        return updated;
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
