/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringAcknowledgementJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MonitoringAcknowledgement.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity;

import dz.sh.hidra.modules.monitoring.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for MonitoringAcknowledgement.
     */
    @Entity
    @Table(name = "hidra_monitoring_acknowledgement")
    public class MonitoringAcknowledgementJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "target_type", nullable = false, length = 80)
    private String targetType;

    @Column(name = "target_id", nullable = false, length = 80)
    private String targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "acknowledgement_status", nullable = false, length = 40)
    private AcknowledgementStatus acknowledgementStatus;

    @Column(name = "acknowledged_by_actor_id", nullable = false, length = 80)
    private String acknowledgedByActorId;

    @Column(name = "acknowledged_at", nullable = false)
    private Instant acknowledgedAt;

    @Column(name = "comment", nullable = true, columnDefinition = "text")
    private String comment;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

        protected MonitoringAcknowledgementJpaEntity() {
            // Required by JPA.
        }

        public MonitoringAcknowledgementJpaEntity(
                String id,
            String targetType,
            String targetId,
            AcknowledgementStatus acknowledgementStatus,
            String acknowledgedByActorId,
            Instant acknowledgedAt,
            String comment,
            String workflowInstanceId,
            String correlationId
        ) {
            this.id = id;
        this.targetType = targetType;
        this.targetId = targetId;
        this.acknowledgementStatus = acknowledgementStatus;
        this.acknowledgedByActorId = acknowledgedByActorId;
        this.acknowledgedAt = acknowledgedAt;
        this.comment = comment;
        this.workflowInstanceId = workflowInstanceId;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public AcknowledgementStatus acknowledgementStatus() {
        return acknowledgementStatus;
    }


    public String acknowledgedByActorId() {
        return acknowledgedByActorId;
    }


    public Instant acknowledgedAt() {
        return acknowledgedAt;
    }


    public String comment() {
        return comment;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public String correlationId() {
        return correlationId;
    }

    }
