/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NearMissReportJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for NearMissReport.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.repository;

import dz.sh.hidra.modules.hse.infrastructure.persistence.entity.NearMissReportJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for NearMissReport.
 */
@Repository
public interface NearMissReportJpaRepository extends JpaRepository<NearMissReportJpaEntity, String> {
}
