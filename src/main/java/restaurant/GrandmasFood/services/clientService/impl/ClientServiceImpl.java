package restaurant.GrandmasFood.services.clientService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import restaurant.GrandmasFood.common.constant.responses.IResponse;
import restaurant.GrandmasFood.common.converter.client.ClientConverter;
import restaurant.GrandmasFood.common.domains.dto.ClientDTO;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.exception.client.ConflictClientException;
import restaurant.GrandmasFood.exception.client.InternalServerErrorException;
import restaurant.GrandmasFood.repository.ClientRepository.IClientRepository;
import restaurant.GrandmasFood.services.clientService.IClientService;
import restaurant.GrandmasFood.exception.client.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    IClientRepository iClientRepository;

    @Autowired
    ClientConverter clientConverter;

    public ClientDTO createClient(ClientDTO clientDTO) {
        ClientEntity clientEntity = clientConverter.convertClientDTOToClientEntity(clientDTO);
        Optional<ClientEntity> find = iClientRepository.findClientByDocument(clientEntity.getDocument());
        if (find.isPresent()) {
            throw new ConflictClientException(IResponse.CREATE_CLIENT_CONFLICT);
        }
        try {
            iClientRepository.save(clientEntity);
            return clientConverter.convertClientEntityToClientDTO(clientEntity);
        }
        catch (Exception e){
            throw new InternalServerErrorException(IResponse.INTERNAL_SERVER_ERROR);
        }
    }

    public ClientDTO getClient(String document){
        ClientEntity find = iClientRepository.findClientByDocument(document)
                .orElseThrow(()-> new NotFoundException("Document " + document + " not found"));
        return clientConverter.convertClientEntityToClientDTO(find);
    }

    public List<ClientDTO> getAllClients(String orderBy, String direction) {
        List<ClientEntity> clients;
        switch (orderBy) {
            case "NAME":
                clients = direction.equals("DESC") ? iClientRepository.findAllByOrderByFullNameDesc() :
                        iClientRepository.findAllByOrderByFullNameAsc();
                break;
            case "ADDRESS":
                clients = direction.equals("DESC") ? iClientRepository.findAllByOrderByAddressDesc() :
                        iClientRepository.findAllByOrderByAddressAsc();
                break;
            case "DOCUMENT":
            default:
                clients = direction.equals("DESC") ? iClientRepository.findAllByOrderByDocumentDesc() :
                        iClientRepository.findAllByOrderByDocumentAsc();
                break;
        }

        return clientConverter.convertClientEntityListToClientDTOList(clients);
    }


    public ClientDTO updateClient(String document, ClientDTO updatedClient) {
        ClientEntity existingClient = iClientRepository.findClientByDocument(document)
                .orElseThrow(() -> new NotFoundException("Document " + document + " not found"));
        if (updatedClient.equals(existingClient)) {
            throw new ConflictClientException(IResponse.UPDATE_CLIENT_CONFLICT);
        }
        existingClient.setCellphone(updatedClient.getCellphone());
        existingClient.setFullName(updatedClient.getFullName());
        existingClient.setEmail(updatedClient.getEmail());
        existingClient.setAddress(updatedClient.getAddress());
        try {
            ClientEntity savedClient = iClientRepository.save(existingClient);
            return clientConverter.convertClientEntityToClientDTO(savedClient);
        } catch (Exception e) {
            throw new InternalServerErrorException(IResponse.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteClient(String document){
        ClientEntity existingClient = iClientRepository.findClientByDocument(document).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client %s Not Found", document )));
        iClientRepository.delete(existingClient);
    }
}
