/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDeviceJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for telemetry devices.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryDeviceJpaEntity;

/**
 * Spring Data repository for telemetry devices.
 */
public interface TelemetryDeviceJpaRepository extends JpaRepository<TelemetryDeviceJpaEntity, String> {

    Optional<TelemetryDeviceJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    Page<TelemetryDeviceJpaEntity> findBySourceId(String sourceId, Pageable pageable);

    Page<TelemetryDeviceJpaEntity> findByDeviceTypeId(String deviceTypeId, Pageable pageable);

    Page<TelemetryDeviceJpaEntity> findByStatus(String status, Pageable pageable);

    Page<TelemetryDeviceJpaEntity> findBySourceIdAndStatus(String sourceId, String status, Pageable pageable);

    Page<TelemetryDeviceJpaEntity> findByCodeContainingIgnoreCaseOrNameFrContainingIgnoreCase(String code, String nameFr, Pageable pageable);
}
