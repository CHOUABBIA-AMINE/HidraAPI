/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentClosureJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IncidentClosure.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for IncidentClosure.
     */
    @Entity
    @Table(name = "hidra_incident_closure")
    public class IncidentClosureJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "incident_id", nullable = false, length = 80)
    private String incidentId;

    @Column(name = "closure_summary", nullable = false, columnDefinition = "text")
    private String closureSummary;

    @Column(name = "resolution_verified", nullable = false)
    private boolean resolutionVerified;

    @Column(name = "evidence_reviewed", nullable = false)
    private boolean evidenceReviewed;

    @Column(name = "root_cause_reviewed", nullable = false)
    private boolean rootCauseReviewed;

    @Column(name = "follow_up_actions_created", nullable = false)
    private boolean followUpActionsCreated;

    @Column(name = "closed_by_actor_id", nullable = false, length = 80)
    private String closedByActorId;

    @Column(name = "closed_by_actor_name_snapshot", nullable = true, length = 255)
    private String closedByActorNameSnapshot;

    @Column(name = "closed_at", nullable = false)
    private Instant closedAt;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

        protected IncidentClosureJpaEntity() {
            // Required by JPA.
        }

        public IncidentClosureJpaEntity(
                String id,
            String incidentId,
            String closureSummary,
            boolean resolutionVerified,
            boolean evidenceReviewed,
            boolean rootCauseReviewed,
            boolean followUpActionsCreated,
            String closedByActorId,
            String closedByActorNameSnapshot,
            Instant closedAt,
            String workflowInstanceId
        ) {
            this.id = id;
        this.incidentId = incidentId;
        this.closureSummary = closureSummary;
        this.resolutionVerified = resolutionVerified;
        this.evidenceReviewed = evidenceReviewed;
        this.rootCauseReviewed = rootCauseReviewed;
        this.followUpActionsCreated = followUpActionsCreated;
        this.closedByActorId = closedByActorId;
        this.closedByActorNameSnapshot = closedByActorNameSnapshot;
        this.closedAt = closedAt;
        this.workflowInstanceId = workflowInstanceId;
        }


    public String id() {
        return id;
    }


    public String incidentId() {
        return incidentId;
    }


    public String closureSummary() {
        return closureSummary;
    }


    public boolean resolutionVerified() {
        return resolutionVerified;
    }


    public boolean evidenceReviewed() {
        return evidenceReviewed;
    }


    public boolean rootCauseReviewed() {
        return rootCauseReviewed;
    }


    public boolean followUpActionsCreated() {
        return followUpActionsCreated;
    }


    public String closedByActorId() {
        return closedByActorId;
    }


    public String closedByActorNameSnapshot() {
        return closedByActorNameSnapshot;
    }


    public Instant closedAt() {
        return closedAt;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }

    }
