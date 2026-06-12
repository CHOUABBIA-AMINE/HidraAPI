/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmAcknowledgementJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AlarmAcknowledgement.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AlarmAcknowledgement.
     */
    @Entity
    @Table(name = "hidra_alarm_acknowledgement")
    public class AlarmAcknowledgementJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "alarm_id", nullable = false, length = 80)
    private String alarmId;

    @Column(name = "acknowledged_by_actor_id", nullable = false, length = 80)
    private String acknowledgedByActorId;

    @Column(name = "acknowledged_by_display_name", nullable = true, length = 255)
    private String acknowledgedByDisplayName;

    @Column(name = "organization_unit_id", nullable = true, length = 80)
    private String organizationUnitId;

    @Column(name = "organization_unit_code", nullable = true, length = 160)
    private String organizationUnitCode;

    @Column(name = "acknowledged_at", nullable = false)
    private Instant acknowledgedAt;

    @Column(name = "comment", nullable = true, columnDefinition = "text")
    private String comment;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

        protected AlarmAcknowledgementJpaEntity() {
            // Required by JPA.
        }

        public AlarmAcknowledgementJpaEntity(
                String id,
            String alarmId,
            String acknowledgedByActorId,
            String acknowledgedByDisplayName,
            String organizationUnitId,
            String organizationUnitCode,
            Instant acknowledgedAt,
            String comment,
            String correlationId
        ) {
            this.id = id;
        this.alarmId = alarmId;
        this.acknowledgedByActorId = acknowledgedByActorId;
        this.acknowledgedByDisplayName = acknowledgedByDisplayName;
        this.organizationUnitId = organizationUnitId;
        this.organizationUnitCode = organizationUnitCode;
        this.acknowledgedAt = acknowledgedAt;
        this.comment = comment;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String alarmId() {
        return alarmId;
    }


    public String acknowledgedByActorId() {
        return acknowledgedByActorId;
    }


    public String acknowledgedByDisplayName() {
        return acknowledgedByDisplayName;
    }


    public String organizationUnitId() {
        return organizationUnitId;
    }


    public String organizationUnitCode() {
        return organizationUnitCode;
    }


    public Instant acknowledgedAt() {
        return acknowledgedAt;
    }


    public String comment() {
        return comment;
    }


    public String correlationId() {
        return correlationId;
    }

    }
