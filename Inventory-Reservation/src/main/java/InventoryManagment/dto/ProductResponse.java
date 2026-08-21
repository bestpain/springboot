package InventoryManagment.dto;

import org.springframework.http.HttpStatus;

public record ProductResponse(HttpStatus status , String message, int remainingQuantity) {
}
