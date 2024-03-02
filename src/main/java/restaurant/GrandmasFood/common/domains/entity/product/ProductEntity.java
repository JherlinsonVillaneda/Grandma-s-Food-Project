package restaurant.GrandmasFood.common.domains.entity.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Builder
@EqualsAndHashCode
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 36)
    @NotNull
    private String uuid;

    @Column(length = 255, unique = true)
    @NotNull
    @NotBlank(message = "The name is required.")
    @NotEmpty
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CategoryProduct category;

    @Column(length = 511)
    @NotNull
    private String description;

    @Column
    @NotNull
    private Double price;

    @Column(columnDefinition = "boolean default true", nullable = false)
    private Boolean available;

    @Column(columnDefinition = "boolean default true", nullable = false)
    private Boolean removed;
}
