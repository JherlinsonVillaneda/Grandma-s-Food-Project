package restaurant.GrandmasFood.common.converter.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import restaurant.GrandmasFood.common.domains.dto.ClientDTO;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.config.MapperConfig;

import java.util.ArrayList;
import java.util.List;

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

    public ClientDTO convertClientEntityToClientDTO(ClientEntity clientEntity){
        ClientDTO clientDTO = new ClientDTO();
        try {
            clientDTO = MapperConfig.modelMapper().map(clientEntity, ClientDTO.class);
        }
        catch (Exception e){
            log.error(HttpStatus.CONFLICT);
        }
        return clientDTO;
    }

    public List<ClientDTO> convertClientEntityListToClientDTOList(List<ClientEntity> clientEntities) {
        List<ClientDTO> clientDTOs = new ArrayList<>();
        for (ClientEntity clientEntity : clientEntities) {
            clientDTOs.add(convertClientEntityToClientDTO(clientEntity));
        }
        return clientDTOs;
    }
}

