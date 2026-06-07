/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTypeCatalogJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for telemetry catalog entries.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryTypeCatalogJpaEntity;

/**
 * Spring Data repository for telemetry catalog entries.
 */
public interface TelemetryTypeCatalogJpaRepository extends JpaRepository<TelemetryTypeCatalogJpaEntity, String> {

    Optional<TelemetryTypeCatalogJpaEntity> findByCatalogNameAndCode(String catalogName, String code);

    boolean existsByCatalogNameAndCode(String catalogName, String code);

    Page<TelemetryTypeCatalogJpaEntity> findByCatalogName(String catalogName, Pageable pageable);

    Page<TelemetryTypeCatalogJpaEntity> findByActive(Boolean active, Pageable pageable);

    Page<TelemetryTypeCatalogJpaEntity> findByCatalogNameAndActive(String catalogName, Boolean active, Pageable pageable);
}
