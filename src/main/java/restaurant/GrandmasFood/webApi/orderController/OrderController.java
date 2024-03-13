package restaurant.GrandmasFood.webApi.orderController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.GrandmasFood.common.constant.endpoints.IOrderEndPoints;
import restaurant.GrandmasFood.common.domains.dto.OrderDTO;
import restaurant.GrandmasFood.services.orderService.IOrderService;

@RestController
@RequestMapping(IOrderEndPoints.ORDERS_BASE_URL)
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    IOrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        LOGGER.info("Begin method createProduct");
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDTO));
    }

    @PatchMapping(IOrderEndPoints.ORDERS_UPDATE_STATUS)
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable("uuid") String orderUuid, @PathVariable("timestamp") String timestamp) {
        LOGGER.info("Begin method updateOrderStatus");
        return ResponseEntity.ok(orderService.updateOrderStatus(orderUuid, timestamp));
    }
}
