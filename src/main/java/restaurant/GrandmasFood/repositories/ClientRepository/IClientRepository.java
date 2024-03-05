package restaurant.GrandmasFood.repositories.ClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import java.util.Optional;

@Repository
public interface IClientRepository extends JpaRepository<ClientEntity, String> {
    Optional<ClientEntity> findClientByDocument(String document);
}