package restaurant.GrandmasFood.services.orderService;

import restaurant.GrandmasFood.common.domains.dto.OrderDTO;

public interface IOrderService {

    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateStatus(String uuid, String timestamp);


}
