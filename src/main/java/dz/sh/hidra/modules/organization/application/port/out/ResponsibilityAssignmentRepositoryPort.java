/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssignmentRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Repository port for effective-dated responsibility assignments.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for ResponsibilityAssignment.
 */
public interface ResponsibilityAssignmentRepositoryPort {

    ResponsibilityAssignment save(ResponsibilityAssignment model);

    Optional<ResponsibilityAssignment> findById(String id);

    /**
     * Returns ACTIVE assignments for the same responsibility identity.
     *
     * <p>The application layer evaluates effective-date overlap so persistence
     * remains responsible only for data access.</p>
     */
    List<ResponsibilityAssignment> findActiveByAssigneeAndResponsibilityAndScope(
            String assigneeType,
            String assigneeId,
            ResponsibilityType responsibilityType,
            Long scopeId
    );
}
