package restaurant.GrandmasFood.services.orderService.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import restaurant.GrandmasFood.common.converter.date.DateTimeConverter;
import restaurant.GrandmasFood.common.converter.order.OrderConverter;
import restaurant.GrandmasFood.common.domains.dto.OrderDTO;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.common.domains.entity.order.OrderEntity;
import restaurant.GrandmasFood.common.domains.entity.product.CategoryProduct;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;
import restaurant.GrandmasFood.exception.client.NotFoundException;
import restaurant.GrandmasFood.exception.order.OrderNotFoundException;
import restaurant.GrandmasFood.exception.product.NotProductFoundException;
import restaurant.GrandmasFood.repository.ClientRepository.IClientRepository;
import restaurant.GrandmasFood.repository.OrderRepository.IOrderRepository;
import restaurant.GrandmasFood.repository.productRepository.IProductRepository;
import restaurant.GrandmasFood.validator.order.OrderDtoValidator;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;
    @Mock
    IOrderRepository orderRepository;
    @Mock
    IClientRepository clientRepository;
    @Mock
    IProductRepository productRepository;
    @Mock
    OrderConverter orderConverter;
    @Mock
    DateTimeConverter dateTimeConverter;
    @Mock
    OrderDtoValidator orderDtoValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateOrderSuccessfully() {

        // Given
        ProductEntity productEntity = ProductEntity.builder()
                .uuid("123")
                .name("Burger")
                .category(CategoryProduct.HAMBURGERS_AND_HOTDOGS)
                .description("Burger")
                .price(100D)
                .available(true)
                .build();

        ClientEntity clientEntity = ClientEntity.builder()
                .document("CC-123456")
                .fullName("Luisa Aldana")
                .email("luisa@gmail.com")
                .cellphone("123456789")
                .address("Calle 15")
                .build();

        OrderEntity orderEntity = OrderEntity.builder()
                .uuid("1234")
                .creationDateTime("2024-03-03T11:00:00Z")
                .clientDocument(clientEntity)
                .productUuid(productEntity)
                .quantity(2)
                .extraInformation("")
                .subTotal(productEntity.getPrice())
                .tax(productEntity.getPrice() * 0.19)
                .grandTotal((productEntity.getPrice() * 0.19) + productEntity.getPrice())
                .delivered(false)
                .deliveredDate(null)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .uuid("1234")
                .creationDateTime("2024-03-03T11:00:00Z")
                .clientDocument("CC-123456")
                .productUuid("123")
                .quantity(2)
                .extraInformation("")
                .subTotal(productEntity.getPrice())
                .tax(productEntity.getPrice() * 0.19)
                .grandTotal((productEntity.getPrice() * 0.19) + productEntity.getPrice())
                .delivered(false)
                .deliveredDate(null)
                .build();

        // When

        when(clientRepository.findClientByDocument(Mockito.anyString())).thenReturn(Optional.of(clientEntity));
        when(productRepository.findProductByUuid(Mockito.anyString())).thenReturn(Optional.of(productEntity));
        when(orderConverter.convertOrderDTOToOrderEntity(orderDTO)).thenReturn(orderEntity);
        when(dateTimeConverter.formatDateTimeToIso8601(Mockito.any(LocalDateTime.class))).thenReturn("2024-03-03T11:00:00Z");
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderConverter.convertOrderEntityToOrderDTO(orderEntity)).thenReturn(orderDTO);

        OrderDTO orderSaved = orderService.createOrder(orderDTO);

        // Then

        verify(clientRepository, times(1)).findClientByDocument(Mockito.anyString());
        verify(productRepository, times(1)).findProductByUuid(Mockito.anyString());
        verify(orderConverter, times(1)).convertOrderDTOToOrderEntity(orderDTO);
        verify(dateTimeConverter, times(1)).formatDateTimeToIso8601(Mockito.any(LocalDateTime.class));
        verify(orderRepository, times(1)).save(orderEntity);
        verify(orderConverter, times(1)).convertOrderEntityToOrderDTO(orderEntity);

        verifyNoMoreInteractions(clientRepository, productRepository, orderConverter, dateTimeConverter);

        assertThat(orderSaved).isNotNull();
        assertThat(orderSaved.getCreationDateTime()).isEqualTo(orderEntity.getCreationDateTime());

        assertNotNull(orderSaved.getClientDocument());
        assertNotNull(orderSaved.getProductUuid());

        assertThat(orderSaved.getClientDocument()).isEqualTo(orderEntity.getClientDocument().getDocument());
        assertThat(orderSaved.getProductUuid()).isEqualTo(orderEntity.getProductUuid().getUuid());
        assertThat(orderSaved.getQuantity()).isEqualTo(orderEntity.getQuantity());
        assertThat(orderSaved.getExtraInformation()).isEqualTo(orderEntity.getExtraInformation());
        assertThat(orderSaved.getSubTotal()).isEqualTo(productEntity.getPrice());
        assertThat(orderSaved.getTax()).isEqualTo(productEntity.getPrice() * 0.19);
        assertThat(orderSaved.getGrandTotal()).isEqualTo(productEntity.getPrice() + productEntity.getPrice() * 0.19);

        assertFalse(orderSaved.getDelivered());
        assertNull(orderSaved.getDeliveredDate());
    }

    @Test
    void testUpdateOrderStatusSuccessfully() {

        // Given

        OrderEntity orderEntity = OrderEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .creationDateTime("2024-03-03T11:00:00Z")
                .clientDocument(ClientEntity.builder().build())
                .productUuid(ProductEntity.builder().build())
                .quantity(2)
                .extraInformation("")
                .subTotal(15000D)
                .tax(15000D * 0.19)
                .grandTotal(15000D * 0.19 + 15000D)
                .delivered(false)
                .deliveredDate(null)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .delivered(true)
                .deliveredDate("2024-03-03T11:00:00Z")
                .build();

        // When

        when(orderRepository.findOrderByUuid(Mockito.anyString())).thenReturn(Optional.of(orderEntity));
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderConverter.convertOrderEntityToOrderDTO(orderEntity)).thenReturn(orderDTO);

        OrderDTO orderUpdated = orderService.updateOrderStatus("123", "2024-03-03T11:00:00Z");

        // Then

        verify(orderRepository, times(1)).findOrderByUuid(Mockito.anyString());
        verify(orderRepository, times(1)).save(orderEntity);
        verify(orderConverter, times(1)).convertOrderEntityToOrderDTO(orderEntity);
        verifyNoMoreInteractions(orderRepository, orderConverter);

        assertThat(orderUpdated).isNotNull();
        assertTrue(orderUpdated.getDelivered());
        assertThat(orderUpdated.getDeliveredDate()).isEqualTo(orderEntity.getDeliveredDate());
    }

    @Test
    void testCreateOrderFailedByClientNotFoundException() {

        // Given
        OrderDTO orderDTO = OrderDTO.builder()
                .clientDocument("1234")
                .build();

        // When
        when(clientRepository.findClientByDocument(Mockito.anyString())).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> orderService.createOrder(orderDTO));
    }

    @Test
    void testCreateOrderFailedByProductNotFoundException() {

        // Given
        OrderDTO orderDTO = OrderDTO.builder()
                .clientDocument("4321")
                .productUuid("1234")
                .build();

        // When
        when(clientRepository.findClientByDocument(Mockito.anyString())).thenReturn(Optional.of(ClientEntity.builder().build()));
        when(productRepository.findProductByUuid(Mockito.anyString())).thenReturn(Optional.empty());

        // Then
        assertThrows(NotProductFoundException.class, () -> orderService.createOrder(orderDTO));
    }

    @Test
    void testUpdateStatusOrderFailedByOrderNotFoundException() {
        // Given
        String orderUuid = "123";
        String timestamp = "2024-03-03T11:00:00Z";

        // When
        when(orderRepository.findOrderByUuid(Mockito.anyString())).thenReturn(Optional.empty());

        // Then
        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrderStatus(orderUuid, timestamp));
    }
}