/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCaseStatusHistoryJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrityCaseStatusHistory.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integrity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IntegrityCaseStatusHistory.
     */
    @Entity
    @Table(name = "hidra_integrity_case_status_history")
    public class IntegrityCaseStatusHistoryJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "integrity_case_id", nullable = false, length = 80)
    private String integrityCaseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status", nullable = true, length = 40)
    private IntegrityCaseStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 40)
    private IntegrityCaseStatus newStatus;

    @Column(name = "reason_id", nullable = true, length = 80)
    private String reasonId;

    @Column(name = "reason_text", nullable = true, columnDefinition = "text")
    private String reasonText;

    @Column(name = "changed_by_actor_id", nullable = true, length = 80)
    private String changedByActorId;

    @Column(name = "changed_at", nullable = false)
    private Instant changedAt;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

        protected IntegrityCaseStatusHistoryJpaEntity() {
            // Required by JPA.
        }

        public IntegrityCaseStatusHistoryJpaEntity(
                String id,
            String integrityCaseId,
            IntegrityCaseStatus oldStatus,
            IntegrityCaseStatus newStatus,
            String reasonId,
            String reasonText,
            String changedByActorId,
            Instant changedAt,
            String correlationId
        ) {
            this.id = id;
        this.integrityCaseId = integrityCaseId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.reasonId = reasonId;
        this.reasonText = reasonText;
        this.changedByActorId = changedByActorId;
        this.changedAt = changedAt;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String integrityCaseId() {
        return integrityCaseId;
    }


    public IntegrityCaseStatus oldStatus() {
        return oldStatus;
    }


    public IntegrityCaseStatus newStatus() {
        return newStatus;
    }


    public String reasonId() {
        return reasonId;
    }


    public String reasonText() {
        return reasonText;
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
