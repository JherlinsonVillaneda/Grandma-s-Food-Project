package restaurant.GrandmasFood.common.constant.responses;

public interface IProductResponse {

    String CREATE_FAIL_NAME_EXISTS = "The product with the name provided already exists.";
    String NOT_DIFFERENT_VALUES = "There aren't different values.";
    String PRICE_NOT_VALID = "The price of the product is invalid.";
    String ERRORS_WITH_NAMES_OR_DESCRIPTION = "The name or the description of the product has errors.";
    String GET_FAIL_WITH_UUID = "The given UUID is not valid.";
    String GET_FAIL_PRODUCT_NOT_FOUND = "The product with the given UUID doesn't exists.";
    String GET_FAIL_WITH_PARAM = "The param can't be empty.";
    String GET_FAIL_WITH_PARAM_NAME = "The products with name '%s' doesn't exists.";
}
