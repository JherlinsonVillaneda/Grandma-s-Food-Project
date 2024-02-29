package restaurant.GrandmasFood.servicetest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import restaurant.GrandmasFood.repositories.ClientRepository.IClientRepository;
import restaurant.GrandmasFood.services.clientService.ClientServiceImpl;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {
    @InjectMocks
    private ClientServiceImpl clientService;
    @Mock
    private IClientRepository iClientRepository;
    @Before
    public void setup(){
        when(iClientRepository.save(any(ClientEntity.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
    }
    @Test
    public void testCreateClientSuccess(){
        ClientEntity clientEntity=new ClientEntity();
        ClientEntity clientEntity1=clientService.createClient(clientEntity);
        assertEquals(clientEntity, clientEntity1);
    }

    @Test(expected = ResponseStatusException.class)
    public void testClientNotCreated(){
        ClientEntity existingClient=new ClientEntity();
        existingClient.setDocument("1110454141");
        when(iClientRepository.findClientByDocument(anyString())).thenReturn(Optional.of(existingClient));
        ClientEntity newClient=new ClientEntity();
        newClient.setDocument("1110454141");
        clientService.createClient(newClient);
    }

}
