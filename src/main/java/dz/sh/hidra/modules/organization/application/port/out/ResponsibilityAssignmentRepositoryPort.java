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
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for ResponsibilityAssignment.
 */
public interface ResponsibilityAssignmentRepositoryPort {

    ResponsibilityAssignment save(ResponsibilityAssignment model);

    Optional<ResponsibilityAssignment> findById(String id);

    /**
     * Loads one assignment while holding a database write lock for the surrounding transaction.
     *
     * <p>The default keeps non-JPA test adapters source-compatible. The production JPA adapter
     * overrides this method with a pessimistic write lock so concurrent revocations serialize.</p>
     */
    default Optional<ResponsibilityAssignment> findByIdForUpdate(String id) {
        return findById(id);
    }

    /**
     * Returns all responsibility assignments for read-only reconciliation.
     *
     * <p>This contract deliberately returns domain models rather than persistence
     * entities and does not authorize mutation or repair.</p>
     */
    default List<ResponsibilityAssignment> findAll() {
        return List.of();
    }

    default List<ResponsibilityAssignment> findByScopeId(Long scopeId) {
        return List.of();
    }

    default List<ResponsibilityAssignment> findByAssignee(ResponsibilityAssigneeType assigneeType, String assigneeId) {
        return findByAssignee(assigneeType.name(), assigneeId);
    }

    /** Transitional compatibility boundary for legacy textual implementations. */
    @Deprecated(forRemoval = true)
    default List<ResponsibilityAssignment> findByAssignee(String assigneeType, String assigneeId) {
        return List.of();
    }

    /**
     * Returns ACTIVE assignments for the same responsibility identity.
     *
     * <p>The application layer evaluates effective-date overlap so persistence
     * remains responsible only for data access.</p>
     */
    default List<ResponsibilityAssignment> findActiveByAssigneeAndResponsibilityAndScope(
            ResponsibilityAssigneeType assigneeType,
            String assigneeId,
            ResponsibilityType responsibilityType,
            Long scopeId
    ) {
        return findActiveByAssigneeAndResponsibilityAndScope(
                assigneeType.name(), assigneeId, responsibilityType, scopeId
        );
    }

    /** Transitional compatibility boundary for legacy textual implementations. */
    @Deprecated(forRemoval = true)
    default List<ResponsibilityAssignment> findActiveByAssigneeAndResponsibilityAndScope(
            String assigneeType,
            String assigneeId,
            ResponsibilityType responsibilityType,
            Long scopeId
    ) {
        return List.of();
    }
}
