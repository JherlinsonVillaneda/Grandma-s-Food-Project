package restaurant.GrandmasFood.services.clientService;

import restaurant.GrandmasFood.common.domains.dto.ClientDTO;

public interface IClientService {
    ClientDTO createClient(ClientDTO clientDTO);
    ClientDTO getClient(String document);
    ClientDTO updateClient(String document, ClientDTO updatedClient);
     void deleteClient(String document);
}
