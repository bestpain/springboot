package nplusone.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nplusone.dto.*;
import nplusone.entity.User;
import nplusone.enums.OrderStatus;
import nplusone.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // this will cause infinite recursion since jackson will try to access the orders field on each user.
    public List<User> getAllUsersV1() {
        return userRepository.findAll();
    }

    // we are fixing it by transforming the user to user_response and omit the order field.
    public List<UserResponse> getAllUsersV2() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> new UserResponse(user.getId(), user.getName())).toList();
    }

    // if we want user along with it orders
    // approach 1 - use "DTO projection"
    // - Constructor expression creates one DTO per joined row.
    // - Does NOT automatically group multiple orders under one user.
    // - Avoids exposing the User/Order entity graph to JSON serialization.
    public List<UserOrderResponse> getUsersWithOrdersV1() {
        List<UserOrderResponse> userResponses = userRepository.findUsersOrders();
        userResponses.forEach(System.out::println);
        return userResponses;
    }

    // Approach 2 - JOIN FETCH
    // - Loads User entities with their orders association populated.
    // - Hibernate reconstructs the User -> Orders relationship.
    // - Avoids N+1 queries for the fetched association.
    // - Map entities to DTOs before returning the API response.
    @Transactional
    public List<UserWithOrdersResponse> getUsersWithOrdersV2() {
        List<User> usersWithOrders = userRepository.getUsersWithOrders();
        // JOIN FETCH returns managed entities, so they can be modified.
        // Normal DTO projection returns DTOs, not the managed entity graph.
        // user.getOrders() - can use the already-loaded collection instead of requiring Hibernate to go back to the database.
        usersWithOrders.stream().forEach(user -> user.getOrders().forEach(order -> order.setStatus(OrderStatus.COMPLETED)));
        return usersWithOrders.stream().map(user -> new UserWithOrdersResponse(user.getId(), user.getName(),
                user.getOrders().stream().map(order -> new OrderResponse(order.getId(), order.getAmount(), order.getStatus(), order.getCreatedAt())).toList())).toList();
    }
}
