package restaurant.GrandmasFood.validator.order;

import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;
import restaurant.GrandmasFood.common.constant.responses.IOrderResponse;
import restaurant.GrandmasFood.common.domains.dto.OrderDTO;
import restaurant.GrandmasFood.exception.order.OrderValidationException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OrderDtoValidator {

    public void validateOrderCreateDto(OrderDTO orderDto) {

        final List<String> errorList = new ArrayList<>();

        if(orderDto == null) {
            throw new OrderValidationException(IOrderResponse.ORDERDTO_NOT_NULL);
        }

        if(!StringUtils.hasLength(StringUtils.trimAllWhitespace(orderDto.getClientDocument()))) {
            errorList.add("Client document can't be empty");
        }

        if(!StringUtils.hasLength(StringUtils.trimAllWhitespace(orderDto.getProductUuid()))) {
            errorList.add("Product UUID can't be empty");
        }

        if(orderDto.getQuantity() == null || orderDto.getQuantity() <= 0) {
            errorList.add(IOrderResponse.ORDER_QUANTITY_GREATER_THAN_ZERO);
        }

        if(orderDto.getExtraInformation() == null) {
            errorList.add(IOrderResponse.ORDER_EXTRA_INFORMATION_NOTNULL);
        }

        if (!errorList.isEmpty()) {
            throw new OrderValidationException(
                    String.format("Validation exception: %s", String.join(", ", errorList)));
        }
    }

    public void validateUpdateOrderStatus(String orderUuid, String timestamp) {

        final List<String> errorList = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        try {
            UUID uuid = UUID.fromString(orderUuid);
        } catch (IllegalArgumentException exception){
            errorList.add(IOrderResponse.ORDER_UUID_NOT_VALID);
        }
        df.setLenient(false);

        try {
            df.parse(timestamp);
        } catch (ParseException e) {
            errorList.add(IOrderResponse.ORDER_TIMESTAMP_NOT_VALID);
        }

        if (!errorList.isEmpty()) {
            throw new OrderValidationException(
                    String.format("Validation exception: %s", String.join(", ", errorList)));
        }
    }
}
