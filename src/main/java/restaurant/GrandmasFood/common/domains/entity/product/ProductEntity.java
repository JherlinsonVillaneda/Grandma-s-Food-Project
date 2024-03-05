package restaurant.GrandmasFood.common.domains.entity.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


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

    @Column(unique = true, length = 36, nullable = false)
    @NotNull
    private String uuid;

    @Column(length = 255, unique = true, nullable = false)
    @NotNull
    @NotBlank(message = "The name is required.")
    @NotEmpty
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CategoryProduct category;

    @Column(length = 511, nullable = false)
    @NotNull
    private String description;

    @Column(nullable = false)
    @NotNull
    private Double price;

    @Column(columnDefinition = "boolean default true", nullable = false)
    private Boolean available;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean removed;
}
