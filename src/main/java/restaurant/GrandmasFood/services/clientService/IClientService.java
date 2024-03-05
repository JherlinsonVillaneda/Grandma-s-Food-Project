package restaurant.GrandmasFood.services.clientService;

import restaurant.GrandmasFood.common.domains.dto.ClientDTO;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;

public interface IClientService {
    ClientEntity createClient(ClientDTO clientDTO);
    ClientEntity getClient(String document);
    ClientEntity updateClient(String document, ClientDTO updatedClient);
     void deleteClient(String document);
}
