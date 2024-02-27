package restaurant.GrandmasFood.repositories.ClientRepository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restaurant.GrandmasFood.common.domains.entity.client.ClientEntity;

public interface IClientRepository extends JpaRepository<ClientEntity, String> {

    @Modifying
    @Transactional
    @Query("DELETE FROM client c WHERE c.document = :document_param")
    void deleteByDocument(@Param("document_param") String document_param);
}
