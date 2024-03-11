package restaurant.GrandmasFood.validator.order;

import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;
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
            throw new OrderValidationException("Order instance mustn't be null");
        }

        if(!StringUtils.hasLength(StringUtils.trimAllWhitespace(orderDto.getClientDocument()))) {
            errorList.add("Client document can't be empty");
        }

        if(!StringUtils.hasLength(StringUtils.trimAllWhitespace(orderDto.getProductUuid()))) {
            errorList.add("Product UUID can't be empty");
        }

        if(orderDto.getQuantity() == null || orderDto.getQuantity() <= 0) {
            errorList.add("Quantity can't be less than 0");
        }

        if(orderDto.getExtraInformation() == null) {
            errorList.add("Extra information can't be null");
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
            errorList.add("UUID format is not valid ");
        }
        df.setLenient(false);

        try {
            df.parse(timestamp);
        } catch (ParseException e) {
            errorList.add("Timestamp format is not valid");
        }

        if (!errorList.isEmpty()) {
            throw new OrderValidationException(
                    String.format("Validation exception: %s", String.join(", ", errorList)));
        }
    }
}
