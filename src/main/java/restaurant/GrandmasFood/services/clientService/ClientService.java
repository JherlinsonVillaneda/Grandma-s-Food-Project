package restaurant.GrandmasFood.services.clientService;

import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    //Basic Crud
    List<ClientEntity> findAll();

    Optional<ClientEntity> findByDocument(String document);

    void save(ClientEntity client);

    void deleteByDocument(String document);
}