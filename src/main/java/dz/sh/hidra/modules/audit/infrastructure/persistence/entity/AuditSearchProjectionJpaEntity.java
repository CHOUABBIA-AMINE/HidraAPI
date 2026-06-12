/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditSearchProjectionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuditSearchProjection.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AuditSearchProjection.
     */
    @Entity
    @Table(name = "hidra_audit_search_projection")
    public class AuditSearchProjectionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "audit_event_id", nullable = false, length = 80)
    private String auditEventId;

    @Column(name = "source_module", nullable = false, length = 80)
    private String sourceModule;

    @Column(name = "event_category_code", nullable = false, length = 120)
    private String eventCategoryCode;

    @Column(name = "event_type_code", nullable = false, length = 120)
    private String eventTypeCode;

    @Column(name = "action_code", nullable = false, length = 120)
    private String actionCode;

    @Column(name = "actor_id", nullable = true, length = 120)
    private String actorId;

    @Column(name = "actor_display_name_search", nullable = true, length = 240)
    private String actorDisplayNameSearch;

    @Column(name = "organization_unit_id", nullable = true, length = 120)
    private String organizationUnitId;

    @Column(name = "target_module", nullable = false, length = 80)
    private String targetModule;

    @Column(name = "target_type", nullable = false, length = 120)
    private String targetType;

    @Column(name = "target_id", nullable = false, length = 120)
    private String targetId;

    @Column(name = "target_search_text", nullable = true, length = 500)
    private String targetSearchText;

    @Column(name = "decision_code", nullable = true, length = 120)
    private String decisionCode;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "request_id", nullable = true, length = 120)
    private String requestId;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    @Column(name = "indexed_at", nullable = false)
    private Instant indexedAt;

        protected AuditSearchProjectionJpaEntity() {
            // Required by JPA.
        }

        public AuditSearchProjectionJpaEntity(
                String id,
            String auditEventId,
            String sourceModule,
            String eventCategoryCode,
            String eventTypeCode,
            String actionCode,
            String actorId,
            String actorDisplayNameSearch,
            String organizationUnitId,
            String targetModule,
            String targetType,
            String targetId,
            String targetSearchText,
            String decisionCode,
            String correlationId,
            String requestId,
            Instant occurredAt,
            Instant recordedAt,
            Instant indexedAt
        ) {
            this.id = id;
        this.auditEventId = auditEventId;
        this.sourceModule = sourceModule;
        this.eventCategoryCode = eventCategoryCode;
        this.eventTypeCode = eventTypeCode;
        this.actionCode = actionCode;
        this.actorId = actorId;
        this.actorDisplayNameSearch = actorDisplayNameSearch;
        this.organizationUnitId = organizationUnitId;
        this.targetModule = targetModule;
        this.targetType = targetType;
        this.targetId = targetId;
        this.targetSearchText = targetSearchText;
        this.decisionCode = decisionCode;
        this.correlationId = correlationId;
        this.requestId = requestId;
        this.occurredAt = occurredAt;
        this.recordedAt = recordedAt;
        this.indexedAt = indexedAt;
        }


    public String id() {
        return id;
    }


    public String auditEventId() {
        return auditEventId;
    }


    public String sourceModule() {
        return sourceModule;
    }


    public String eventCategoryCode() {
        return eventCategoryCode;
    }


    public String eventTypeCode() {
        return eventTypeCode;
    }


    public String actionCode() {
        return actionCode;
    }


    public String actorId() {
        return actorId;
    }


    public String actorDisplayNameSearch() {
        return actorDisplayNameSearch;
    }


    public String organizationUnitId() {
        return organizationUnitId;
    }


    public String targetModule() {
        return targetModule;
    }


    public String targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public String targetSearchText() {
        return targetSearchText;
    }


    public String decisionCode() {
        return decisionCode;
    }


    public String correlationId() {
        return correlationId;
    }


    public String requestId() {
        return requestId;
    }


    public Instant occurredAt() {
        return occurredAt;
    }


    public Instant recordedAt() {
        return recordedAt;
    }


    public Instant indexedAt() {
        return indexedAt;
    }

    }
