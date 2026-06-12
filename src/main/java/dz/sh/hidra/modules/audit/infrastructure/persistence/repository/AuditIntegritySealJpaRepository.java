/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditIntegritySealJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for AuditIntegritySeal.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.repository;

import dz.sh.hidra.modules.audit.infrastructure.persistence.entity.AuditIntegritySealJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for AuditIntegritySeal.
 */
@Repository
public interface AuditIntegritySealJpaRepository extends JpaRepository<AuditIntegritySealJpaEntity, String> {
}
