/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmClosureJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AlarmClosure.
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
     * Database-backed JPA entity for AlarmClosure.
     */
    @Entity
    @Table(name = "hidra_alarm_closure")
    public class AlarmClosureJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "alarm_id", nullable = false, length = 80)
    private String alarmId;

    @Enumerated(EnumType.STRING)
    @Column(name = "closure_type", nullable = false, length = 80)
    private AlarmClosureType closureType;

    @Column(name = "closure_reason_id", nullable = true, length = 80)
    private String closureReasonId;

    @Column(name = "closure_comment", nullable = true, columnDefinition = "text")
    private String closureComment;

    @Column(name = "closed_by_actor_id", nullable = false, length = 80)
    private String closedByActorId;

    @Column(name = "closed_at", nullable = false)
    private Instant closedAt;

    @Column(name = "requires_review", nullable = false)
    private boolean requiresReview;

    @Column(name = "review_workflow_instance_id", nullable = true, length = 80)
    private String reviewWorkflowInstanceId;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

        protected AlarmClosureJpaEntity() {
            // Required by JPA.
        }

        public AlarmClosureJpaEntity(
                String id,
            String alarmId,
            AlarmClosureType closureType,
            String closureReasonId,
            String closureComment,
            String closedByActorId,
            Instant closedAt,
            boolean requiresReview,
            String reviewWorkflowInstanceId,
            String correlationId
        ) {
            this.id = id;
        this.alarmId = alarmId;
        this.closureType = closureType;
        this.closureReasonId = closureReasonId;
        this.closureComment = closureComment;
        this.closedByActorId = closedByActorId;
        this.closedAt = closedAt;
        this.requiresReview = requiresReview;
        this.reviewWorkflowInstanceId = reviewWorkflowInstanceId;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String alarmId() {
        return alarmId;
    }


    public AlarmClosureType closureType() {
        return closureType;
    }


    public String closureReasonId() {
        return closureReasonId;
    }


    public String closureComment() {
        return closureComment;
    }


    public String closedByActorId() {
        return closedByActorId;
    }


    public Instant closedAt() {
        return closedAt;
    }


    public boolean requiresReview() {
        return requiresReview;
    }


    public String reviewWorkflowInstanceId() {
        return reviewWorkflowInstanceId;
    }


    public String correlationId() {
        return correlationId;
    }

    }
