package FilteringSorting.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record ProductSearchRequest(

        @Min(value = 0, message = "Page must be >= 0")
        Integer page,

        @Min(value = 1, message = "Size must be >= 1")
        @Max(value = 100, message = "Size must be <= 100")
        Integer size,

        String sort,

        String category,

        @DecimalMin(value = "0.0", message = "Minimum price must be >= 0")
        BigDecimal minPrice,

        @DecimalMin(value = "0.0", message = "Maximum price must be >= 0")
        BigDecimal maxPrice
) {

    public ProductSearchRequest {
        if (page == null) page = 0;
        if (size == null) size = 20;
        if (sort == null || sort.isBlank()) sort = "id,asc";
    }
}
