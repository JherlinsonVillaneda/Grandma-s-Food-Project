package restaurant.GrandmasFood.services.orderService;

import restaurant.GrandmasFood.common.domains.dto.OrderDTO;

import java.time.LocalDateTime;

public interface IOrderService {

    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateStatus(String uuid, LocalDateTime timestamp);
}
