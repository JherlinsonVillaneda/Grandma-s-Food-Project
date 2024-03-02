package restaurant.GrandmasFood.webApi.clientController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.GrandmasFood.common.constant.endpoints.IClientEndPoints;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.services.clientService.impl.ClientServiceImpl;

@RestController
@RequestMapping(IClientEndPoints.CLIENTS_BASE_URL)
public class ClientController {
    @Autowired
    private ClientServiceImpl clientService;

    @PostMapping()
    public ResponseEntity<ClientEntity> createClient(@RequestBody ClientEntity clientEntity){
        ClientEntity saveClient = clientService.createClient(clientEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveClient);
    }

    @GetMapping(IClientEndPoints.CLIENT_DOCUMENT)
    public ResponseEntity<ClientEntity> getClient(@PathVariable("document") String document){
        return new ResponseEntity<>(clientService.getClient(document), HttpStatus.OK);
    }

    @PutMapping(IClientEndPoints.CLIENT_DOCUMENT)
    public ResponseEntity<ClientEntity> updateClient(@PathVariable("document")String document, @RequestBody ClientEntity updateClient){
        ClientEntity saveClient = clientService.updateClient(document, updateClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveClient);
    }

    @DeleteMapping(IClientEndPoints.CLIENT_DOCUMENT)
    public ResponseEntity<Void> deleteClient(@PathVariable("document")String document){
        clientService.deleteClient(document);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
