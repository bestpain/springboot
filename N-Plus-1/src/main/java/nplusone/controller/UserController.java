package nplusone.controller;

import lombok.RequiredArgsConstructor;
import nplusone.dto.UserOrderResponse;
import nplusone.dto.UserResponse;
import nplusone.dto.UserWithOrdersResponse;
import nplusone.entity.User;
import nplusone.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/v1")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsersV1());
    }

    @GetMapping("/users/v2")
    public ResponseEntity<List<UserResponse>> getAllUsersV2(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsersV2());
    }

    @GetMapping("/users/orders/v1")
    public ResponseEntity<List<UserOrderResponse>> getUsersOrdersV1(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersWithOrdersV1());
    }

    @GetMapping("/users/orders/v2")
    public ResponseEntity<List<UserWithOrdersResponse>> getUsersOrdersV2(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersWithOrdersV2());
    }
}
