/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for telemetry points.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryPointJpaEntity;

/**
 * Spring Data repository for telemetry points.
 */
public interface TelemetryPointJpaRepository extends JpaRepository<TelemetryPointJpaEntity, String> {

    Optional<TelemetryPointJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    Page<TelemetryPointJpaEntity> findByDeviceId(String deviceId, Pageable pageable);

    Page<TelemetryPointJpaEntity> findByPointTypeId(String pointTypeId, Pageable pageable);

    Page<TelemetryPointJpaEntity> findBySignalTypeId(String signalTypeId, Pageable pageable);

    Page<TelemetryPointJpaEntity> findByStatus(String status, Pageable pageable);

    Page<TelemetryPointJpaEntity> findByDeviceIdAndStatus(String deviceId, String status, Pageable pageable);

    Page<TelemetryPointJpaEntity> findByCodeContainingIgnoreCaseOrNameFrContainingIgnoreCase(String code, String nameFr, Pageable pageable);
}
