/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmEscalationJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AlarmEscalation.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.entity;

import dz.sh.hidra.modules.alarm.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AlarmEscalation.
     */
    @Entity
    @Table(name = "hidra_alarm_escalation")
    public class AlarmEscalationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "alarm_id", nullable = false, length = 80)
    private String alarmId;

    @Column(name = "escalation_level", nullable = false)
    private Integer escalationLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "escalation_type", nullable = false, length = 80)
    private AlarmEscalationType escalationType;

    @Column(name = "target_organization_unit_id", nullable = true, length = 80)
    private String targetOrganizationUnitId;

    @Column(name = "target_actor_id", nullable = true, length = 80)
    private String targetActorId;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

    @Column(name = "incident_id", nullable = true, length = 80)
    private String incidentId;

    @Column(name = "reason_id", nullable = true, length = 80)
    private String reasonId;

    @Column(name = "reason_text", nullable = true, columnDefinition = "text")
    private String reasonText;

    @Column(name = "escalated_by_actor_id", nullable = false, length = 80)
    private String escalatedByActorId;

    @Column(name = "escalated_at", nullable = false)
    private Instant escalatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private AlarmEscalationStatus status;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

        protected AlarmEscalationJpaEntity() {
            // Required by JPA.
        }

        public AlarmEscalationJpaEntity(
                String id,
            String alarmId,
            Integer escalationLevel,
            AlarmEscalationType escalationType,
            String targetOrganizationUnitId,
            String targetActorId,
            String workflowInstanceId,
            String incidentId,
            String reasonId,
            String reasonText,
            String escalatedByActorId,
            Instant escalatedAt,
            AlarmEscalationStatus status,
            String correlationId
        ) {
            this.id = id;
        this.alarmId = alarmId;
        this.escalationLevel = escalationLevel;
        this.escalationType = escalationType;
        this.targetOrganizationUnitId = targetOrganizationUnitId;
        this.targetActorId = targetActorId;
        this.workflowInstanceId = workflowInstanceId;
        this.incidentId = incidentId;
        this.reasonId = reasonId;
        this.reasonText = reasonText;
        this.escalatedByActorId = escalatedByActorId;
        this.escalatedAt = escalatedAt;
        this.status = status;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String alarmId() {
        return alarmId;
    }


    public Integer escalationLevel() {
        return escalationLevel;
    }


    public AlarmEscalationType escalationType() {
        return escalationType;
    }


    public String targetOrganizationUnitId() {
        return targetOrganizationUnitId;
    }


    public String targetActorId() {
        return targetActorId;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public String incidentId() {
        return incidentId;
    }


    public String reasonId() {
        return reasonId;
    }


    public String reasonText() {
        return reasonText;
    }


    public String escalatedByActorId() {
        return escalatedByActorId;
    }


    public Instant escalatedAt() {
        return escalatedAt;
    }


    public AlarmEscalationStatus status() {
        return status;
    }


    public String correlationId() {
        return correlationId;
    }

    }
