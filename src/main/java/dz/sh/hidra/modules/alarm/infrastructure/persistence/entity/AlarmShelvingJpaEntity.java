/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmShelvingJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AlarmShelving.
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
     * Database-backed JPA entity for AlarmShelving.
     */
    @Entity
    @Table(name = "hidra_alarm_shelving")
    public class AlarmShelvingJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "alarm_id", nullable = false, length = 80)
    private String alarmId;

    @Column(name = "shelving_reason_id", nullable = false, length = 80)
    private String shelvingReasonId;

    @Column(name = "reason_text", nullable = true, columnDefinition = "text")
    private String reasonText;

    @Column(name = "shelved_by_actor_id", nullable = false, length = 80)
    private String shelvedByActorId;

    @Column(name = "shelved_at", nullable = false)
    private Instant shelvedAt;

    @Column(name = "shelved_until", nullable = false)
    private Instant shelvedUntil;

    @Column(name = "unshelved_at", nullable = true)
    private Instant unshelvedAt;

    @Column(name = "unshelved_by_actor_id", nullable = true, length = 80)
    private String unshelvedByActorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private AlarmShelvingStatus status;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

        protected AlarmShelvingJpaEntity() {
            // Required by JPA.
        }

        public AlarmShelvingJpaEntity(
                String id,
            String alarmId,
            String shelvingReasonId,
            String reasonText,
            String shelvedByActorId,
            Instant shelvedAt,
            Instant shelvedUntil,
            Instant unshelvedAt,
            String unshelvedByActorId,
            AlarmShelvingStatus status,
            String correlationId
        ) {
            this.id = id;
        this.alarmId = alarmId;
        this.shelvingReasonId = shelvingReasonId;
        this.reasonText = reasonText;
        this.shelvedByActorId = shelvedByActorId;
        this.shelvedAt = shelvedAt;
        this.shelvedUntil = shelvedUntil;
        this.unshelvedAt = unshelvedAt;
        this.unshelvedByActorId = unshelvedByActorId;
        this.status = status;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String alarmId() {
        return alarmId;
    }


    public String shelvingReasonId() {
        return shelvingReasonId;
    }


    public String reasonText() {
        return reasonText;
    }


    public String shelvedByActorId() {
        return shelvedByActorId;
    }


    public Instant shelvedAt() {
        return shelvedAt;
    }


    public Instant shelvedUntil() {
        return shelvedUntil;
    }


    public Instant unshelvedAt() {
        return unshelvedAt;
    }


    public String unshelvedByActorId() {
        return unshelvedByActorId;
    }


    public AlarmShelvingStatus status() {
        return status;
    }


    public String correlationId() {
        return correlationId;
    }

    }
