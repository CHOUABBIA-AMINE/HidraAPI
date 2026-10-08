/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryUnitJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for TelemetryUnit.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository;

import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryUnitJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

/**
 * Spring Data JPA repository for TelemetryUnit.
 */
@Repository
public interface TelemetryUnitJpaRepository extends JpaRepository<TelemetryUnitJpaEntity, String> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select reference from TelemetryUnitJpaEntity reference where reference.id = :id")
    Optional<TelemetryUnitJpaEntity> findByIdForShare(@Param("id") String id);
}
