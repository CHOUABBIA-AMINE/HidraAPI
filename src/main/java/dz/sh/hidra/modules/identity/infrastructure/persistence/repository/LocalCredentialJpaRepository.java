/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalCredentialJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for Identity-owned LOCAL credentials.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.repository;

import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.LocalCredentialJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Database repository for LOCAL credentials.
 */
@Repository
public interface LocalCredentialJpaRepository extends JpaRepository<LocalCredentialJpaEntity, String> {

    Optional<LocalCredentialJpaEntity> findByUserId(String userId);
}
