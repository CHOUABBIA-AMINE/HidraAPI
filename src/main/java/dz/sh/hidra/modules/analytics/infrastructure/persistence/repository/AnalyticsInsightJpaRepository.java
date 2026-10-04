/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsInsightJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for AnalyticsInsight and its fail-closed same-module validations.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.repository;

import dz.sh.hidra.modules.analytics.infrastructure.persistence.entity.AnalyticsInsightJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsInsightJpaRepository extends JpaRepository<AnalyticsInsightJpaEntity, String> {

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM hidra_analytics_catalog_entry
                WHERE catalog_name = :catalogName
                  AND code = :code
                  AND active = true
            )
            """, nativeQuery = true)
    boolean existsActiveCatalogCode(
            @Param("catalogName") String catalogName,
            @Param("code") String code
    );

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM hidra_analytics_catalog_entry
                WHERE catalog_name = :catalogName
                  AND id = :id
                  AND active = true
            )
            """, nativeQuery = true)
    boolean existsActiveCatalogEntryId(
            @Param("catalogName") String catalogName,
            @Param("id") String id
    );

    @Query(value = "SELECT EXISTS (SELECT 1 FROM hidra_analytics_projection_snapshot WHERE id = :id)", nativeQuery = true)
    boolean existsProjectionSnapshot(@Param("id") String id);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM hidra_analytics_trend_analysis WHERE id = :id)", nativeQuery = true)
    boolean existsTrendAnalysis(@Param("id") String id);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM hidra_analytics_model_run WHERE id = :id)", nativeQuery = true)
    boolean existsModelRun(@Param("id") String id);
}
