/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RequestContextSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.web
 *
 * @Description : Represents a technical request-context snapshot.
 *
 */
package dz.sh.hidra.platform.web;

import java.time.Instant;
import java.util.Objects;

/**
 * Technical request-context snapshot.
 *
 * @param correlationId correlation identifier
 * @param requestId request identifier
 * @param actorId actor identifier
 * @param tenantId tenant identifier
 * @param organizationScopeId organization-scope identifier
 * @param capturedAt capture timestamp
 */
public record RequestContextSnapshot(
        String correlationId,
        String requestId,
        String actorId,
        String tenantId,
        String organizationScopeId,
        Instant capturedAt
) {

    public RequestContextSnapshot {
        correlationId = normalize(correlationId);
        requestId = normalize(requestId);
        actorId = normalize(actorId);
        tenantId = normalize(tenantId);
        organizationScopeId = normalize(organizationScopeId);
        Objects.requireNonNull(capturedAt, "Request context capture timestamp must not be null.");
    }

    public static RequestContextSnapshot empty() {
        return new RequestContextSnapshot(null, null, null, null, null, Instant.now());
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
