/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityReconciliationResult
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.dto
 *
 * @Description : Read-only reconciliation findings for responsibility assignments.
 *
 */
package dz.sh.hidra.modules.organization.application.dto;

import java.util.List;
import java.util.Objects;

/**
 * Reports responsibility-integrity findings without repairing or mutating data.
 *
 * <p>Business role: identifies assignments whose assignee, registered scope, or
 * current scope owner no longer satisfies Organization's canonical integrity rules.</p>
 *
 * <p>Architecture role: application DTO returned by the reconciliation inbound port.
 * It exposes no repository/JPA type and never invents replacement identifiers.</p>
 *
 * <p>Usage: callers may inspect findings and route them to governed remediation,
 * workflow, or audit processes. This result is not an authorization decision.</p>
 *
 * @param scannedAssignments number of assignments inspected
 * @param issues immutable reconciliation findings
 */
public record ResponsibilityReconciliationResult(
        int scannedAssignments,
        List<Issue> issues
) {

    public ResponsibilityReconciliationResult {
        if (scannedAssignments < 0) {
            throw new IllegalArgumentException("Scanned assignment count must not be negative.");
        }
        issues = List.copyOf(Objects.requireNonNull(issues, "Reconciliation issues must not be null."));
    }

    /**
     * One deterministic reconciliation finding.
     *
     * @param assignmentId affected assignment identifier
     * @param code governed issue category
     * @param referenceId relevant assignee, scope, or owner identifier when available
     */
    public record Issue(String assignmentId, IssueCode code, String referenceId) {
        public Issue {
            if (assignmentId == null || assignmentId.isBlank()) {
                throw new IllegalArgumentException("Reconciliation assignment ID must not be blank.");
            }
            assignmentId = assignmentId.trim();
            Objects.requireNonNull(code, "Reconciliation issue code must not be null.");
            referenceId = referenceId == null || referenceId.isBlank() ? null : referenceId.trim();
        }
    }

    /**
     * Closed reconciliation issue vocabulary.
     */
    public enum IssueCode {
        MISSING_ASSIGNEE,
        ASSIGNEE_NOT_ACTIVE,
        UNKNOWN_SCOPE,
        UNSUPPORTED_SCOPE_RESOLVER,
        MISSING_SCOPE_OWNER,
        SCOPE_OWNER_IDENTITY_MISMATCH,
        SCOPE_OWNER_NOT_ASSIGNABLE,
        SELF_TARGET_RESPONSIBILITY
    }
}
