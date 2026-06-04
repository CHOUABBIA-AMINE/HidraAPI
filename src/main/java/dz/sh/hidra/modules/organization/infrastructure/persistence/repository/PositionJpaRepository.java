/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PositionJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for position persistence entities.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.PositionJpaEntity;

/**
 * Spring Data repository for position persistence entities.
 *
 * <p>Business role:
 * Provides storage access for operational position/function records.
 *
 * <p>Architecture role:
 * This is an infrastructure repository used only by persistence adapters.
 *
 * <p>Validation:
 * Domain validation occurs before mapping. Database constraints enforce required fields and code
 * uniqueness.
 *
 * <p>Usage:
 * Use only from PositionRepositoryAdapter.
 */
public interface PositionJpaRepository extends JpaRepository<PositionJpaEntity, String> {

    Optional<PositionJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<PositionJpaEntity> findByActiveTrue();
}
