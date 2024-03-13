package restaurant.GrandmasFood.common.domains.entity.order;

import jakarta.persistence.*;
import lombok.*;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;

@Entity(name = "order_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 36)
    private String uuid;

    @Column(name = "creation_date_time")
    private String creationDateTime;

    @Column
    private Integer quantity;

    @Column(name = "extra_information", length = 511)
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
    private String deliveredDate;

    //relation
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_document", referencedColumnName = "document")
    private ClientEntity clientDocument;

    //relation
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_uuid", referencedColumnName = "uuid")
    private ProductEntity productUuid;

}
