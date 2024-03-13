package restaurant.GrandmasFood.common.domains.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
    private boolean removed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return removed == clientDTO.removed && Objects.equals(document, clientDTO.document) && Objects.equals(fullName, clientDTO.fullName) && Objects.equals(email, clientDTO.email) && Objects.equals(cellphone, clientDTO.cellphone) && Objects.equals(address, clientDTO.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, fullName, email, cellphone, address, removed);
    }
}
