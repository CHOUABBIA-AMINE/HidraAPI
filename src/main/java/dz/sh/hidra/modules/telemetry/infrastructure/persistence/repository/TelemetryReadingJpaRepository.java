/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for telemetry readings.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryReadingJpaEntity;

/**
 * Spring Data repository for telemetry readings.
 */
public interface TelemetryReadingJpaRepository extends JpaRepository<TelemetryReadingJpaEntity, String> {

    Optional<TelemetryReadingJpaEntity> findFirstByPointIdOrderBySourceTimestampDesc(String pointId);

    boolean existsByPointIdAndSourceTimestamp(String pointId, Instant sourceTimestamp);

    Page<TelemetryReadingJpaEntity> findByPointId(String pointId, Pageable pageable);

    Page<TelemetryReadingJpaEntity> findByQualityCodeId(String qualityCodeId, Pageable pageable);

    Page<TelemetryReadingJpaEntity> findByState(String state, Pageable pageable);

    Page<TelemetryReadingJpaEntity> findByIngestionBatchId(String ingestionBatchId, Pageable pageable);

    Page<TelemetryReadingJpaEntity> findByPointIdAndSourceTimestampBetween(String pointId, Instant fromSourceTimestamp, Instant toSourceTimestamp, Pageable pageable);
}
