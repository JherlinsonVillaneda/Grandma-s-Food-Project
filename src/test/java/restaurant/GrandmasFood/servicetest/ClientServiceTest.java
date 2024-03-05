package restaurant.GrandmasFood.servicetest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
/**
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
        when(iClientRepository.findClientByDocument(anyString())).thenReturn(Optional.empty());
        ClientEntity clientEntity = ClientEntity.builder().document("CC-1111111")
                .fullName("Juan").email("juan123@gmail.com").cellphone("32121212").address("Calle 19").build();
        ClientEntity createClient = clientService.createClient(clientEntity);
        assertEquals(clientEntity, createClient);
        verify(iClientRepository, times(1)).save(clientEntity);
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
*/