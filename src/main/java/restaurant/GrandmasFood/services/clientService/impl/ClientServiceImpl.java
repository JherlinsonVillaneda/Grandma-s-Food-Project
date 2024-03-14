package restaurant.GrandmasFood.services.clientService.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.GrandmasFood.common.constant.responses.IClientResponse;
import restaurant.GrandmasFood.common.converter.client.ClientConverter;
import restaurant.GrandmasFood.common.domains.dto.ClientDTO;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.exception.client.ConflictClientException;
import restaurant.GrandmasFood.exception.client.InternalServerErrorException;
import restaurant.GrandmasFood.repository.ClientRepository.IClientRepository;
import restaurant.GrandmasFood.repository.OrderRepository.IOrderRepository;
import restaurant.GrandmasFood.services.clientService.IClientService;
import restaurant.GrandmasFood.exception.client.NotFoundException;
import restaurant.GrandmasFood.validator.client.ClientDtoValidator;

import java.util.List;
import java.util.Optional;


@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private IClientRepository iClientRepository;

    @Autowired
    private IOrderRepository iOrderRepository;

    @Autowired
     private ClientConverter clientConverter;

    @Autowired
    private ClientDtoValidator clientDtoValidator;

    public ClientDTO createClient(ClientDTO clientDTO) {
        clientDtoValidator.validateCreateClient(clientDTO);
        ClientEntity clientEntity = clientConverter.convertClientDTOToClientEntity(clientDTO);
        Optional<ClientEntity> find = iClientRepository.findClientByDocument(clientEntity.getDocument());
        if (find.isPresent()) {
            throw new ConflictClientException(IClientResponse.CREATE_CLIENT_CONFLICT);
        }
        try {
            clientEntity.setRemoved(false);
            iClientRepository.save(clientEntity);
            return clientConverter.convertClientEntityToClientDTO(clientEntity);
        } catch (Exception e) {
            throw new InternalServerErrorException(IClientResponse.INTERNAL_SERVER_ERROR);
        }
    }

    public ClientDTO getClient(String document) {
        clientDtoValidator.validateGetClient(document);
        ClientEntity find = iClientRepository.findClientByDocument(document)
                .orElseThrow(() -> new NotFoundException("Document " + document + " not found"));
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
        clientDtoValidator.validateUpdateClient(document, updatedClient, existingClient);
        existingClient.setCellphone(updatedClient.getCellphone());
        existingClient.setFullName(updatedClient.getFullName());
        existingClient.setEmail(updatedClient.getEmail());
        existingClient.setAddress(updatedClient.getAddress());
        try {
            ClientEntity savedClient = iClientRepository.save(existingClient);
            return clientConverter.convertClientEntityToClientDTO(savedClient);
        } catch (Exception e) {
            throw new InternalServerErrorException(IClientResponse.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteClient(String document) {
        clientDtoValidator.validateDeleteClient(document);
        ClientEntity existingClient = iClientRepository.findClientByDocument(document)
                .orElseThrow(() -> new NotFoundException("Document " + document + " not found"));

        iOrderRepository.deleteAllByClientDocument(existingClient);
        existingClient.setRemoved(true);
        iClientRepository.save(existingClient);
    }


}
