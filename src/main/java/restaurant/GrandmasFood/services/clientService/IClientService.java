package restaurant.GrandmasFood.services.clientService;

import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;

public interface IClientService {
    ClientEntity createClient(ClientEntity clientEntity);
    ClientEntity getClient(String document);
    ClientEntity updateClient(String document, ClientEntity updatedClient);
     void deleteClient(String document);
}
