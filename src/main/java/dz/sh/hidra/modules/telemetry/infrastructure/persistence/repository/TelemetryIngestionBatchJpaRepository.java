/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryIngestionBatchJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for telemetry ingestion batches.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryIngestionBatchJpaEntity;

/**
 * Spring Data repository for telemetry ingestion batches.
 */
public interface TelemetryIngestionBatchJpaRepository extends JpaRepository<TelemetryIngestionBatchJpaEntity, String> {

    Page<TelemetryIngestionBatchJpaEntity> findBySourceId(String sourceId, Pageable pageable);

    Page<TelemetryIngestionBatchJpaEntity> findByStatus(String status, Pageable pageable);

    Page<TelemetryIngestionBatchJpaEntity> findByStartedAtBetween(Instant startedFrom, Instant startedTo, Pageable pageable);

    Page<TelemetryIngestionBatchJpaEntity> findBySourceIdAndStatus(String sourceId, String status, Pageable pageable);
}
