/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentEscalationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IncidentEscalation.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for IncidentEscalation.
     */
    @Entity
    @Table(name = "hidra_incident_escalation")
    public class IncidentEscalationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "incident_id", nullable = false, length = 80)
    private String incidentId;

    @Column(name = "from_level", nullable = false)
    private int fromLevel;

    @Column(name = "to_level", nullable = false)
    private int toLevel;

    @Column(name = "reason_id", nullable = false, length = 80)
    private String reasonId;

    @Column(name = "reason_comment", nullable = true, columnDefinition = "text")
    private String reasonComment;

    @Column(name = "escalated_to_organization_unit_id", nullable = true, length = 80)
    private String escalatedToOrganizationUnitId;

    @Column(name = "escalated_to_actor_id", nullable = true, length = 80)
    private String escalatedToActorId;

    @Column(name = "escalated_by_actor_id", nullable = false, length = 80)
    private String escalatedByActorId;

    @Column(name = "escalated_at", nullable = false)
    private Instant escalatedAt;

    @Column(name = "acknowledged_at", nullable = true)
    private Instant acknowledgedAt;

        protected IncidentEscalationJpaEntity() {
            // Required by JPA.
        }

        public IncidentEscalationJpaEntity(
                String id,
            String incidentId,
            int fromLevel,
            int toLevel,
            String reasonId,
            String reasonComment,
            String escalatedToOrganizationUnitId,
            String escalatedToActorId,
            String escalatedByActorId,
            Instant escalatedAt,
            Instant acknowledgedAt
        ) {
            this.id = id;
        this.incidentId = incidentId;
        this.fromLevel = fromLevel;
        this.toLevel = toLevel;
        this.reasonId = reasonId;
        this.reasonComment = reasonComment;
        this.escalatedToOrganizationUnitId = escalatedToOrganizationUnitId;
        this.escalatedToActorId = escalatedToActorId;
        this.escalatedByActorId = escalatedByActorId;
        this.escalatedAt = escalatedAt;
        this.acknowledgedAt = acknowledgedAt;
        }


    public String id() {
        return id;
    }


    public String incidentId() {
        return incidentId;
    }


    public int fromLevel() {
        return fromLevel;
    }


    public int toLevel() {
        return toLevel;
    }


    public String reasonId() {
        return reasonId;
    }


    public String reasonComment() {
        return reasonComment;
    }


    public String escalatedToOrganizationUnitId() {
        return escalatedToOrganizationUnitId;
    }


    public String escalatedToActorId() {
        return escalatedToActorId;
    }


    public String escalatedByActorId() {
        return escalatedByActorId;
    }


    public Instant escalatedAt() {
        return escalatedAt;
    }


    public Instant acknowledgedAt() {
        return acknowledgedAt;
    }

    }
