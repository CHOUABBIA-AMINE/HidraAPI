/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditCorrelationContext
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.model
 *
 * @Description : Request, correlation, and causation context.
 *
 */
package dz.sh.hidra.modules.audit.domain.model;

import java.time.Instant;

    /**
     * Request, correlation, and causation context.
     *
         * @param id id
     * @param auditEventId auditEventId
     * @param correlationId correlationId
     * @param requestId requestId
     * @param causationId causationId
     * @param sessionIdHash sessionIdHash
     * @param traceId traceId
     * @param spanId spanId
     * @param sourceSystemCode sourceSystemCode
     * @param sourceMessageId sourceMessageId
     * @param capturedAt capturedAt
     */
    public record AuditCorrelationContext(
            String id,
        String auditEventId,
        String correlationId,
        String requestId,
        String causationId,
        String sessionIdHash,
        String traceId,
        String spanId,
        String sourceSystemCode,
        String sourceMessageId,
        Instant capturedAt
    ) {

        public AuditCorrelationContext {
        id = normalize(id);
        auditEventId = normalize(auditEventId);
        correlationId = normalize(correlationId);
        requestId = normalize(requestId);
        causationId = normalize(causationId);
        sessionIdHash = normalize(sessionIdHash);
        traceId = normalize(traceId);
        spanId = normalize(spanId);
        sourceSystemCode = normalize(sourceSystemCode);
        sourceMessageId = normalize(sourceMessageId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
