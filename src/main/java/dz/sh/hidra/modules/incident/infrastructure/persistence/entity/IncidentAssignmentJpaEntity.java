/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentAssignmentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IncidentAssignment.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for IncidentAssignment.
     */
    @Entity
    @Table(name = "hidra_incident_assignment")
    public class IncidentAssignmentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "incident_id", nullable = false, length = 80)
    private String incidentId;

    @Column(name = "assignment_type_id", nullable = false, length = 80)
    private String assignmentTypeId;

    @Column(name = "assigned_organization_unit_id", nullable = true, length = 80)
    private String assignedOrganizationUnitId;

    @Column(name = "assigned_organization_unit_name_snapshot", nullable = true, length = 500)
    private String assignedOrganizationUnitNameSnapshot;

    @Column(name = "assigned_actor_id", nullable = true, length = 80)
    private String assignedActorId;

    @Column(name = "assigned_actor_name_snapshot", nullable = true, length = 255)
    private String assignedActorNameSnapshot;

    @Column(name = "assigned_by_actor_id", nullable = false, length = 80)
    private String assignedByActorId;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;

    @Column(name = "accepted_at", nullable = true)
    private Instant acceptedAt;

    @Column(name = "released_at", nullable = true)
    private Instant releasedAt;

    @Column(name = "release_reason", nullable = true, columnDefinition = "text")
    private String releaseReason;

    @Column(name = "primary_assignment", nullable = false)
    private boolean primaryAssignment;

        protected IncidentAssignmentJpaEntity() {
            // Required by JPA.
        }

        public IncidentAssignmentJpaEntity(
                String id,
            String incidentId,
            String assignmentTypeId,
            String assignedOrganizationUnitId,
            String assignedOrganizationUnitNameSnapshot,
            String assignedActorId,
            String assignedActorNameSnapshot,
            String assignedByActorId,
            Instant assignedAt,
            Instant acceptedAt,
            Instant releasedAt,
            String releaseReason,
            boolean primaryAssignment
        ) {
            this.id = id;
        this.incidentId = incidentId;
        this.assignmentTypeId = assignmentTypeId;
        this.assignedOrganizationUnitId = assignedOrganizationUnitId;
        this.assignedOrganizationUnitNameSnapshot = assignedOrganizationUnitNameSnapshot;
        this.assignedActorId = assignedActorId;
        this.assignedActorNameSnapshot = assignedActorNameSnapshot;
        this.assignedByActorId = assignedByActorId;
        this.assignedAt = assignedAt;
        this.acceptedAt = acceptedAt;
        this.releasedAt = releasedAt;
        this.releaseReason = releaseReason;
        this.primaryAssignment = primaryAssignment;
        }


    public String id() {
        return id;
    }


    public String incidentId() {
        return incidentId;
    }


    public String assignmentTypeId() {
        return assignmentTypeId;
    }


    public String assignedOrganizationUnitId() {
        return assignedOrganizationUnitId;
    }


    public String assignedOrganizationUnitNameSnapshot() {
        return assignedOrganizationUnitNameSnapshot;
    }


    public String assignedActorId() {
        return assignedActorId;
    }


    public String assignedActorNameSnapshot() {
        return assignedActorNameSnapshot;
    }


    public String assignedByActorId() {
        return assignedByActorId;
    }


    public Instant assignedAt() {
        return assignedAt;
    }


    public Instant acceptedAt() {
        return acceptedAt;
    }


    public Instant releasedAt() {
        return releasedAt;
    }


    public String releaseReason() {
        return releaseReason;
    }


    public boolean primaryAssignment() {
        return primaryAssignment;
    }

    }
