/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakEscalationReferenceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for LeakEscalationReference.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity;

import dz.sh.hidra.modules.leakdetection.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for LeakEscalationReference.
     */
    @Entity
    @Table(name = "hidra_leak_detection_escalation_reference")
    public class LeakEscalationReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "case_id", nullable = false, length = 80)
    private String caseId;

    @Column(name = "candidate_id", nullable = true, length = 80)
    private String candidateId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 80)
    private LeakEscalationTargetType targetType;

    @Column(name = "target_reference_id", nullable = false, length = 80)
    private String targetReferenceId;

    @Column(name = "target_code_snapshot", nullable = true, length = 160)
    private String targetCodeSnapshot;

    @Column(name = "target_name_snapshot", nullable = true, length = 500)
    private String targetNameSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private LeakEscalationStatus status;

    @Column(name = "escalated_by_actor_id", nullable = true, length = 80)
    private String escalatedByActorId;

    @Column(name = "escalated_at", nullable = false)
    private Instant escalatedAt;

    @Column(name = "reason_text", nullable = true, columnDefinition = "text")
    private String reasonText;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

        protected LeakEscalationReferenceJpaEntity() {
            // Required by JPA.
        }

        public LeakEscalationReferenceJpaEntity(
                String id,
            String caseId,
            String candidateId,
            LeakEscalationTargetType targetType,
            String targetReferenceId,
            String targetCodeSnapshot,
            String targetNameSnapshot,
            LeakEscalationStatus status,
            String escalatedByActorId,
            Instant escalatedAt,
            String reasonText,
            String correlationId
        ) {
            this.id = id;
        this.caseId = caseId;
        this.candidateId = candidateId;
        this.targetType = targetType;
        this.targetReferenceId = targetReferenceId;
        this.targetCodeSnapshot = targetCodeSnapshot;
        this.targetNameSnapshot = targetNameSnapshot;
        this.status = status;
        this.escalatedByActorId = escalatedByActorId;
        this.escalatedAt = escalatedAt;
        this.reasonText = reasonText;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String caseId() {
        return caseId;
    }


    public String candidateId() {
        return candidateId;
    }


    public LeakEscalationTargetType targetType() {
        return targetType;
    }


    public String targetReferenceId() {
        return targetReferenceId;
    }


    public String targetCodeSnapshot() {
        return targetCodeSnapshot;
    }


    public String targetNameSnapshot() {
        return targetNameSnapshot;
    }


    public LeakEscalationStatus status() {
        return status;
    }


    public String escalatedByActorId() {
        return escalatedByActorId;
    }


    public Instant escalatedAt() {
        return escalatedAt;
    }


    public String reasonText() {
        return reasonText;
    }


    public String correlationId() {
        return correlationId;
    }

    }
