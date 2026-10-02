/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentRelatedIncidentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IncidentRelatedIncident.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for IncidentRelatedIncident.
     */
    @Entity
    @Table(name = "hidra_incident_related_incident")
    public class IncidentRelatedIncidentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "incident_id", nullable = false, length = 80)
    private String incidentId;

    @Column(name = "related_incident_id", nullable = false, length = 80)
    private String relatedIncidentId;

    @Column(name = "relationship_type_id", nullable = false, length = 80)
    private String relationshipTypeId;

    @Column(name = "comment", nullable = true, columnDefinition = "text")
    private String comment;

    @Column(name = "created_by_actor_id", nullable = false, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected IncidentRelatedIncidentJpaEntity() {
            // Required by JPA.
        }

        public IncidentRelatedIncidentJpaEntity(
                String id,
            String incidentId,
            String relatedIncidentId,
            String relationshipTypeId,
            String comment,
            String createdByActorId,
            Instant createdAt
        ) {
            this.id = id;
        this.incidentId = incidentId;
        this.relatedIncidentId = relatedIncidentId;
        this.relationshipTypeId = relationshipTypeId;
        this.comment = comment;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String incidentId() {
        return incidentId;
    }


    public String relatedIncidentId() {
        return relatedIncidentId;
    }


    public String relationshipTypeId() {
        return relationshipTypeId;
    }


    public String comment() {
        return comment;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
