/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanTargetJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for PlanTarget.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.repository;

import dz.sh.hidra.modules.planning.infrastructure.persistence.entity.PlanTargetJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

/**
 * Spring Data JPA repository for PlanTarget.
 */
@Repository
public interface PlanTargetJpaRepository extends JpaRepository<PlanTargetJpaEntity, String>, JpaSpecificationExecutor<PlanTargetJpaEntity> {

    Page<PlanTargetJpaEntity> findByRevisionId(String revisionId, Pageable pageable);
    @Query("select target.revisionId from PlanTargetJpaEntity target where target.id = :id")
    Optional<String> revisionIdForTarget(@Param("id") String id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select target from PlanTargetJpaEntity target where target.revisionId = :revisionId order by target.id")
    java.util.List<PlanTargetJpaEntity> findByRevisionIdForUpdate(@Param("revisionId") String revisionId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select reference from PlanTargetJpaEntity reference where reference.id = :id")
    Optional<PlanTargetJpaEntity> findByIdForUpdate(@Param("id") String id);
}
