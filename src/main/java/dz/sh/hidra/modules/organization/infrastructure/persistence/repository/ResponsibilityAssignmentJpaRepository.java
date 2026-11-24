/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssignmentJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-24
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for ResponsibilityAssignment.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.repository;

import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ResponsibilityAssignmentJpaEntity;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for ResponsibilityAssignment.
 */
@Repository
public interface ResponsibilityAssignmentJpaRepository
        extends JpaRepository<ResponsibilityAssignmentJpaEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select assignment from ResponsibilityAssignmentJpaEntity assignment where assignment.id = :id")
    Optional<ResponsibilityAssignmentJpaEntity> findByIdForUpdate(@Param("id") String id);

    List<ResponsibilityAssignmentJpaEntity> findByScopeId(Long scopeId);

    // Never scan or export the administrative responsibility directory.
    @Query(value = """
            SELECT r.* FROM public.hidra_org_responsibility_assignment r
            WHERE r.status = 'ACTIVE'
              AND r.responsibility_type IN ('OWNER','ACCOUNTABLE','RESPONSIBLE')
              AND r.valid_from <= :at AND (r.valid_to IS NULL OR r.valid_to > :at)
              AND ((r.assignee_type = 'EMPLOYEE' AND r.assignee_id = :employeeId)
                OR (r.assignee_type = 'ORGANIZATION_UNIT' AND EXISTS (
                    SELECT 1 FROM public.hidra_org_employee_assignment a
                    WHERE a.employee_id = :employeeId AND a.organization_unit_id = r.assignee_id
                      AND a.status = 'ACTIVE'
                      AND a.valid_from <= :at AND (a.valid_to IS NULL OR a.valid_to > :at))))
            ORDER BY r.scope_id, r.id
            """, nativeQuery = true)
    List<ResponsibilityAssignmentJpaEntity> findCurrentCandidatesForEmployee(
            @Param("employeeId") String employeeId, @Param("at") java.time.Instant at);

    List<ResponsibilityAssignmentJpaEntity> findByAssigneeTypeAndAssigneeId(
            ResponsibilityAssigneeType assigneeType,
            String assigneeId
    );

    List<ResponsibilityAssignmentJpaEntity>
    findByAssigneeTypeAndAssigneeIdAndResponsibilityTypeAndScopeIdAndStatus(
            ResponsibilityAssigneeType assigneeType,
            String assigneeId,
            ResponsibilityType responsibilityType,
            Long scopeId,
            AssignmentStatus status
    );
}
