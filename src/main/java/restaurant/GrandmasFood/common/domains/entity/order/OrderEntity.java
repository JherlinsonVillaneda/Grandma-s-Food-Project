package restaurant.GrandmasFood.common.domains.entity.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "order_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 36)
    private String uuid;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column
    @NotBlank
    private Integer quantity;

    @Column(name = "extra_information", length = 511)
    @NotBlank
    private String extraInformation;

    @Column(name = "sub_total")
    private Double subTotal;

    @Column
    private Double tax;

    @Column(name = "grand_total")
    private Double grandTotal;

    @Column(columnDefinition = "boolean default false")
    private Boolean delivered;

    @Column(name = "delivered_date")
    private LocalDateTime deliveredDate;

    //relation
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_document", referencedColumnName = "document")
    private ClientEntity clientDocument;

    //relation
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_uuid", referencedColumnName = "uuid")
    private ProductEntity productUuid;

}
