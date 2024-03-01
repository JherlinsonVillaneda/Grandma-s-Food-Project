package restaurant.GrandmasFood.common.domains.dto;

import lombok.*;
import restaurant.GrandmasFood.common.domains.entity.product.CategoryProduct;

@Getter
@Setter
@ToString
@Data
@Builder
public class ProductDTO {
    private String uuid;
    private String name;
    private CategoryProduct category;
    private String description;
    private Double price;
    private Boolean available;
}
