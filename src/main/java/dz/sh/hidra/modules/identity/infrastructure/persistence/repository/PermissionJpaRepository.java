/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for identity permission persistence.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.repository;

import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.PermissionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for identity permission persistence.
 *
 * <p>Business role: provides database access for permission catalog records.</p>
 *
 * <p>Architecture role: infrastructure repository used by identity persistence adapters.
 * It must not be imported by identity domain or application ports.</p>
 *
 * <p>Validation responsibility: delegates uniqueness and required-column enforcement to
 * database constraints and repository method contracts.</p>
 *
 * <p>Usage: inject into {@link PermissionRepositoryAdapter} only.</p>
 */
@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionJpaEntity, String> {

    Optional<PermissionJpaEntity> findByCode(String code);

    boolean existsByCode(String code);
}
