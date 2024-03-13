package restaurant.GrandmasFood.webApi.clientController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @PostMapping
//    @Operation(summary = "Create a new client", description = "Creates a new client with the provided details")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Client created successfully",
//                    content = @Content(schema = @Schema(implementation = ClientDTO.class))),
//            @ApiResponse(responseCode = "400", description = "Bad request"),
//            @ApiResponse(responseCode = "500", description = "Internal server error")
//    })
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        clientDtoValidator.validateCreateClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(clientDTO));
    }

    @GetMapping(IClientEndPoints.CLIENT_DOCUMENT)
    @Operation(summary = "Get client by document", description = "Retrieves a client by their document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client retrieved successfully", content = @Content(schema = @Schema(implementation = ClientDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ClientDTO> getClient(@PathVariable("document") @Parameter(description = "Client document", required = true) String document)  {
      clientDtoValidator.validateGetClient(document);  
      return new ResponseEntity<>(clientService.getClient(document), HttpStatus.OK);
    }

    @GetMapping()
    @Operation(summary = "Get all clients", description = "Retrieves all clients")
    @ApiResponse(responseCode = "200", description = "Clients retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ClientDTO.class, type = "array")))
    public ResponseEntity<List<ClientDTO>> getClients(
            @RequestParam(defaultValue = "DOCUMENT") String orderBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        List<ClientDTO> clients = clientService.getAllClients(orderBy, direction);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
    @PutMapping(IClientEndPoints.CLIENT_DOCUMENT)
    @Operation(summary = "Update a client", description = "Updates an existing client with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client updated successfully", content = @Content(schema = @Schema(implementation = ClientDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ClientDTO> updateClient(@PathVariable("document") @Parameter(description = "Client document", required = true) String document,
                                                  @RequestBody @Parameter(description = "Updated client details", required = true) ClientDTO updatedClient) {
        clientDtoValidator.validateUpdateClient(document, updatedClient);
        ClientDTO savedClient = clientService.updateClient(document, updatedClient);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(savedClient);
    }

    @DeleteMapping(IClientEndPoints.CLIENT_DOCUMENT)
    @Operation(summary = "Delete a client", description = "Deletes an existing client by document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteClient(@PathVariable("document") @Parameter(description = "Client document", required = true) String document) {
        clientDtoValidator.validateDeleteClient(document);
        clientService.deleteClient(document);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
