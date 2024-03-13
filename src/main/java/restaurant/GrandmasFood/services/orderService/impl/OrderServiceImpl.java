package restaurant.GrandmasFood.services.orderService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.GrandmasFood.common.constant.responses.IOrderResponse;
import restaurant.GrandmasFood.common.constant.responses.IProductResponse;
import restaurant.GrandmasFood.common.constant.responses.IClientResponse;
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
import restaurant.GrandmasFood.validator.order.OrderDtoValidator;

import java.time.LocalDateTime;
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
    @Autowired
    OrderDtoValidator orderDtoValidator;

    @Override
    public OrderDTO createOrder(OrderDTO order) {

        orderDtoValidator.validateOrderCreateDto(order);

        ClientEntity clientEntity = getClientEntityByDocument(order.getClientDocument());
        ProductEntity productEntity = getProductEntityByUuid(order.getProductUuid());
        OrderEntity orderEntity = buildOrderEntity(order, clientEntity, productEntity);

        orderRepository.save(orderEntity);

        return orderConverter.convertOrderEntityToOrderDTO(orderEntity);
    }

    @Override
    public OrderDTO updateOrderStatus(String uuid, String timestamp) {

        orderDtoValidator.validateUpdateOrderStatus(uuid, timestamp);

        OrderEntity orderFound = getOrderEntityByUuid(uuid);

        orderFound.setDelivered(true);
        orderFound.setDeliveredDate(timestamp);
        orderRepository.save(orderFound);

        return orderConverter.convertOrderEntityToOrderDTO(orderFound);
    }

    // Utility methods

    private OrderEntity getOrderEntityByUuid(String uuid) {
        return orderRepository.findOrderByUuid(uuid)
                .orElseThrow(() -> new OrderNotFoundException(IOrderResponse.ORDER_NOT_FOUND));
    }

    private ClientEntity getClientEntityByDocument(String clientUuid) {
        return clientRepository.findClientByDocument(clientUuid)
                .orElseThrow(() -> new NotFoundException(IClientResponse.CLIENT_NOT_FOUND));
    }

    private ProductEntity getProductEntityByUuid(String productUuid) {
        return productRepository.findProductByUuid(productUuid)
                .orElseThrow(() -> new NotProductFoundException(IProductResponse.GET_FAIL_PRODUCT_NOT_FOUND));
    }

    private OrderEntity buildOrderEntity(OrderDTO order, ClientEntity clientEntity, ProductEntity productEntity) {

        OrderEntity orderEntity = orderConverter.convertOrderDTOToOrderEntity(order);

        double total = productEntity.getPrice();
        double tax = 0.19 * total;

        orderEntity.setUuid(UUID.randomUUID().toString());
        orderEntity.setCreationDateTime(dateTimeConverter.formatDateTimeToIso8601(LocalDateTime.now()));
        orderEntity.setSubTotal(total);
        orderEntity.setTax(tax);
        orderEntity.setGrandTotal(total + tax);
        orderEntity.setDelivered(false);
        orderEntity.setDeliveredDate(null);
        orderEntity.setClientDocument(clientEntity);
        orderEntity.setProductUuid(productEntity);
        return orderEntity;
    }

}