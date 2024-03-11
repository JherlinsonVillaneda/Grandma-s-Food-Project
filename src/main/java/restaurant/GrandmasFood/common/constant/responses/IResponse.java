package restaurant.GrandmasFood.common.constant.responses;

public interface IResponse {

    String CREATE_CLIENT_BAD_REQUEST = "Clients data invalid";
    String CLIENT_BAD_REQUEST = "Invalid document format";
    String INTERNAL_SERVER_ERROR = "Internal server error";
    String CREATE_CLIENT_CONFLICT = "A client with the same document already exists";
    String GET_CLIENT_SERVER_ERROR =  "An error occurred while retrieving the client";
    String CLIENT_NOT_FOUND = "Client Not Found";
    String GET_CLIENT_BAD_REQUEST = "Invalid document";

}
