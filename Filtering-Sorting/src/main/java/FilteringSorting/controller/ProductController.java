package FilteringSorting.controller;

import FilteringSorting.dto.PageResponse;
import FilteringSorting.dto.ProductResponse;
import FilteringSorting.dto.ProductSearchRequest;
import FilteringSorting.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/v1")
    public ResponseEntity<PageResponse<ProductResponse>> getProductsV1(

            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Page must be >= 0")
            int page,

            @RequestParam(defaultValue = "20")
            @Min(value = 1, message = "Size must be >= 1")
            @Max(value = 100, message = "Size must be <= 100")
            int size,

            @RequestParam(defaultValue = "id,asc")
            String sort,

            @RequestParam(required = false)
            String category,

            @RequestParam(required = false)
            @DecimalMin(value = "0.0", message = "Minimum price must be >= 0")
            BigDecimal minPrice,

            @RequestParam(required = false)
            @DecimalMin(value = "0.0", message = "Maximum price must be >= 0")
            BigDecimal maxPrice
    ) {

        ProductSearchRequest productSearchRequest = new ProductSearchRequest(page, size, sort,
                category, minPrice, maxPrice);

        return ResponseEntity.ok(
                productService.getProductsByCriteria(
                        productSearchRequest
                )
        );
    }


    @GetMapping("/products")
    public ResponseEntity<PageResponse<ProductResponse>> getProducts(
            @Valid @ModelAttribute ProductSearchRequest request
    ) {
        return ResponseEntity.ok(productService.getProductsByCriteria(request));
    }
}
