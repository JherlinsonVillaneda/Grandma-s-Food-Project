package restaurant.GrandmasFood.services.clientService;

import restaurant.GrandmasFood.common.domains.dto.ClientDTO;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;

public interface IClientService {
    ClientDTO createClient(ClientDTO clientDTO);
    ClientDTO getClient(String document, ClientDTO clientDTO);
    ClientDTO updateClient(String document, ClientDTO updatedClient);
     void deleteClient(String document);
}
