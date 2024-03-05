package restaurant.GrandmasFood.common.constant.responses;

public interface IResponse {

    // ERRORS FOR CLIENTS
    String CREATE_CLIENT_BAD_REQUEST = "Clients data invalid";

    String CLIENT_BAD_REQUEST = "Invalid document format";
    String INTERNAL_SERVER_ERROR = "Internal server error";
    String CREATE_CLIENT_CONFLICT = "A client with the same document already exists";

    String GET_CLIENT_SERVER_ERROR =  "An error occurred while retrieving the client";

    String CLIENT_NOT_FOUND = String.format("Client Not Found");

    String GET_CLIENT_BAD_REQUEST = "Invalid document";

    String ORDER_NOT_FOUND = "Order Not Found";

    // ERRORS FOR PRODUCTS
    String CREATE_FAIL_NAME_EXISTS = "The product with name '%s' already exists.";
    String NOT_DIFFERENT_VALUES = "There aren't different values.";
    String PRICE_NOT_VALID = "The price of the product is invalid.";
    String ERRORS_WITH_NAMES_OR_DESCRIPTION = "The name or the description of the product has errors.";
    String GET_FAIL_WITH_UUID = "The given UUID is not valid.";
    String GET_FAIL_PRODUCT_NOT_FOUND = "The product with the given UUID doesn't exists.";
    String GET_FAIL_WITH_PARAM = "The param can't be empty.";
    String GET_FAIL_WITH_PARAM_NAME = "The products with name '%s' doesn't exists.";
}
