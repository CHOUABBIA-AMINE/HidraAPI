/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AlarmSuppression.
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
     * Database-backed JPA entity for AlarmSuppression.
     */
    @Entity
    @Table(name = "hidra_alarm_suppression")
    public class AlarmSuppressionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope_type", nullable = false, length = 80)
    private AlarmSuppressionScopeType scopeType;

    @Column(name = "scope_reference_id", nullable = false, length = 80)
    private String scopeReferenceId;

    @Column(name = "alarm_id", nullable = true, length = 80)
    private String alarmId;

    @Column(name = "alarm_type_id", nullable = true, length = 80)
    private String alarmTypeId;

    @Column(name = "topology_asset_type_code", nullable = true, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = true, length = 80)
    private String topologyAssetId;

    @Column(name = "suppression_reason_id", nullable = false, length = 80)
    private String suppressionReasonId;

    @Column(name = "reason_text", nullable = true, columnDefinition = "text")
    private String reasonText;

    @Column(name = "suppressed_by_actor_id", nullable = false, length = 80)
    private String suppressedByActorId;

    @Column(name = "suppressed_at", nullable = false)
    private Instant suppressedAt;

    @Column(name = "suppressed_until", nullable = true)
    private Instant suppressedUntil;

    @Column(name = "released_at", nullable = true)
    private Instant releasedAt;

    @Column(name = "released_by_actor_id", nullable = true, length = 80)
    private String releasedByActorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private AlarmSuppressionStatus status;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

        protected AlarmSuppressionJpaEntity() {
            // Required by JPA.
        }

        public AlarmSuppressionJpaEntity(
                String id,
            AlarmSuppressionScopeType scopeType,
            String scopeReferenceId,
            String alarmId,
            String alarmTypeId,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String suppressionReasonId,
            String reasonText,
            String suppressedByActorId,
            Instant suppressedAt,
            Instant suppressedUntil,
            Instant releasedAt,
            String releasedByActorId,
            AlarmSuppressionStatus status,
            String workflowInstanceId,
            String correlationId
        ) {
            this.id = id;
        this.scopeType = scopeType;
        this.scopeReferenceId = scopeReferenceId;
        this.alarmId = alarmId;
        this.alarmTypeId = alarmTypeId;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.suppressionReasonId = suppressionReasonId;
        this.reasonText = reasonText;
        this.suppressedByActorId = suppressedByActorId;
        this.suppressedAt = suppressedAt;
        this.suppressedUntil = suppressedUntil;
        this.releasedAt = releasedAt;
        this.releasedByActorId = releasedByActorId;
        this.status = status;
        this.workflowInstanceId = workflowInstanceId;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public AlarmSuppressionScopeType scopeType() {
        return scopeType;
    }


    public String scopeReferenceId() {
        return scopeReferenceId;
    }


    public String alarmId() {
        return alarmId;
    }


    public String alarmTypeId() {
        return alarmTypeId;
    }


    public String topologyAssetTypeCode() {
        return topologyAssetTypeCode;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String suppressionReasonId() {
        return suppressionReasonId;
    }


    public String reasonText() {
        return reasonText;
    }


    public String suppressedByActorId() {
        return suppressedByActorId;
    }


    public Instant suppressedAt() {
        return suppressedAt;
    }


    public Instant suppressedUntil() {
        return suppressedUntil;
    }


    public Instant releasedAt() {
        return releasedAt;
    }


    public String releasedByActorId() {
        return releasedByActorId;
    }


    public AlarmSuppressionStatus status() {
        return status;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public String correlationId() {
        return correlationId;
    }

    }
