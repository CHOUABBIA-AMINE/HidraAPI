/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditCorrelationContextJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuditCorrelationContext.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AuditCorrelationContext.
     */
    @Entity
    @Table(name = "hidra_audit_correlation_context")
    public class AuditCorrelationContextJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "audit_event_id", nullable = false, length = 80)
    private String auditEventId;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "request_id", nullable = true, length = 120)
    private String requestId;

    @Column(name = "causation_id", nullable = true, length = 120)
    private String causationId;

    @Column(name = "session_id_hash", nullable = true, length = 256)
    private String sessionIdHash;

    @Column(name = "trace_id", nullable = true, length = 120)
    private String traceId;

    @Column(name = "span_id", nullable = true, length = 120)
    private String spanId;

    @Column(name = "source_system_code", nullable = true, length = 120)
    private String sourceSystemCode;

    @Column(name = "source_message_id", nullable = true, length = 120)
    private String sourceMessageId;

    @Column(name = "captured_at", nullable = false)
    private Instant capturedAt;

        protected AuditCorrelationContextJpaEntity() {
            // Required by JPA.
        }

        public AuditCorrelationContextJpaEntity(
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
            this.id = id;
        this.auditEventId = auditEventId;
        this.correlationId = correlationId;
        this.requestId = requestId;
        this.causationId = causationId;
        this.sessionIdHash = sessionIdHash;
        this.traceId = traceId;
        this.spanId = spanId;
        this.sourceSystemCode = sourceSystemCode;
        this.sourceMessageId = sourceMessageId;
        this.capturedAt = capturedAt;
        }


    public String id() {
        return id;
    }


    public String auditEventId() {
        return auditEventId;
    }


    public String correlationId() {
        return correlationId;
    }


    public String requestId() {
        return requestId;
    }


    public String causationId() {
        return causationId;
    }


    public String sessionIdHash() {
        return sessionIdHash;
    }


    public String traceId() {
        return traceId;
    }


    public String spanId() {
        return spanId;
    }


    public String sourceSystemCode() {
        return sourceSystemCode;
    }


    public String sourceMessageId() {
        return sourceMessageId;
    }


    public Instant capturedAt() {
        return capturedAt;
    }

    }
