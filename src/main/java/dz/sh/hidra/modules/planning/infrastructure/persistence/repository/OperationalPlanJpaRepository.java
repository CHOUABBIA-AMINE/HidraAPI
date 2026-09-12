/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlanJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for OperationalPlan.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.repository;

import dz.sh.hidra.modules.planning.infrastructure.persistence.entity.OperationalPlanJpaEntity;
import java.time.Instant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for OperationalPlan.
 */
@Repository
public interface OperationalPlanJpaRepository extends JpaRepository<OperationalPlanJpaEntity, String> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            update OperationalPlanJpaEntity plan
               set plan.nameAr = :nameAr,
                   plan.nameFr = :nameFr,
                   plan.nameEn = :nameEn,
                   plan.responsibleOrganizationUnitId = :responsibleOrganizationUnitId,
                   plan.updatedAt = :newUpdatedAt
             where plan.id = :id
               and plan.updatedAt = :expectedUpdatedAt
            """)
    int updateMetadataIfUpdatedAtMatches(
            @Param("id") String id,
            @Param("expectedUpdatedAt") Instant expectedUpdatedAt,
            @Param("nameAr") String nameAr,
            @Param("nameFr") String nameFr,
            @Param("nameEn") String nameEn,
            @Param("responsibleOrganizationUnitId") String responsibleOrganizationUnitId,
            @Param("newUpdatedAt") Instant newUpdatedAt
    );
}
