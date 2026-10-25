/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignmentJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for EmployeeAssignment.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.repository;

import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.EmployeeAssignmentJpaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for EmployeeAssignment.
 */
@Repository
public interface EmployeeAssignmentJpaRepository extends org.springframework.data.repository.Repository<EmployeeAssignmentJpaEntity, String> {

    java.util.Optional<EmployeeAssignmentJpaEntity> findById(String id);

    java.util.List<EmployeeAssignmentJpaEntity> findAll();

    /**
     * A bounded membership predicate for Workflow's existing eligibility contract.
     * Does not grant authorization or expose other employee/assignment records.
     * Validity uses the original inclusive-start/exclusive-end interval.
     */
    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM public.hidra_org_employee_assignment a
                WHERE a.employee_id = :employeeId
                  AND a.organization_unit_id = :unitId
                  AND a.status = 'ACTIVE'
                  AND a.valid_from <= :at
                  AND (a.valid_to IS NULL OR a.valid_to > :at)
            )
            """, nativeQuery = true)
    boolean existsActiveMembershipAt(
            @Param("employeeId") String employeeId,
            @Param("unitId") String unitId,
            @Param("at") Instant at
    );
}
