/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskSignalJpaRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for RiskSignal.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository;

import dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity.RiskSignalJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for RiskSignal.
 */
@Repository
public interface RiskSignalJpaRepository extends JpaRepository<RiskSignalJpaEntity, String> {
}
