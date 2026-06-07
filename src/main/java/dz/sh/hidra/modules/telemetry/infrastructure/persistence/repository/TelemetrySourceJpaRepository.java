/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for telemetry sources.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetrySourceJpaEntity;

/**
 * Spring Data repository for telemetry sources.
 */
public interface TelemetrySourceJpaRepository extends JpaRepository<TelemetrySourceJpaEntity, String> {

    Optional<TelemetrySourceJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    Page<TelemetrySourceJpaEntity> findByStatus(String status, Pageable pageable);

    Page<TelemetrySourceJpaEntity> findBySourceTypeId(String sourceTypeId, Pageable pageable);

    Page<TelemetrySourceJpaEntity> findByProtocolId(String protocolId, Pageable pageable);

    Page<TelemetrySourceJpaEntity> findBySourceTypeIdAndStatus(String sourceTypeId, String status, Pageable pageable);

    Page<TelemetrySourceJpaEntity> findByCodeContainingIgnoreCaseOrNameFrContainingIgnoreCase(String code, String nameFr, Pageable pageable);
}
