/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDiscrepancyJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for CustodyDiscrepancy.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.repository;

import dz.sh.hidra.modules.custody.infrastructure.persistence.entity.CustodyDiscrepancyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for CustodyDiscrepancy.
 */
@Repository
public interface CustodyDiscrepancyJpaRepository extends JpaRepository<CustodyDiscrepancyJpaEntity, String> {
}
