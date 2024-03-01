package restaurant.GrandmasFood.services.clientService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.repositories.ClientRepository.IClientRepository;
import restaurant.GrandmasFood.services.clientService.IClientService;

import java.util.Optional;


@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    IClientRepository iClientRepository;


    public ClientEntity createClient(ClientEntity clientEntity) {

        Optional<ClientEntity> find = iClientRepository.findClientByDocument(clientEntity.getDocument());
        if (find.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A client with the same document already exists");
        }
        return iClientRepository.save(clientEntity);
    }

    public ClientEntity getClient(String document){
        return iClientRepository.findClientByDocument(document).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client %s Not Found", document )));
    }

    public ClientEntity updateClient(String document, ClientEntity updatedClient){
        ClientEntity existingClient = iClientRepository.findClientByDocument(document).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client %s Not Found", document )));
        existingClient.setCellphone(updatedClient.getCellphone());
        existingClient.setFullName(updatedClient.getFullName());
        existingClient.setEmail(updatedClient.getEmail());
        existingClient.setAddress(updatedClient.getAddress());

        return iClientRepository.save(existingClient);
    }

    public void deleteClient(String document){
        ClientEntity existingClient = iClientRepository.findClientByDocument(document).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client %s Not Found", document )));
        iClientRepository.delete(existingClient);
    }
}
