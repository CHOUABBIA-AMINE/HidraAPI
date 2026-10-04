/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionRunJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for AnalyticsProjectionRun.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.repository;

import dz.sh.hidra.modules.analytics.infrastructure.persistence.entity.AnalyticsProjectionRunJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsProjectionRunJpaRepository
        extends JpaRepository<AnalyticsProjectionRunJpaEntity, String> {

    @Query(value = """
            SELECT to_char(d.updated_at AT TIME ZONE 'UTC', 'YYYYMMDDHH24MISS.US')
                   || '-' ||
                   md5(jsonb_build_array(
                       d.projection_type,
                       d.calculation_policy,
                       d.refresh_policy,
                       d.retention_policy
                   )::text)
            FROM hidra_analytics_projection_definition d
            WHERE d.id = :definitionId
            """, nativeQuery = true)
    Optional<String> resolveProjectionDefinitionVersion(
            @Param("definitionId") String definitionId
    );
}
