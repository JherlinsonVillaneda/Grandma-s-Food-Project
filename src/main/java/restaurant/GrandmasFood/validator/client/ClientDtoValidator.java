package restaurant.GrandmasFood.validator.client;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import restaurant.GrandmasFood.common.constant.responses.IResponse;
import restaurant.GrandmasFood.common.domains.dto.ClientDTO;
import restaurant.GrandmasFood.exception.client.ClientBadRequestException;


import java.util.ArrayList;
import java.util.List;

@Component
public class ClientDtoValidator {

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

    public void validateUpdateClient(String document, ClientDTO clientDTO){
        final List<String> errorList = new ArrayList<>();
        if(clientDTO == null) {
            throw new ClientBadRequestException("Client instance mustn't be null");
        }
        if (!document.matches("^(CC|CE|TI)-\\d+$")) {
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

}
