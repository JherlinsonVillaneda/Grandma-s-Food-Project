package restaurant.GrandmasFood.repository.ClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;
import java.util.List;
import java.util.Optional;

@Repository
public interface IClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findClientByDocument(String document);
    List<ClientEntity> findAllByOrderByFullNameAsc();
    List<ClientEntity> findAllByOrderByFullNameDesc();
    List<ClientEntity> findAllByOrderByAddressAsc();
    List<ClientEntity> findAllByOrderByAddressDesc();
    List<ClientEntity> findAllByOrderByDocumentAsc();
    List<ClientEntity> findAllByOrderByDocumentDesc();

}

