package restaurant.GrandmasFood.services.clientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.repositories.ClientRepository.IClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    IClientRepository iClientRepository;


    @Override
    public List<ClientEntity> findAll() {
        return iClientRepository.findAll();
    }

    @Override
    public Optional<ClientEntity> findByDocument(String document) {
        return iClientRepository.findById(document);
    }

    @Override
    public void save(ClientEntity client) {
        iClientRepository.save(client);
    }

    @Override
    public void deleteByDocument(String document) {
        iClientRepository.deleteByDocument(document);
    }
}
