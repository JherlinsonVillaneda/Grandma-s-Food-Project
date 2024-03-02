package restaurant.GrandmasFood.repositories.ClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import java.util.Optional;

public interface IClientRepository extends JpaRepository<ClientEntity, String> {
    Optional<ClientEntity> findClientByDocument(String document);
}