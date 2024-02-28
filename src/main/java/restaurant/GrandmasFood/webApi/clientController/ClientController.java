package restaurant.GrandmasFood.webApi.clientController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import restaurant.GrandmasFood.common.constant.endpoints.IClientEndPoints;
import restaurant.GrandmasFood.common.domains.dto.ClientDTO;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.services.clientService.ClientServiceImpl;

@RestController
@RequestMapping(IClientEndPoints.CLIENTS_BASE_URL)
public class ClientController {
    @Autowired
    private ClientServiceImpl clientService;

    @PostMapping(IClientEndPoints.CREATE_CLIENT)
    public ResponseEntity<ClientEntity> createClient(@RequestBody ClientEntity clientEntity){
        ClientEntity saveClient = clientService.createClient(clientEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveClient);
    }

    @GetMapping(IClientEndPoints.GET_CLIENT)
    public ResponseEntity<ClientEntity> getClient(@PathVariable("document") String document){
        return new ResponseEntity<>(clientService.getClient(document), HttpStatus.OK);
    }

    @PutMapping(IClientEndPoints.PUT_CLIENT)
    public ResponseEntity<ClientEntity> updateClient(@PathVariable("document")String document, @RequestBody ClientEntity updateClient){
        ClientEntity saveClient = clientService.updateClient(document, updateClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveClient);
    }

    @DeleteMapping(IClientEndPoints.DELETE_CLIENT)
    public ResponseEntity<Void> deleteClient(@PathVariable("document")String document){
        clientService.deleteClient(document);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
