/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseStatusHistoryJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for HseCaseStatusHistory.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.entity;

import dz.sh.hidra.modules.hse.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for HseCaseStatusHistory.
     */
    @Entity
    @Table(name = "hidra_hse_case_status_history")
    public class HseCaseStatusHistoryJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "hse_case_id", nullable = false, length = 80)
    private String hseCaseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status", nullable = true, length = 40)
    private HseCaseStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 40)
    private HseCaseStatus newStatus;

    @Column(name = "reason_id", nullable = true, length = 80)
    private String reasonId;

    @Column(name = "reason_text", nullable = true, columnDefinition = "text")
    private String reasonText;

    @Column(name = "changed_by_actor_id", nullable = true, length = 80)
    private String changedByActorId;

    @Column(name = "changed_by_display_name_snapshot", nullable = true, length = 255)
    private String changedByDisplayNameSnapshot;

    @Column(name = "changed_at", nullable = false)
    private Instant changedAt;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

        protected HseCaseStatusHistoryJpaEntity() {
            // Required by JPA.
        }

        public HseCaseStatusHistoryJpaEntity(
                String id,
            String hseCaseId,
            HseCaseStatus oldStatus,
            HseCaseStatus newStatus,
            String reasonId,
            String reasonText,
            String changedByActorId,
            String changedByDisplayNameSnapshot,
            Instant changedAt,
            String correlationId
        ) {
            this.id = id;
        this.hseCaseId = hseCaseId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.reasonId = reasonId;
        this.reasonText = reasonText;
        this.changedByActorId = changedByActorId;
        this.changedByDisplayNameSnapshot = changedByDisplayNameSnapshot;
        this.changedAt = changedAt;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String hseCaseId() {
        return hseCaseId;
    }


    public HseCaseStatus oldStatus() {
        return oldStatus;
    }


    public HseCaseStatus newStatus() {
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


    public String changedByDisplayNameSnapshot() {
        return changedByDisplayNameSnapshot;
    }


    public Instant changedAt() {
        return changedAt;
    }


    public String correlationId() {
        return correlationId;
    }

    }
