package restaurant.GrandmasFood.webApi.orderController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.GrandmasFood.common.constant.endpoints.IOrderEndPoints;
import restaurant.GrandmasFood.common.domains.dto.OrderDTO;
import restaurant.GrandmasFood.services.orderService.IOrderService;
import java.time.LocalDateTime;

@RestController
@RequestMapping(IOrderEndPoints.ORDERS_BASE_URL)
public class OrderController {

    @Autowired
    IOrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO){
        OrderDTO orderSaved = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderSaved);
    }

    @PatchMapping(IOrderEndPoints.ORDERS_UPDATE_STATUS)
    public ResponseEntity<OrderDTO> updateStatus(@PathVariable("uuid") String orderUuid, @PathVariable("timestamp") LocalDateTime timestamp) {
        return ResponseEntity.ok(orderService.updateStatus(orderUuid, timestamp));
    }
}
