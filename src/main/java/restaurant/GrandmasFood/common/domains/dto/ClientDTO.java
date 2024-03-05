package restaurant.GrandmasFood.common.domains.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    //Data Transfer Object
    //Entidad - Controlador
    private String document;
    private String fullName;
    private String email;
    private String cellphone;
    private String address;
}
