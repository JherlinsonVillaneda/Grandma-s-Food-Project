package restaurant.GrandmasFood.servicetest;

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
import restaurant.GrandmasFood.repository.ClientRepository.IClientRepository;
import restaurant.GrandmasFood.repository.OrderRepository.IOrderRepository;
import restaurant.GrandmasFood.repository.productRepository.IProductRepository;
import restaurant.GrandmasFood.services.orderService.impl.OrderServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateOrderSuccess() {

        // Given

        String creationDateTime = "2024-03-03T11:00:00Z";
        String clientDocument = "CC-123456";
        String productUuid = "123";

        Optional<ProductEntity> productFound = Optional.of(ProductEntity.builder()
                .name("Burger")
                .category(CategoryProduct.HAMBURGERS_AND_HOTDOGS)
                .description("Burger")
                .price(100D)
                .available(true)
                .build());

        Optional<ClientEntity> clientFound = Optional.of(ClientEntity.builder()
                .document("CC-123456")
                .fullName("Luisa Aldana")
                .email("luisa@gmail.com")
                .cellphone("123456789")
                .address("Calle 15")
                .build());

        OrderEntity orderEntity = OrderEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .creationDateTime(creationDateTime)
                .clientDocument(clientFound.get())
                .productUuid(productFound.get())
                .quantity(2)
                .extraInformation("")
                .subTotal(productFound.get().getPrice())
                .tax(productFound.get().getPrice() * 0.19)
                .grandTotal((productFound.get().getPrice() * 0.19) + productFound.get().getPrice())
                .delivered(false)
                .deliveredDate(null)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .uuid(UUID.randomUUID().toString())
                .creationDateTime(creationDateTime)
                .clientDocument(clientDocument)
                .productUuid(productUuid)
                .quantity(2)
                .extraInformation("")
                .subTotal(productFound.get().getPrice())
                .tax(productFound.get().getPrice() * 0.19)
                .grandTotal((productFound.get().getPrice() * 0.19) + productFound.get().getPrice())
                .delivered(false)
                .deliveredDate(null)
                .build();

        // When

        Mockito.when(productRepository.findProductByUuid(productUuid)).thenReturn(productFound);
        Mockito.when(clientRepository.findClientByDocument(clientDocument)).thenReturn(clientFound);
        Mockito.when(orderConverter.convertOrderDTOToOrderEntity(orderDTO)).thenReturn(orderEntity);
        Mockito.when(dateTimeConverter.formatDateTimeToIso8601(Mockito.any())).thenReturn(creationDateTime);
        Mockito.when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        Mockito.when(orderConverter.convertOrderEntityToOrderDTO(orderEntity)).thenReturn(orderDTO);

        OrderDTO orderSaved = orderService.createOrder(orderDTO);

        // Then

        assertThat(orderSaved).isNotNull();
        assertThat(UUID.fromString(orderSaved.getUuid())).isNotNull();
        assertThat(orderSaved.getCreationDateTime()).isEqualTo(creationDateTime);
        assertThat(orderSaved.getSubTotal()).isEqualTo(productFound.get().getPrice());
        assertThat(orderSaved.getTax()).isEqualTo(productFound.get().getPrice() * 0.19);
        assertThat(orderSaved.getGrandTotal()).isEqualTo(productFound.get().getPrice() + productFound.get().getPrice() * 0.19);
        assertThat(orderSaved.getDelivered()).isFalse();
        assertThat(orderSaved.getDeliveredDate()).isNull();
        assertThat(orderSaved.getClientDocument()).isNotNull();
        assertThat(orderSaved.getProductUuid()).isNotNull();

//        assertThat(orderSaved)
    }

    @Test
    void testCreateOrderClientException() {

        String clientDocument = "CC-123456";

        Optional<ClientEntity> clientFound = Optional.of(ClientEntity.builder()
                .document("CC-123456")
                .fullName("Luisa Aldana")
                .email("luisa@gmail.com")
                .cellphone("123456789")
                .address("Calle 15")
                .build());


    }
}
