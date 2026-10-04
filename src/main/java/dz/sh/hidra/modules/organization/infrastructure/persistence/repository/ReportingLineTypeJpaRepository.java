/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineTypeJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for reporting-line type catalog entries.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.repository;

import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ReportingLineTypeJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Persists and resolves Organization-owned reporting-line classifications.
 */
@Repository
public interface ReportingLineTypeJpaRepository
        extends JpaRepository<ReportingLineTypeJpaEntity, String> {

    Optional<ReportingLineTypeJpaEntity> findByCodeAndActiveTrue(String code);
}
