package restaurant.GrandmasFood.common.domains.entity.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.common.domains.entity.product.ProductEntity;

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

    @Column
    @NotBlank
    private Integer cant;

    @Column(name = "info_additional", length = 511)
    @NotBlank
    private String infoAdditional;

    @Column(name = "sub_total")
    @NotBlank
    private Double subTotal;

    @Column
    @NotBlank
    private Double iva;

    @Column
    @NotBlank
    private Double total;

    @Column(columnDefinition = "boolean default false")
    @NotNull
    private Boolean ordered;

    @Column(name = "date_ordered")
    @NotBlank
    private Date dateOrdered;

    @Column(name = "date_order")
    @NotBlank
    private Date dateOrder;

    //relation
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_document")
    private ClientEntity documentClient;

    //relation
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_uuid", referencedColumnName = "uuid")
    private ProductEntity uuidProduct;

}
