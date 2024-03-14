package restaurant.GrandmasFood.validator.client;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import restaurant.GrandmasFood.common.constant.responses.IClientResponse;
import restaurant.GrandmasFood.common.domains.dto.ClientDTO;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.exception.client.ClientBadRequestException;
import restaurant.GrandmasFood.exception.client.ConflictClientException;
import restaurant.GrandmasFood.exception.client.NotFoundException;
import restaurant.GrandmasFood.repository.ClientRepository.IClientRepository;


import java.util.ArrayList;
import java.util.List;

@Component
public class ClientDtoValidator {

    IClientRepository iClientRepository;

    public void validateCreateClient(ClientDTO clientDTO){
        final List<String> errorList = new ArrayList<>();
        if(clientDTO == null) {
            throw new ClientBadRequestException("Client instance mustn't be null");
        }
        if(!StringUtils.hasLength(StringUtils.trimAllWhitespace(clientDTO.getDocument()))) {
            errorList.add("Client document can't be empty");
        }else if (!clientDTO.getDocument().matches("^(CC|CE|TI)-\\d+$")) {
            errorList.add("Invalid document format. It should start with 'CC-', 'CE-' or 'TI-' followed by numbers.");
        }
        if(!StringUtils.hasLength(StringUtils.trimAllWhitespace(clientDTO.getFullName()))) {
            errorList.add("Client name can't be empty");
        }

        if(!StringUtils.hasLength(StringUtils.trimAllWhitespace(clientDTO.getCellphone()))) {
            errorList.add("Client cellphone can't be empty");
        }

        if(!StringUtils.hasLength(StringUtils.trimAllWhitespace(clientDTO.getAddress()))) {
            errorList.add("Client address can't be empty");
        }
        if(!StringUtils.hasLength(StringUtils.trimAllWhitespace(clientDTO.getEmail()))) {
            errorList.add("Client email can't be empty");
        }
        if (!errorList.isEmpty()) {
            throw new ClientBadRequestException(
                    String.format("Invalid and incomplete client data: %s", String.join(", ", errorList)));
        }

    }
    public void validateGetClient(String document){
        if (!document.matches("^(CC|CE|TI)-\\d+$")) {
            throw new ClientBadRequestException("Invalid document format. It should start with 'CC-', 'CE-' or 'TI-' followed by numbers.");
        }
    }

    public void validateUpdateClient(String document, ClientDTO clientDTO) {
        final List<String> errorList = new ArrayList<>();
        if (clientDTO == null) {
            throw new ClientBadRequestException("Client instance mustn't be null");
        }
        if (!document.matches("^(CC|CE|TI)-\\d+$")) {
            errorList.add("Invalid document format. It should start with 'CC-', 'CE-' or 'TI-' followed by numbers.");
        }
        if (!StringUtils.hasLength(StringUtils.trimAllWhitespace(clientDTO.getFullName()))) {
            errorList.add("Client name can't be empty");
        }

        if (!StringUtils.hasLength(StringUtils.trimAllWhitespace(clientDTO.getCellphone()))) {
            errorList.add("Client cellphone can't be empty");
        }

        if (!StringUtils.hasLength(StringUtils.trimAllWhitespace(clientDTO.getAddress()))) {
            errorList.add("Client address can't be empty");
        }
        if (!StringUtils.hasLength(StringUtils.trimAllWhitespace(clientDTO.getEmail()))) {
            errorList.add("Client email can't be empty");
        }
        if (!errorList.isEmpty()) {
            throw new ClientBadRequestException(
                    String.format("Invalid and incomplete client data: %s", String.join(", ", errorList)));
        }

        ClientEntity existingClient = new ClientEntity();
        if (clientDTO.getCellphone().equals(existingClient.getCellphone()) &&
                clientDTO.getFullName().equals(existingClient.getFullName()) &&
                clientDTO.getEmail().equals(existingClient.getEmail()) &&
                clientDTO.getAddress().equals(existingClient.getAddress())) {
            throw new ConflictClientException(IClientResponse.UPDATE_CLIENT_CONFLICT);
        }
    }

    public void validateDeleteClient(String document){
        if (!document.matches("^(CC|CE|TI)-\\d+$")) {
            throw new ClientBadRequestException("Invalid document format. It should start with 'CC-', 'CE-' or 'TI-' followed by numbers.");
        }
    }

}
