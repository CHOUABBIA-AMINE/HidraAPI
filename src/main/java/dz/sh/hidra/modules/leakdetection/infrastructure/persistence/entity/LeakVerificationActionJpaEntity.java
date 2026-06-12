/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakVerificationActionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for LeakVerificationAction.
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
     * Database-backed JPA entity for LeakVerificationAction.
     */
    @Entity
    @Table(name = "hidra_leak_detection_verification_action")
    public class LeakVerificationActionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "candidate_id", nullable = false, length = 80)
    private String candidateId;

    @Column(name = "case_id", nullable = true, length = 80)
    private String caseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false, length = 80)
    private LeakVerificationActionType actionType;

    @Column(name = "assigned_organization_unit_id", nullable = true, length = 80)
    private String assignedOrganizationUnitId;

    @Column(name = "assigned_actor_id", nullable = true, length = 80)
    private String assignedActorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private LeakVerificationStatus status;

    @Column(name = "requested_at", nullable = false)
    private Instant requestedAt;

    @Column(name = "started_at", nullable = true)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "result_text", nullable = true, columnDefinition = "text")
    private String resultText;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

        protected LeakVerificationActionJpaEntity() {
            // Required by JPA.
        }

        public LeakVerificationActionJpaEntity(
                String id,
            String candidateId,
            String caseId,
            LeakVerificationActionType actionType,
            String assignedOrganizationUnitId,
            String assignedActorId,
            LeakVerificationStatus status,
            Instant requestedAt,
            Instant startedAt,
            Instant completedAt,
            String resultText,
            String correlationId
        ) {
            this.id = id;
        this.candidateId = candidateId;
        this.caseId = caseId;
        this.actionType = actionType;
        this.assignedOrganizationUnitId = assignedOrganizationUnitId;
        this.assignedActorId = assignedActorId;
        this.status = status;
        this.requestedAt = requestedAt;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.resultText = resultText;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String candidateId() {
        return candidateId;
    }


    public String caseId() {
        return caseId;
    }


    public LeakVerificationActionType actionType() {
        return actionType;
    }


    public String assignedOrganizationUnitId() {
        return assignedOrganizationUnitId;
    }


    public String assignedActorId() {
        return assignedActorId;
    }


    public LeakVerificationStatus status() {
        return status;
    }


    public Instant requestedAt() {
        return requestedAt;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String resultText() {
        return resultText;
    }


    public String correlationId() {
        return correlationId;
    }

    }
