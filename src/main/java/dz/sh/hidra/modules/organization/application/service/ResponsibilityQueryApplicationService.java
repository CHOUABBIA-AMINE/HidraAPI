/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityQueryApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Read-only application service for responsibility assignment queries.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.port.in.ListResponsibilitiesUseCase;
import dz.sh.hidra.modules.organization.application.port.out.ResponsibilityAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Queries responsibility facts from organization persistence.
 *
 * <p>Business role: supports administration and reconciliation views over responsibility assignments.
 *
 * <p>Architecture role: application service depending only on an outbound repository port.
 *
 * <p>Validation: rejects invalid scope IDs and blank assignee identity before repository access.
 *
 * <p>Usage: this service reports responsibility records and must not be used as an authorization evaluator.
 */
@Service
public final class ResponsibilityQueryApplicationService implements ListResponsibilitiesUseCase {

    private final ResponsibilityAssignmentRepositoryPort repository;

    public ResponsibilityQueryApplicationService(ResponsibilityAssignmentRepositoryPort repository) {
        this.repository = Objects.requireNonNull(repository, "Responsibility assignment repository port must not be null.");
    }

    @Override
    public List<ResponsibilityAssignment> listByScopeId(Long scopeId) {
        if (scopeId == null || scopeId <= 0) {
            throw new IllegalArgumentException("Operational scope registry ID must be positive.");
        }
        return List.copyOf(repository.findByScopeId(scopeId));
    }

    @Override
    public List<ResponsibilityAssignment> listByAssignee(String assigneeType, String assigneeId) {
        if (assigneeType == null || assigneeType.isBlank() || assigneeId == null || assigneeId.isBlank()) {
            throw new IllegalArgumentException("Assignee type and ID must not be blank.");
        }
        return List.copyOf(repository.findByAssignee(assigneeType.trim(), assigneeId.trim()));
    }
}
