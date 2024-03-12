package restaurant.GrandmasFood.common.constant.responses;

public interface IClientResponse {

    String INTERNAL_SERVER_ERROR = "Internal server error";
    String CREATE_CLIENT_CONFLICT = "A client with the same document already exists";
    String CLIENT_NOT_FOUND = "Client Not Found";
    String UPDATE_CLIENT_CONFLICT = "There is no different field in the Request";

}
