package restaurant.GrandmasFood.webApi.clientController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.GrandmasFood.common.constant.endpoints.IClientEndPoints;
import restaurant.GrandmasFood.common.domains.dto.ClientDTO;
import restaurant.GrandmasFood.services.clientService.impl.ClientServiceImpl;
import restaurant.GrandmasFood.validator.client.ClientDtoValidator;

import java.util.List;

@RestController
@RequestMapping(IClientEndPoints.CLIENTS_BASE_URL)
public class ClientController {
    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private ClientDtoValidator clientDtoValidator;


    @PostMapping()
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO){
        clientDtoValidator.validateCreateClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(clientDTO));
    }

    @GetMapping(IClientEndPoints.CLIENT_DOCUMENT)
    public ResponseEntity<ClientDTO> getClient(@PathVariable("document") String document){
        clientDtoValidator.validateGetClient(document);
        return new ResponseEntity<>(clientService.getClient(document), HttpStatus.OK);
    }

    /*
        get all clients
     */

    @GetMapping()
    public ResponseEntity<List<ClientDTO>> getClients(
            @RequestParam(defaultValue = "DOCUMENT") String orderBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        List<ClientDTO> clients = clientService.getAllClients(orderBy, direction);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
    @PutMapping(IClientEndPoints.CLIENT_DOCUMENT)
    public ResponseEntity<ClientDTO> updateClient(@PathVariable("document")String document, @RequestBody ClientDTO updateClient){
        clientDtoValidator.validateUpdateClient(document, updateClient);
        ClientDTO saveClient = clientService.updateClient(document, updateClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveClient);
    }

    @DeleteMapping(IClientEndPoints.CLIENT_DOCUMENT)
    public ResponseEntity<Void> deleteClient(@PathVariable("document")String document){
        clientService.deleteClient(document);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
