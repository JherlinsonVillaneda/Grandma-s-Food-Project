package restaurant.GrandmasFood.common.domains.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import restaurant.GrandmasFood.common.converter.product.CategoryProductDeserializer;
import restaurant.GrandmasFood.common.domains.entity.product.CategoryProduct;

@Getter
@Setter
@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String uuid;
    private String name;
    @JsonDeserialize(using = CategoryProductDeserializer.class)
    private CategoryProduct category;
    private String description;
    private Double price;
    private Boolean available;
}
