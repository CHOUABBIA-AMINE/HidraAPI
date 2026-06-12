/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQuarantineRecordJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for TelemetryQuarantineRecord.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository;

import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryQuarantineRecordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for TelemetryQuarantineRecord.
 */
@Repository
public interface TelemetryQuarantineRecordJpaRepository extends JpaRepository<TelemetryQuarantineRecordJpaEntity, String> {
}
