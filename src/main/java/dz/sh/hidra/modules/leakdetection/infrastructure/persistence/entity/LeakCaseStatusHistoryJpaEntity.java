/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakCaseStatusHistoryJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for LeakCaseStatusHistory.
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
     * Database-backed JPA entity for LeakCaseStatusHistory.
     */
    @Entity
    @Table(name = "hidra_leak_detection_case_status_history")
    public class LeakCaseStatusHistoryJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "case_id", nullable = false, length = 80)
    private String caseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status", nullable = true, length = 40)
    private LeakDetectionCaseStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 40)
    private LeakDetectionCaseStatus newStatus;

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

        protected LeakCaseStatusHistoryJpaEntity() {
            // Required by JPA.
        }

        public LeakCaseStatusHistoryJpaEntity(
                String id,
            String caseId,
            LeakDetectionCaseStatus oldStatus,
            LeakDetectionCaseStatus newStatus,
            String reasonId,
            String reasonText,
            String changedByActorId,
            Instant changedAt,
            String correlationId
        ) {
            this.id = id;
        this.caseId = caseId;
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


    public String caseId() {
        return caseId;
    }


    public LeakDetectionCaseStatus oldStatus() {
        return oldStatus;
    }


    public LeakDetectionCaseStatus newStatus() {
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
