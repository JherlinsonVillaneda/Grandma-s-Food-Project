package restaurant.GrandmasFood.common.domains.entity.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import restaurant.GrandmasFood.common.domains.entity.product.CategoryProduct;

import java.util.UUID;


@Entity(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 38)
    @NotEmpty
    private UUID uuid;

    @Column(length = 255)
    @NotEmpty
    private String name;

    @Enumerated(EnumType.STRING)
    @NotEmpty
    private CategoryProduct category;

    @Column
    @NotEmpty
    private String description;

    @Column
    @NotEmpty
    private Double price;

    @Column(columnDefinition = "boolean default true")
    @NotNull
    private Boolean availability;

}
