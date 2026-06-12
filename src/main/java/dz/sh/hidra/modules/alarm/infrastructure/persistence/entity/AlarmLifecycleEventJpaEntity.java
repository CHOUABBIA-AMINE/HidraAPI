/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmLifecycleEventJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AlarmLifecycleEvent.
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
     * Database-backed JPA entity for AlarmLifecycleEvent.
     */
    @Entity
    @Table(name = "hidra_alarm_lifecycle_event")
    public class AlarmLifecycleEventJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "alarm_id", nullable = false, length = 80)
    private String alarmId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 80)
    private AlarmLifecycleEventType eventType;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_state", nullable = true, length = 40)
    private AlarmState previousState;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_state", nullable = false, length = 40)
    private AlarmState newState;

    @Column(name = "reason_id", nullable = true, length = 80)
    private String reasonId;

    @Column(name = "reason_text", nullable = true, columnDefinition = "text")
    private String reasonText;

    @Column(name = "actor_id", nullable = false, length = 80)
    private String actorId;

    @Column(name = "actor_display_name", nullable = true, length = 255)
    private String actorDisplayName;

    @Column(name = "organization_unit_id", nullable = true, length = 80)
    private String organizationUnitId;

    @Column(name = "organization_unit_code", nullable = true, length = 160)
    private String organizationUnitCode;

    @Column(name = "organization_unit_name_snapshot", nullable = true, length = 500)
    private String organizationUnitNameSnapshot;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

    @Column(name = "metadata_json", nullable = true, columnDefinition = "jsonb")
    private String metadataJson;

        protected AlarmLifecycleEventJpaEntity() {
            // Required by JPA.
        }

        public AlarmLifecycleEventJpaEntity(
                String id,
            String alarmId,
            AlarmLifecycleEventType eventType,
            AlarmState previousState,
            AlarmState newState,
            String reasonId,
            String reasonText,
            String actorId,
            String actorDisplayName,
            String organizationUnitId,
            String organizationUnitCode,
            String organizationUnitNameSnapshot,
            Instant occurredAt,
            String correlationId,
            String metadataJson
        ) {
            this.id = id;
        this.alarmId = alarmId;
        this.eventType = eventType;
        this.previousState = previousState;
        this.newState = newState;
        this.reasonId = reasonId;
        this.reasonText = reasonText;
        this.actorId = actorId;
        this.actorDisplayName = actorDisplayName;
        this.organizationUnitId = organizationUnitId;
        this.organizationUnitCode = organizationUnitCode;
        this.organizationUnitNameSnapshot = organizationUnitNameSnapshot;
        this.occurredAt = occurredAt;
        this.correlationId = correlationId;
        this.metadataJson = metadataJson;
        }


    public String id() {
        return id;
    }


    public String alarmId() {
        return alarmId;
    }


    public AlarmLifecycleEventType eventType() {
        return eventType;
    }


    public AlarmState previousState() {
        return previousState;
    }


    public AlarmState newState() {
        return newState;
    }


    public String reasonId() {
        return reasonId;
    }


    public String reasonText() {
        return reasonText;
    }


    public String actorId() {
        return actorId;
    }


    public String actorDisplayName() {
        return actorDisplayName;
    }


    public String organizationUnitId() {
        return organizationUnitId;
    }


    public String organizationUnitCode() {
        return organizationUnitCode;
    }


    public String organizationUnitNameSnapshot() {
        return organizationUnitNameSnapshot;
    }


    public Instant occurredAt() {
        return occurredAt;
    }


    public String correlationId() {
        return correlationId;
    }


    public String metadataJson() {
        return metadataJson;
    }

    }
