/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListResponsibilitiesUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.in
 *
 * @Description : Read-only application contract for querying responsibility assignments.
 *
 */
package dz.sh.hidra.modules.organization.application.port.in;

import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;

import java.util.List;

/**
 * Lists responsibility assignments without granting authorization or control rights.
 *
 * <p>Business role: exposes assignment records for organization workflows and administration.
 *
 * <p>Architecture role: inbound application port backed by organization persistence ports.
 *
 * <p>Validation: scope identifiers must be positive and assignee identity must be nonblank.
 *
 * <p>Usage: callers may query responsibility facts; security decisions remain owned by identity/security.
 */
public interface ListResponsibilitiesUseCase {

    List<ResponsibilityAssignment> listByScopeId(Long scopeId);

    List<ResponsibilityAssignment> listByAssignee(ResponsibilityAssigneeType assigneeType, String assigneeId);

    /** Transitional compatibility boundary for textual callers. */
    @Deprecated(forRemoval = true)
    default List<ResponsibilityAssignment> listByAssignee(String assigneeType, String assigneeId) {
        return listByAssignee(ResponsibilityAssigneeType.from(assigneeType), assigneeId);
    }
}
