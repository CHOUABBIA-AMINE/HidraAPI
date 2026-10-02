/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyStatusHistoryJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyStatusHistory.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.entity;

import dz.sh.hidra.modules.party.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for PartyStatusHistory.
     */
    @Entity
    @Table(name = "hidra_party_status_history")
    public class PartyStatusHistoryJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status", nullable = true, length = 40)
    private PartyStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 40)
    private PartyStatus newStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false, length = 80)
    private StatusChangeReason reason;

    @Column(name = "reason_message", nullable = true, columnDefinition = "text")
    private String reasonMessage;

    @Column(name = "changed_by_actor_id", nullable = true, length = 120)
    private String changedByActorId;

    @Column(name = "changed_at", nullable = false)
    private Instant changedAt;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

        protected PartyStatusHistoryJpaEntity() {
            // Required by JPA.
        }

        public PartyStatusHistoryJpaEntity(
                String id,
            String partyId,
            PartyStatus oldStatus,
            PartyStatus newStatus,
            StatusChangeReason reason,
            String reasonMessage,
            String changedByActorId,
            Instant changedAt,
            String correlationId
        ) {
            this.id = id;
        this.partyId = partyId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.reason = reason;
        this.reasonMessage = reasonMessage;
        this.changedByActorId = changedByActorId;
        this.changedAt = changedAt;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String partyId() {
        return partyId;
    }


    public PartyStatus oldStatus() {
        return oldStatus;
    }


    public PartyStatus newStatus() {
        return newStatus;
    }


    public StatusChangeReason reason() {
        return reason;
    }


    public String reasonMessage() {
        return reasonMessage;
    }


    public String changedByActorId() {
        return changedByActorId;
    }


    public Instant changedAt() {
        return changedAt;
    }


    public String correlationId() {
        return correlationId;
    }

    }
