package restaurant.GrandmasFood.common.constant.endpoints;

public interface IOrderEndPoints {
    String ORDERS_BASE_URL = "/orders";
    String ORDERS_UPDATE_STATUS = "/{uuid}/delivered/{timestamp}";
}
