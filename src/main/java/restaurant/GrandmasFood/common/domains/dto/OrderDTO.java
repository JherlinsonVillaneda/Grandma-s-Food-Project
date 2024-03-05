package restaurant.GrandmasFood.common.domains.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private String uuid;
    private LocalDateTime creationDateTime;
    private String clientDocument;
    private String productUuid;
    private Integer quantity;
    private String extraInformation;
    private Double subTotal;
    private Double tax;
    private Double grandTotal;
    private Boolean delivered;
    private LocalDateTime deliveredDate;
}
