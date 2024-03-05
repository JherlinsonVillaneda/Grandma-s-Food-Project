package restaurant.GrandmasFood.common.constant.responses;

public interface IResponse {
    String OPERATION_SUCCESS = "Operación exitosa";
    String OPERATION_FAIL = "Operación fallida";
    String CREATE_SUCCESS = "OK se creo correctamente";
    String CREATE_FAIL = "No se creo correctamente";
    String UPDATE_SUCCESS = "OK se actualizo correctamente";
    String UPDATE_FAIL = "No se actualizo correctamente";
    String DELETE_SUCCESS = "OK se elimino correctamente";
    String DELETE_FAIL = "No se elimino correctamente";
    String NOT_FOUND = "Operación fallida. no requerida";
    String DOCUMENT_FAIL = "No se pudo convertir el documento: ";
    String INTERNAL_SERVER_ERROR = "Internal Server error. Error inesperado del sistema";
    String INTERNAL_SERVER = "Error interno del servidor";

    // ERRORS FOR PRODUCTS
    String CREATE_FAIL_NAME_EXISTS = "The product with name '%s' already exists.";
    String NOT_DIFFERENT_VALUES = "There aren't different values.";
    String PRICE_NOT_VALID = "The price of the product is invalid.";
    String ERRORS_WITH_NAMES_OR_DESCRIPTION = "The name or the description of the product has errors.";
    String GET_FAIL_WITH_UUID = "The given UUID is not valid.";
    String GET_FAIL_PRODUCT_NOT_FOUND = "The product with the given UUID doesn't exists.";
}
