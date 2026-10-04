/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository and fail-closed semantic lookups for TelemetrySource.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository;

import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetrySourceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Persists telemetry sources and verifies code/catalog semantics before save.
 */
@Repository
public interface TelemetrySourceJpaRepository
        extends JpaRepository<TelemetrySourceJpaEntity, String> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, String id);

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM hidra_telemetry_type_catalog entry
                WHERE entry.id = :entryId
                  AND entry.catalog_name = :catalogName
                  AND entry.active = true
            )
            """, nativeQuery = true)
    boolean existsActiveCatalogEntry(
            @Param("entryId") String entryId,
            @Param("catalogName") String catalogName
    );
}
