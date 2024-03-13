package restaurant.GrandmasFood.exception.client;

public class ClientBadRequestException extends RuntimeException{
    public ClientBadRequestException(String message) {
        super(message);
    }
}
