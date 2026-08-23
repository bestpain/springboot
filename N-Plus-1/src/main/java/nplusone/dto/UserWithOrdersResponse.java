package nplusone.dto;

import java.util.List;

public record UserWithOrdersResponse(Integer userId, String name, List<OrderResponse> orders) {
}
