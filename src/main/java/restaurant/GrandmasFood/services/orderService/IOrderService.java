package restaurant.GrandmasFood.services.orderService;

import restaurant.GrandmasFood.common.domains.dto.OrderDTO;

public interface IOrderService {

    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrderStatus(String uuid, String timestamp);
}
