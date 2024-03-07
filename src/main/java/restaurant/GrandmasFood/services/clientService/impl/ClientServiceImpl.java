package restaurant.GrandmasFood.services.clientService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import restaurant.GrandmasFood.common.constant.responses.IResponse;
import restaurant.GrandmasFood.common.converter.client.ClientConverter;
import restaurant.GrandmasFood.common.domains.dto.ClientDTO;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.repository.ClientRepository.IClientRepository;
import restaurant.GrandmasFood.services.clientService.IClientService;
import restaurant.GrandmasFood.exception.client.NotFoundException;
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
            throw new ResponseStatusException(HttpStatus.CONFLICT, IResponse.CREATE_CLIENT_CONFLICT);
        }
        if (clientDTO.getFullName()==null || clientDTO.getFullName().isEmpty() || clientDTO.getDocument() == null ||clientDTO.getDocument().isEmpty() || clientDTO.getEmail() == null ||
        clientDTO.getEmail().isEmpty() || clientDTO.getAddress() == null || clientDTO.getAddress().isEmpty() || clientDTO.getCellphone() == null || clientDTO.getCellphone().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, IResponse.CREATE_CLIENT_BAD_REQUEST);
        }
        try {
            iClientRepository.save(clientEntity);
            return clientConverter.convertClientEntityToClientDTO(clientEntity);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, IResponse.INTERNAL_SERVER_ERROR, e);

        }
    }

    public ClientDTO getClient(String document, ClientDTO clientDTO){
        if (!document.matches("^CC-\\d+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, IResponse.CLIENT_BAD_REQUEST);
        }
        ClientEntity find = iClientRepository.findClientByDocument(document)
                .orElseThrow(()-> new NotFoundException("Document " + document + " not found"));
        return clientConverter.convertClientEntityToClientDTO(find);
    }
    public ClientDTO updateClient(String document, ClientDTO updatedClient) {
        ClientEntity existingClient = iClientRepository.findClientByDocument(document)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(IResponse.CLIENT_NOT_FOUND, document)));

        if (updatedClient.equals(existingClient)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        if (updatedClient.getFullName() == null || updatedClient.getFullName().trim().isEmpty() || updatedClient.getEmail() == null ||
                updatedClient.getEmail().trim().isEmpty() || updatedClient.getAddress() == null ||
                updatedClient.getAddress().trim().isEmpty() || updatedClient.getCellphone() == null ||
                updatedClient.getCellphone().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, IResponse.CREATE_CLIENT_BAD_REQUEST);
        }
        existingClient.setCellphone(updatedClient.getCellphone());
        existingClient.setFullName(updatedClient.getFullName());
        existingClient.setEmail(updatedClient.getEmail());
        existingClient.setAddress(updatedClient.getAddress());

        try {
            ClientEntity savedClient = iClientRepository.save(existingClient);
            return clientConverter.convertClientEntityToClientDTO(savedClient);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, IResponse.INTERNAL_SERVER_ERROR, e);
        }
    }

    public void deleteClient(String document){
        ClientEntity existingClient = iClientRepository.findClientByDocument(document).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client %s Not Found", document )));
        iClientRepository.delete(existingClient);
    }
}
