package restaurant.GrandmasFood.common.domains.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Data
public class ClientDTO {
    private String document;
    private String fullName;
    private String email;
    private String cellphone;
    private String address;
}
