package restaurant.GrandmasFood.webApi.clientController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import restaurant.GrandmasFood.common.converter.client.ClientConverter;
import restaurant.GrandmasFood.common.converter.date.DateTimeConverter;
import restaurant.GrandmasFood.common.domains.dto.ClientDTO;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.services.clientService.impl.ClientServiceImpl;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClientController.class)
@AutoConfigureMockMvc
public class ClientControllerTest {


    @MockBean
    DateTimeConverter dateTimeConverter;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    ClientServiceImpl clientService;


    @Test
    void testCreateClientSuccessfully() throws Exception {
        // Given
        ClientDTO clientDTO = ClientDTO.builder()
                .document("CC-123456789")
                .fullName("Juan Marquez")
                .email("juan.marquez@example.com")
                .cellphone("1234567890")
                .address("calle 2 esquina")
                .build();

        ClientDTO clientDTOExpected = ClientDTO.builder()
                .document("CC-123456789")
                .fullName("Juan Marquez")
                .email("juan.marquez@example.com")
                .cellphone("1234567890")
                .address("calle 2 esquina")
                .build();

        // Configure behavior of the mock service
        when(clientService.createClient(any(ClientDTO.class))).thenReturn(clientDTO);

        // Perform the request and assert the response
        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.document").value("CC-123456789"))
                .andExpect(jsonPath("$.fullName").value("Juan Marquez"))
                .andExpect(jsonPath("$.email").value("juan.marquez@example.com"))
                .andExpect(jsonPath("$.cellphone").value("1234567890"))
                .andExpect(jsonPath("$.address").value("calle 2 esquina"));
    }


    @Test
    void testGetClientSuccessfully() throws Exception {
        // Given
        String document = "CC-123456";
        ClientDTO clientDTOExpected = ClientDTO.builder()
                .document(document)
                .fullName("Juan Marquez")
                .email("juan.marquez@example.com")
                .cellphone("1234567890")
                .address("123 Main St")
                .build();

        when(clientService.getClient(document)).thenReturn(clientDTOExpected);

        // When
        mockMvc.perform(get("/clients/{document}", document))

                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.document").value(document))
                .andExpect(jsonPath("$.fullName").value("Juan Marquez"))
                .andExpect(jsonPath("$.email").value("juan.marquez@example.com"))
                .andExpect(jsonPath("$.cellphone").value("1234567890"))
                .andExpect(jsonPath("$.address").value("123 Main St"));
    }

    @Test
    void testGetAllClientsSuccessfully() throws Exception {
        // Given
        String orderBy = "NAME";
        String direction = "ASC";
        List<ClientEntity> clientEntityList = Arrays.asList(
                ClientEntity.builder()
                        .document("CC-123456789")
                        .fullName("Juan Marquez")
                        .email("juan.marquez@example.com")
                        .cellphone("5678912345")
                        .address("789 Elm St")
                        .removed(false)
                        .build(),
                ClientEntity.builder()
                        .document("CC-987654321")
                        .fullName("Jherlinson Peña")
                        .email("jherlinson.pena@example.com")
                        .cellphone("9012345678")
                        .address("012 Pine St")
                        .removed(false)
                        .build()
        );

        // Crear una instancia del convertidor real
        ClientConverter clientConverter = new ClientConverter();
        List<ClientDTO> clientDTOList = clientConverter.convertClientEntityListToClientDTOList(clientEntityList);

        when(clientService.getAllClients(orderBy, direction)).thenAnswer(invocation -> {
            // Realizar la conversión en el callback del Answer
            return clientDTOList;
        });

        // When
        mockMvc.perform(get("/clients")
                        .param("orderBy", orderBy)
                        .param("direction", direction))

                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].document").value("CC-123456789"))
                .andExpect(jsonPath("$[0].fullName").value("Juan Marquez"))
                .andExpect(jsonPath("$[0].email").value("juan.marquez@example.com"))
                .andExpect(jsonPath("$[0].cellphone").value("5678912345"))
                .andExpect(jsonPath("$[0].address").value("789 Elm St"))
                .andExpect(jsonPath("$[1].document").value("CC-987654321"))
                .andExpect(jsonPath("$[1].fullName").value("Jherlinson Peña"))
                .andExpect(jsonPath("$[1].email").value("jherlinson.pena@example.com"))
                .andExpect(jsonPath("$[1].cellphone").value("9012345678"))
                .andExpect(jsonPath("$[1].address").value("012 Pine St"));
    }
    @Test
    void testUpdateClientSuccessfully() throws Exception {
        // Given
        String document = "CC-123456";
        ClientDTO clientDTO = ClientDTO.builder()
                .fullName("Juan Marquez")
                .email("juanfe092@gmail.com")
                .cellphone("9876543210")
                .address("calle 2 esquina")
                .build();

        ClientDTO updatedClientDTOExpected = ClientDTO.builder()
                .document(document)
                .fullName("Juan Marquez")
                .email("juanfe092@gmail.com")
                .cellphone("9876543210")
                .address("calle 2 esquina")
                .build();

        when(clientService.updateClient(document, clientDTO)).thenReturn(updatedClientDTOExpected);

        // When
        mockMvc.perform(put("/clients/{document}", document)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))

                // Then
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.document").value(document))
                .andExpect(jsonPath("$.fullName").value("Juan Marquez"))
                .andExpect(jsonPath("$.email").value("juanfe092@gmail.com"))
                .andExpect(jsonPath("$.cellphone").value("9876543210"))
                .andExpect(jsonPath("$.address").value("calle 2 esquina"));
    }

    @Test
    void testDeleteClientSuccessfully() throws Exception {
        // Given
        String document = "CC-123456";

        // When
        mockMvc.perform(delete("/clients/{document}", document))

                // Then
                .andExpect(status().isNoContent());
    }

}