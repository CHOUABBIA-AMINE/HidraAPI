/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseClosureJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for HseClosure.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for HseClosure.
     */
    @Entity
    @Table(name = "hidra_hse_closure")
    public class HseClosureJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "hse_case_id", nullable = false, length = 80)
    private String hseCaseId;

    @Column(name = "closure_summary", nullable = false, columnDefinition = "text")
    private String closureSummary;

    @Column(name = "impact_assessed", nullable = false)
    private boolean impactAssessed;

    @Column(name = "capa_completed", nullable = false)
    private boolean capaCompleted;

    @Column(name = "evidence_reviewed", nullable = false)
    private boolean evidenceReviewed;

    @Column(name = "regulatory_reviewed", nullable = false)
    private boolean regulatoryReviewed;

    @Column(name = "closed_by_actor_id", nullable = false, length = 80)
    private String closedByActorId;

    @Column(name = "closed_by_display_name_snapshot", nullable = true, length = 255)
    private String closedByDisplayNameSnapshot;

    @Column(name = "closed_at", nullable = false)
    private Instant closedAt;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

        protected HseClosureJpaEntity() {
            // Required by JPA.
        }

        public HseClosureJpaEntity(
                String id,
            String hseCaseId,
            String closureSummary,
            boolean impactAssessed,
            boolean capaCompleted,
            boolean evidenceReviewed,
            boolean regulatoryReviewed,
            String closedByActorId,
            String closedByDisplayNameSnapshot,
            Instant closedAt,
            String workflowInstanceId
        ) {
            this.id = id;
        this.hseCaseId = hseCaseId;
        this.closureSummary = closureSummary;
        this.impactAssessed = impactAssessed;
        this.capaCompleted = capaCompleted;
        this.evidenceReviewed = evidenceReviewed;
        this.regulatoryReviewed = regulatoryReviewed;
        this.closedByActorId = closedByActorId;
        this.closedByDisplayNameSnapshot = closedByDisplayNameSnapshot;
        this.closedAt = closedAt;
        this.workflowInstanceId = workflowInstanceId;
        }


    public String id() {
        return id;
    }


    public String hseCaseId() {
        return hseCaseId;
    }


    public String closureSummary() {
        return closureSummary;
    }


    public boolean impactAssessed() {
        return impactAssessed;
    }


    public boolean capaCompleted() {
        return capaCompleted;
    }


    public boolean evidenceReviewed() {
        return evidenceReviewed;
    }


    public boolean regulatoryReviewed() {
        return regulatoryReviewed;
    }


    public String closedByActorId() {
        return closedByActorId;
    }


    public String closedByDisplayNameSnapshot() {
        return closedByDisplayNameSnapshot;
    }


    public Instant closedAt() {
        return closedAt;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }

    }
