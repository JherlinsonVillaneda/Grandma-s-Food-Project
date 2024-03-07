package restaurant.GrandmasFood.common.domains.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ClientDTO {
    //Data Transfer Object
    //Entidad - Controlador
    private String document;
    private String fullName;
    private String email;
    private String cellphone;
    private String address;
}
