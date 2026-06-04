/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for employee persistence entities.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.EmployeeJpaEntity;

/**
 * Spring Data repository for employee persistence entities.
 *
 * <p>Business role:
 * Provides storage access for employee persistence records and employee-owned assignment and
 * reporting-line rows.
 *
 * <p>Architecture role:
 * This is an infrastructure repository. It must not be used directly by API controllers or domain
 * objects; application code depends on outbound ports implemented by adapters.
 *
 * <p>Validation:
 * Database constraints enforce required and unique fields. Domain invariants are enforced before
 * persistence mapping.
 *
 * <p>Usage:
 * Use only from organization persistence adapters.
 */
public interface EmployeeJpaRepository extends JpaRepository<EmployeeJpaEntity, String> {

    Optional<EmployeeJpaEntity> findByEmployeeNumber(String employeeNumber);

    Optional<EmployeeJpaEntity> findByIdentityUserReference(String identityUserReference);

    boolean existsByEmployeeNumber(String employeeNumber);

    @Query("""
            select distinct employee
            from EmployeeJpaEntity employee
            join employee.assignments assignment
            where assignment.organizationUnitId = :organizationUnitId
              and (assignment.effectiveTo is null or assignment.effectiveTo >= current_date)
            """)
    List<EmployeeJpaEntity> findAssignedToOrganizationUnit(@Param("organizationUnitId") String organizationUnitId);
}
