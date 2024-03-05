package restaurant.GrandmasFood.services.orderService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import restaurant.GrandmasFood.common.constant.responses.IResponse;
import restaurant.GrandmasFood.common.converter.order.OrderConverter;
import restaurant.GrandmasFood.common.domains.dto.OrderDTO;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.common.domains.entity.order.OrderEntity;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.repositories.ClientRepository.IClientRepository;
import restaurant.GrandmasFood.repositories.OrderRepository.IOrderRepository;
import restaurant.GrandmasFood.repositories.productRepository.IProductRepository;
import restaurant.GrandmasFood.services.orderService.IOrderService;

import java.time.LocalDateTime;
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

    @Override
    public OrderDTO createOrder(OrderDTO order) {

        Optional<ClientEntity> clientFound = clientRepository.findClientByDocument(order.getClientDocument());
        Optional<ProductEntity> productFound = productRepository.findProductByUuid(order.getProductUuid());

        clientFound.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, IResponse.CLIENT_NOT_FOUND));
        productFound.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, IResponse.GET_FAIL_PRODUCT_NOT_FOUND));

        double total = productFound.get().getPrice();
        double tax = 0.19 * total;

        order.setUuid(UUID.randomUUID().toString());
        order.setCreationDateTime(LocalDateTime.now());
        order.setSubTotal(total);
        order.setTax(tax);
        order.setGrandTotal(total + tax);
        order.setDelivered(false);
        order.setDeliveredDate(null);

        OrderEntity orderEntity = orderConverter.convertOrderDTOToOrderEntity(order);
        orderEntity.setClientDocument(clientFound.get());
        orderEntity.setProductUuid(productFound.get());
        orderRepository.save(orderEntity);
        return order;
    }

    @Override
    public OrderDTO updateStatus(String uuid, LocalDateTime timestamp) {
        Optional<OrderEntity> orderFound = orderRepository.findOrderByUuid(uuid);

        orderFound.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, IResponse.ORDER_NOT_FOUND));

        orderFound.get().setDelivered(true);
        orderFound.get().setDeliveredDate(timestamp);
        return orderConverter.convertOrderEntityToOrderDTO(orderFound.get());
    }
}