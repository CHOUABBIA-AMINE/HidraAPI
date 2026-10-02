/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentResolutionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IncidentResolution.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for IncidentResolution.
     */
    @Entity
    @Table(name = "hidra_incident_resolution")
    public class IncidentResolutionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "incident_id", nullable = false, length = 80)
    private String incidentId;

    @Column(name = "resolution_type_id", nullable = false, length = 80)
    private String resolutionTypeId;

    @Column(name = "resolution_summary", nullable = false, columnDefinition = "text")
    private String resolutionSummary;

    @Column(name = "corrective_action_required", nullable = false)
    private boolean correctiveActionRequired;

    @Column(name = "preventive_action_required", nullable = false)
    private boolean preventiveActionRequired;

    @Column(name = "residual_risk_level_id", nullable = true, length = 80)
    private String residualRiskLevelId;

    @Column(name = "resolved_by_actor_id", nullable = false, length = 80)
    private String resolvedByActorId;

    @Column(name = "resolved_at", nullable = false)
    private Instant resolvedAt;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

        protected IncidentResolutionJpaEntity() {
            // Required by JPA.
        }

        public IncidentResolutionJpaEntity(
                String id,
            String incidentId,
            String resolutionTypeId,
            String resolutionSummary,
            boolean correctiveActionRequired,
            boolean preventiveActionRequired,
            String residualRiskLevelId,
            String resolvedByActorId,
            Instant resolvedAt,
            String workflowInstanceId
        ) {
            this.id = id;
        this.incidentId = incidentId;
        this.resolutionTypeId = resolutionTypeId;
        this.resolutionSummary = resolutionSummary;
        this.correctiveActionRequired = correctiveActionRequired;
        this.preventiveActionRequired = preventiveActionRequired;
        this.residualRiskLevelId = residualRiskLevelId;
        this.resolvedByActorId = resolvedByActorId;
        this.resolvedAt = resolvedAt;
        this.workflowInstanceId = workflowInstanceId;
        }


    public String id() {
        return id;
    }


    public String incidentId() {
        return incidentId;
    }


    public String resolutionTypeId() {
        return resolutionTypeId;
    }


    public String resolutionSummary() {
        return resolutionSummary;
    }


    public boolean correctiveActionRequired() {
        return correctiveActionRequired;
    }


    public boolean preventiveActionRequired() {
        return preventiveActionRequired;
    }


    public String residualRiskLevelId() {
        return residualRiskLevelId;
    }


    public String resolvedByActorId() {
        return resolvedByActorId;
    }


    public Instant resolvedAt() {
        return resolvedAt;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }

    }
