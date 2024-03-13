package restaurant.GrandmasFood.common.constant.responses;

public interface IOrderResponse {

    String ORDER_NOT_FOUND = "Order Not Found";

    // Validation responses
    String ORDERDTO_NOT_NULL = "Order instance mustn't be null";
    String ORDER_QUANTITY_GREATER_THAN_ZERO = "Quantity can't be less than 0";
    String ORDER_EXTRA_INFORMATION_NOTNULL = "Extra information can't be null";
    String ORDER_UUID_NOT_VALID = "UUID format is not valid ";
    String ORDER_TIMESTAMP_NOT_VALID = "Timestamp format is not valid";
}
