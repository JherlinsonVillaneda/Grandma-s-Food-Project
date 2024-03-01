package restaurant.GrandmasFood.repositories.ClientRepository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;

import java.util.Optional;

public interface IClientRepository extends JpaRepository<ClientEntity, String> {
    Optional<ClientEntity>findClientByDocument(String document);

}
