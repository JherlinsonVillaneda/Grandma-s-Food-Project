package restaurant.GrandmasFood.services.orderService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.GrandmasFood.common.constant.responses.IOrderResponse;
import restaurant.GrandmasFood.common.constant.responses.IProductResponse;
import restaurant.GrandmasFood.common.constant.responses.IResponse;
import restaurant.GrandmasFood.common.converter.date.DateTimeConverter;
import restaurant.GrandmasFood.common.converter.order.OrderConverter;
import restaurant.GrandmasFood.common.domains.dto.OrderDTO;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.common.domains.entity.order.OrderEntity;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.exception.client.NotFoundException;
import restaurant.GrandmasFood.exception.order.OrderNotFoundException;
import restaurant.GrandmasFood.exception.product.NotProductFoundException;
import restaurant.GrandmasFood.repository.ClientRepository.IClientRepository;
import restaurant.GrandmasFood.repository.OrderRepository.IOrderRepository;
import restaurant.GrandmasFood.repository.productRepository.IProductRepository;
import restaurant.GrandmasFood.services.orderService.IOrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    IOrderRepository orderRepository;
    @Autowired
    IProductRepository productRepository;
    @Autowired
    IClientRepository clientRepository;
    @Autowired
    OrderConverter orderConverter;
    @Autowired
    DateTimeConverter dateTimeConverter;

    @Override
    public OrderDTO createOrder(OrderDTO order) {

        Optional<ClientEntity> clientFound = clientRepository.findClientByDocument(order.getClientDocument());
        Optional<ProductEntity> productFound = productRepository.findProductByUuid(order.getProductUuid());
        OrderEntity orderEntity = orderConverter.convertOrderDTOToOrderEntity(order);

        clientFound.orElseThrow(() -> new NotFoundException(IResponse.CLIENT_NOT_FOUND));
        productFound.orElseThrow(() -> new NotProductFoundException(IProductResponse.GET_FAIL_PRODUCT_NOT_FOUND));

        double total = productFound.get().getPrice();
        double tax = 0.19 * total;

        orderEntity.setUuid(UUID.randomUUID().toString());
        orderEntity.setCreationDateTime(dateTimeConverter.formatDateTimeToIso8601(LocalDateTime.now()));
        orderEntity.setSubTotal(total);
        orderEntity.setTax(tax);
        orderEntity.setGrandTotal(total + tax);
        orderEntity.setDelivered(false);
        orderEntity.setDeliveredDate(null);
        orderEntity.setClientDocument(clientFound.get());
        orderEntity.setProductUuid(productFound.get());

        orderRepository.save(orderEntity);

        return orderConverter.convertOrderEntityToOrderDTO(orderEntity);
    }

    @Override
    public OrderDTO updateStatus(String uuid, String timestamp) {
        Optional<OrderEntity> orderFound = orderRepository.findOrderByUuid(uuid);

        orderFound.orElseThrow(() -> new OrderNotFoundException(IOrderResponse.ORDER_NOT_FOUND));

        orderFound.get().setDelivered(true);
        orderFound.get().setDeliveredDate(timestamp);
        return orderConverter.convertOrderEntityToOrderDTO(orderFound.get());
    }

    public void deleteOrderByProductUuid(String productUuid) {

        List<OrderEntity> orderList = orderRepository.findOrdersByProductUuid(productUuid);

        if(orderList.isEmpty()) {
            throw new OrderNotFoundException(IOrderResponse.ORDER_NOT_FOUND);
        }
        orderRepository.deleteAllByProductUuid(productUuid);
    }

}