package restaurant.GrandmasFood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import restaurant.GrandmasFood.common.converter.date.DateTimeConverter;
import restaurant.GrandmasFood.common.domains.dto.OrderDTO;
import restaurant.GrandmasFood.services.orderService.IOrderService;
import restaurant.GrandmasFood.webApi.orderController.OrderController;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    IOrderService orderService;
    @MockBean
    DateTimeConverter dateTimeConverter;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCreateOrderSuccessfully() throws Exception {

        //Given
        OrderDTO orderDTO = OrderDTO.builder()
                .clientDocument("CC-123456")
                .productUuid("123")
                .quantity(2)
                .extraInformation("")
                .build();

        OrderDTO orderDTOExpected = OrderDTO.builder()
                .uuid("1234")
                .creationDateTime("2024-03-03T11:00:00Z")
                .clientDocument("CC-123456")
                .productUuid("123")
                .quantity(2)
                .extraInformation("")
                .subTotal(15000D)
                .tax(15000D * 0.19)
                .grandTotal((15000D * 0.19) + 15000D)
                .delivered(false)
                .deliveredDate(null)
                .build();

        when(orderService.createOrder(Mockito.any(OrderDTO.class))).thenReturn(orderDTOExpected);

        // When
        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderDTO)))

        // Then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.clientDocument").value("CC-123456"))
                .andExpect(jsonPath("$.productUuid").value("123"))
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    void testUpdateStatusOrderSuccessfully() throws Exception {

        //Given
        String orderUuid = "123";
        String timestamp = "2024-03-03T11:00:00Z";

        OrderDTO orderDTOExpected = OrderDTO.builder()
                .delivered(true)
                .deliveredDate("2024-03-03T11:00:00Z")
                .build();

        when(orderService.updateOrderStatus(orderUuid, timestamp)).thenReturn(orderDTOExpected);

        // When
        mockMvc.perform(patch("/orders/{orderUuid}/delivered/{timestamp}", orderUuid, timestamp))

        // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.delivered").value(true))
                .andExpect(jsonPath("$.deliveredDate").value(timestamp));

    }
}
