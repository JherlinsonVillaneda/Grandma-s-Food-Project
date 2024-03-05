package restaurant.GrandmasFood.common.converter.client;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import restaurant.GrandmasFood.common.domains.dto.ClientDTO;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.config.MapperConfig;

import javax.print.DocFlavor;

@Component
@Log4j2
public class ClientConverter {

    public ClientEntity convertClientDTOToClientEntity(ClientDTO clientDTO){
        ClientEntity clientEntity = new ClientEntity();
        try {
            clientEntity = MapperConfig.modelMapper().map(clientDTO, ClientEntity.class);
        }
        catch (Exception e){
            log.error(HttpStatus.CONFLICT);
        }
        return clientEntity;
    }

}
