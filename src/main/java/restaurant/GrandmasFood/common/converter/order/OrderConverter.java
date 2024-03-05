package restaurant.GrandmasFood.common.converter.order;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import restaurant.GrandmasFood.common.domains.dto.OrderDTO;
import restaurant.GrandmasFood.common.domains.entity.order.OrderEntity;
import restaurant.GrandmasFood.config.MapperConfig;

@Component
@Log4j2
public class OrderConverter {
    public OrderEntity convertOrderDTOToOrderEntity(OrderDTO orderDTO){
        OrderEntity orderEntity = new OrderEntity();
        try {
            orderEntity = MapperConfig.modelMapper().map(orderDTO, OrderEntity.class);
        }
        catch (Exception e){
            log.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return orderEntity;
    }

    public OrderDTO convertOrderEntityToOrderDTO(OrderEntity orderEntity){
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = MapperConfig.modelMapper().map(orderEntity, OrderDTO.class);
        }
        catch (Exception e){
            log.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return orderDTO;
    }
}
