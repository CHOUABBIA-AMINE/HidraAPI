/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityOperationContext
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Carries authenticated actor, permission, workflow and correlation evidence for responsibility operations.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Execution context for responsibility commands and reconciliation.
 *
 * <p>The permission set is resolved from the authenticated principal before application
 * invocation. In the deployed stack those effective permissions are backed by Identity-owned
 * grants through the platform permission resolver. The application layer never imports Identity
 * internals.</p>
 *
 * @param actorId authenticated actor identifier
 * @param actorUsername authenticated principal name
 * @param actorDisplayName display-name snapshot
 * @param effectivePermissions current Identity-backed effective permission codes
 * @param workflowInstanceId approved workflow instance for state-changing operations
 * @param operationReference stable pre-write workflow target/reference
 * @param requestId request correlation reference
 * @param correlationId cross-module correlation reference
 */
public record ResponsibilityOperationContext(
        String actorId,
        String actorUsername,
        String actorDisplayName,
        Set<String> effectivePermissions,
        String workflowInstanceId,
        String operationReference,
        String requestId,
        String correlationId
) {

    public ResponsibilityOperationContext {
        actorId = requireText(actorId, "Responsibility actor ID must not be blank.");
        actorUsername = normalize(actorUsername);
        actorDisplayName = normalize(actorDisplayName);
        operationReference = requireText(
                operationReference,
                "Responsibility operation reference must not be blank."
        );
        requestId = normalize(requestId);
        correlationId = requireText(
                correlationId,
                "Responsibility correlation ID must not be blank."
        );
        workflowInstanceId = normalize(workflowInstanceId);

        Objects.requireNonNull(effectivePermissions, "Effective permissions must not be null.");
        LinkedHashSet<String> normalized = new LinkedHashSet<>();
        effectivePermissions.stream()
                .map(ResponsibilityOperationContext::normalize)
                .filter(Objects::nonNull)
                .forEach(normalized::add);
        effectivePermissions = Set.copyOf(normalized);
    }

    public boolean hasPermission(String permissionCode) {
        String required = requireText(permissionCode, "Required permission code must not be blank.");
        return effectivePermissions.contains("*") || effectivePermissions.contains(required);
    }

    private static String requireText(String value, String message) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new IllegalArgumentException(message);
        }
        return normalized;
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
