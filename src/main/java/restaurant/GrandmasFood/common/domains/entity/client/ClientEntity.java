package restaurant.GrandmasFood.common.domains.entity.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Entity(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@ToString
public class ClientEntity {

    @Id
    private String document;

    @Column(name = "full_name", length = 255)
    @NotEmpty
    private String fullName;

    @Column(unique = true, length = 255)
    @NotEmpty
    private String email;

    @Column(unique = true, length = 15)
    @NotEmpty
    private String cellphone;

    @Column(length = 500)
    @NotEmpty
    private String address;
}
