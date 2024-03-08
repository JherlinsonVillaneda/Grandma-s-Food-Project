package restaurant.GrandmasFood.exception.product;

public class NotProductFoundException extends RuntimeException{

    public NotProductFoundException(String message){
        super(message);
    }
}
