package restaurant.GrandmasFood.common.domains.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Data
public class ClientDTO {
    //Data Transfer Object
    //Entidad - Controlador
    private String document;
    private String fullName;
    private String email;
    private String cellphone;
    private String address;
}
