package restaurant.GrandmasFood.exception.product;

public class BadRequestProductException extends RuntimeException{
    public BadRequestProductException(String message) {
        super(message);
    }
}
