/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssignmentJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
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
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ResponsibilityAssignmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for ResponsibilityAssignment.
 */
@Repository
public interface ResponsibilityAssignmentJpaRepository
        extends JpaRepository<ResponsibilityAssignmentJpaEntity, String> {

    List<ResponsibilityAssignmentJpaEntity> findByScopeId(Long scopeId);

    List<ResponsibilityAssignmentJpaEntity> findByAssigneeTypeAndAssigneeId(String assigneeType, String assigneeId);

    List<ResponsibilityAssignmentJpaEntity>
    findByAssigneeTypeAndAssigneeIdAndResponsibilityTypeAndScopeIdAndStatus(
            String assigneeType,
            String assigneeId,
            ResponsibilityType responsibilityType,
            Long scopeId,
            AssignmentStatus status
    );
}
