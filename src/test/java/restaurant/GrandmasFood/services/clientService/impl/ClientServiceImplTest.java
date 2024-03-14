package restaurant.GrandmasFood.services.clientService.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import restaurant.GrandmasFood.common.converter.client.ClientConverter;
import restaurant.GrandmasFood.common.domains.dto.ClientDTO;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.exception.client.ConflictClientException;
import restaurant.GrandmasFood.exception.client.InternalServerErrorException;
import restaurant.GrandmasFood.exception.client.NotFoundException;
import restaurant.GrandmasFood.repository.ClientRepository.IClientRepository;
import restaurant.GrandmasFood.repository.OrderRepository.IOrderRepository;
import restaurant.GrandmasFood.validator.client.ClientDtoValidator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private IClientRepository iClientRepository;

    @Mock
    private ClientConverter clientConverter;

    @Mock
    private ClientDtoValidator clientDtoValidator;

    @Mock
    private IOrderRepository iOrderRepository;
    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void testCreateClientSuccessful() {
        // Arrange
        String document = "CC-123456789";
        ClientDTO clientDTO = ClientDTO.builder()
                .document(document)
                .fullName("Jherlinson Peña")
                .email("jherlinson@gmail.com")
                .cellphone("3103299157")
                .address("Calle 20")
                .build();

        ClientEntity clientEntity = ClientEntity.builder()
                .document(document)
                .fullName("Jherlinson Peña")
                .email("jherlinson@gmail.com")
                .cellphone("3103299157")
                .address("Calle 20")
                .build();

        when(clientConverter.convertClientDTOToClientEntity(clientDTO)).thenReturn(clientEntity);
        when(iClientRepository.findClientByDocument(document)).thenReturn(Optional.empty());
        when(iClientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        when(clientConverter.convertClientEntityToClientDTO(clientEntity)).thenReturn(clientDTO);

        // Act
        ClientDTO client = clientService.createClient(clientDTO);

        // Assert
        assertNotNull(client);
        assertEquals(clientEntity.getDocument(), client.getDocument());
        assertEquals(clientEntity.getFullName(), client.getFullName());
        assertEquals(clientEntity.getEmail(), client.getEmail());
        assertEquals(clientEntity.getCellphone(), client.getCellphone());
        assertEquals(clientEntity.getAddress(), client.getAddress());

        verify(clientDtoValidator, times(1)).validateCreateClient(clientDTO);
        verify(clientConverter, times(1)).convertClientDTOToClientEntity(clientDTO);
        verify(iClientRepository, times(1)).findClientByDocument(document);
        verify(iClientRepository, times(1)).save(any(ClientEntity.class));
        verify(clientConverter, times(1)).convertClientEntityToClientDTO(clientEntity);
    }

    @Test
    public void testCreateClientConflictException() {
        // Arrange
        String document = "CC-123456789";
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setDocument(document);

        ClientEntity existingClient = new ClientEntity();
        existingClient.setDocument(document);

        when(clientConverter.convertClientDTOToClientEntity(clientDTO)).thenReturn(existingClient);
        when(iClientRepository.findClientByDocument(document)).thenReturn(Optional.of(existingClient));

        // Act & Assert
        assertThrows(ConflictClientException.class, () -> clientService.createClient(clientDTO));

        verify(clientDtoValidator, times(1)).validateCreateClient(clientDTO);
        verify(clientConverter, times(1)).convertClientDTOToClientEntity(clientDTO);
        verify(iClientRepository, times(1)).findClientByDocument(document);
        verify(iClientRepository, never()).save(any());
        verify(clientConverter, never()).convertClientEntityToClientDTO(any());
    }
    @Test
    public void testCreateClientInternalServerError() {
        // Arrange
        ClientDTO clientDTO = new ClientDTO();

        when(clientConverter.convertClientDTOToClientEntity(clientDTO)).thenReturn(new ClientEntity());
        when(iClientRepository.findClientByDocument(any())).thenReturn(Optional.empty());
        when(iClientRepository.save(any())).thenThrow(RuntimeException.class);

        // Act & Assert
        assertThrows(InternalServerErrorException.class, () -> clientService.createClient(clientDTO));

        verify(clientDtoValidator, times(1)).validateCreateClient(clientDTO);
        verify(clientConverter, times(1)).convertClientDTOToClientEntity(clientDTO);
        verify(iClientRepository, times(1)).findClientByDocument(any());
        verify(iClientRepository, times(1)).save(any());
        verify(clientConverter, never()).convertClientEntityToClientDTO(any());
    }

    @Test
    public void testGetClientSuccess(){
        // Arrange
        String document = "CC-123456789";
        ClientEntity existingClientEntity = new ClientEntity();
        ClientDTO expectedClientDTO = new ClientDTO();
        when(iClientRepository.findClientByDocument(document)).thenReturn(Optional.of(existingClientEntity));
        when(clientConverter.convertClientEntityToClientDTO(existingClientEntity)).thenReturn(expectedClientDTO);

        // Act
        ClientDTO result = clientService.getClient(document);

        // Assert
        assertEquals(expectedClientDTO, result);
        verify(clientDtoValidator, times(1)).validateGetClient(document);
        verify(iClientRepository, times(1)).findClientByDocument(document);
        verify(clientConverter, times(1)).convertClientEntityToClientDTO(existingClientEntity);

    }
    @Test
    public void testGetClientNotFoundException() {
        // Arrange
        String document = "CC-123456789";
        when(iClientRepository.findClientByDocument(document)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> clientService.getClient(document));
        verify(clientDtoValidator, times(1)).validateGetClient(document);
        verify(iClientRepository, times(1)).findClientByDocument(document);
        verify(clientConverter, never()).convertClientEntityToClientDTO(any());
    }

    @Test
    public void testUpdateClientSuccessful() {
        // Arrange
        String document = "CC-123456789";
        ClientDTO updateClient = ClientDTO.builder()
                .document(document)
                .fullName("Jherlinson Peña")
                .email("jherlinson@gmail.com")
                .cellphone("3103299157")
                .address("Calle 20")
                .build();

        ClientEntity clientEntity = ClientEntity.builder()
                .document(document)
                .fullName("Jherlinson Peña")
                .email("jherlinson@gmail.com")
                .cellphone("3103299157")
                .address("Calle 20")
                .build();

        when(iClientRepository.findClientByDocument(document)).thenReturn(Optional.of(clientEntity));
        when(iClientRepository.save(any())).thenReturn(clientEntity);
        when(clientConverter.convertClientEntityToClientDTO(clientEntity)).thenReturn(updateClient);

        // Act
        ClientDTO result = clientService.updateClient(document, updateClient);

        // Assert
        assertNotNull(result);
        assertEquals(updateClient.getFullName(), result.getFullName());
        assertEquals(updateClient.getEmail(), result.getEmail());
        assertEquals(updateClient.getCellphone(), result.getCellphone());
        assertEquals(updateClient.getAddress(), result.getAddress());

        verify(clientDtoValidator, times(1)).validateUpdateClient(document, updateClient, clientEntity);
        verify(iClientRepository, times(1)).findClientByDocument(document);
        verify(iClientRepository, times(1)).save(clientEntity);
        verify(clientConverter, times(1)).convertClientEntityToClientDTO(clientEntity);
    }


    @Test
    public void testUpdateClientClientNotFound() {
        // Arrange
        String document = "CC-123456789";
        ClientDTO updatedClientDTO = new ClientDTO();

        when(iClientRepository.findClientByDocument(document)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> clientService.updateClient(document, updatedClientDTO));

        verify(iClientRepository, times(1)).findClientByDocument(document);
        verify(iClientRepository, never()).save(any());
        verify(clientConverter, never()).convertClientEntityToClientDTO(any());
    }

    @Test
    public void testUpdateClientInternalServerError() {
        // Arrange
        String document = "CC-123456789";
        ClientDTO updatedClientDTO = ClientDTO.builder()
                .document(document)
                .fullName("Jherlinson Peña")
                .email("jherlinson@gmail.com")
                .cellphone("3103299157")
                .address("Calle 20")
                .build();

        ClientEntity existingClientEntity = new ClientEntity();
        existingClientEntity.setDocument(document);

        when(iClientRepository.findClientByDocument(document)).thenReturn(Optional.of(existingClientEntity));
        when(iClientRepository.save(existingClientEntity)).thenThrow(RuntimeException.class);

        // Act & Assert
        assertThrows(InternalServerErrorException.class, () -> clientService.updateClient(document, updatedClientDTO));

        verify(iClientRepository, times(1)).findClientByDocument(document);
        verify(iClientRepository, times(1)).save(existingClientEntity);
        verify(clientConverter, never()).convertClientEntityToClientDTO(any());
    }

    @Test
    public void testDeleteClientNotFound() {
        // Arrange
        String document = "CC-123456789";
        when(iClientRepository.findClientByDocument(document)).thenReturn(java.util.Optional.empty());

        // Act y Assert
        assertThrows(NotFoundException.class, () -> clientService.deleteClient(document));

        verify(clientDtoValidator, times(1)).validateDeleteClient(document);
        verify(iClientRepository, times(1)).findClientByDocument(document);
        verifyNoMoreInteractions(iOrderRepository, iClientRepository);
    }
    @Test
    public void testDeleteClient() {
        // Arrange
        String document = "CC-123456789";

        ClientEntity existingClient = ClientEntity.builder()
                .document(document)
                .fullName("Jherlinson Peña")
                .email("jherlinson@gmail.com")
                .cellphone("3103299157")
                .address("Calle 20")
                .build();

        when(iClientRepository.findClientByDocument(document)).thenReturn(java.util.Optional.of(existingClient));

        // Act
        clientService.deleteClient(document);

        // Assert
        verify(clientDtoValidator, times(1)).validateDeleteClient(document);
        verify(iClientRepository, times(1)).findClientByDocument(document);
        verify(iOrderRepository, times(1)).deleteAllByClientDocument(existingClient);
        verify(iClientRepository, times(1)).save(existingClient);
    }

    /**
     * BONUS TRACK
     */
    @Test
    public void testGetAllClientsOrderByFullNameAsc() {
        //Arrange
        List<ClientEntity> clientEntities = Arrays.asList(
                new ClientEntity("CC-123456", "Juan", "juan@gmail.com", "30099584", "Calle 60", false),
                new ClientEntity("CC-987456", "Stiven", "stiven@gmail.com", "3103299157", "Calle 19", false)
        );
        when(iClientRepository.findAllByOrderByFullNameAsc()).thenReturn(clientEntities);
        List<ClientDTO> expectedClientDTOs = Arrays.asList(
                new ClientDTO("CC-123456", "Juan", "juan@gmail.com", "30099584", "Calle 60"),
                new ClientDTO("CC-987456", "Stiven", "stiven@gmail.com", "3103299157", "Calle 19")
        );
        when(clientConverter.convertClientEntityListToClientDTOList(clientEntities)).thenReturn(expectedClientDTOs);

        //Act
        List<ClientDTO> result = clientService.getAllClients("NAME", "ASC");

        //Assert
        assertEquals(expectedClientDTOs, result);
    }
    @Test
    public void testGetAllClientsOrderByAddressDesc() {
        //Arrange
        List<ClientEntity> clientEntities = Arrays.asList(
                new ClientEntity("CC-123456", "Juan", "juan@gmail.com", "30099584", "Calle 60", false),
                new ClientEntity("CC-987456", "Stiven", "stiven@gmail.com", "3103299157", "Calle 19", false)
        );
        when(iClientRepository.findAllByOrderByAddressAsc()).thenReturn(clientEntities);
        List<ClientDTO> expectedClientDTOs = Arrays.asList(
                new ClientDTO("CC-123456", "Juan", "juan@gmail.com", "30099584", "Calle 60"),
                new ClientDTO("CC-987456", "Stiven", "stiven@gmail.com", "3103299157", "Calle 19")
        );
        when(clientConverter.convertClientEntityListToClientDTOList(clientEntities)).thenReturn(expectedClientDTOs);

        //Act
        List<ClientDTO> result = clientService.getAllClients("ADDRESS", "ASC");

        //Assert
        assertEquals(expectedClientDTOs, result);
    }
    @Test
    public void testGetAllClientsOrderByDocumentAsc() {
        // Arrange
        List<ClientEntity> clientEntities = Arrays.asList(
                new ClientEntity("CC-123456", "Juan", "juan@gmail.com", "30099584", "Calle 60", false),
                new ClientEntity("CC-987456", "Stiven", "stiven@gmail.com", "3103299157", "Calle 19", false)
        );
        when(iClientRepository.findAllByOrderByDocumentAsc()).thenReturn(clientEntities);
        List<ClientDTO> expectedClientDTOs = Arrays.asList(
                new ClientDTO("CC-123456", "Juan", "juan@gmail.com", "30099584", "Calle 60"),
                new ClientDTO("CC-987456", "Stiven", "stiven@gmail.com", "3103299157", "Calle 19")
        );
        when(clientConverter.convertClientEntityListToClientDTOList(clientEntities)).thenReturn(expectedClientDTOs);
        // Act
        List<ClientDTO> result = clientService.getAllClients("DOCUMENT", "ASC");
        // Assert
        assertEquals(expectedClientDTOs, result);
    }
}