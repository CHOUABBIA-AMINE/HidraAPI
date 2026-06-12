/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentTimelineEntryJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IncidentTimelineEntry.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.entity;

import dz.sh.hidra.modules.incident.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IncidentTimelineEntry.
     */
    @Entity
    @Table(name = "hidra_incident_timeline_entry")
    public class IncidentTimelineEntryJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "incident_id", nullable = false, length = 80)
    private String incidentId;

    @Column(name = "entry_type_id", nullable = false, length = 80)
    private String entryTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_before", nullable = true, length = 40)
    private IncidentStatus statusBefore;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_after", nullable = true, length = 40)
    private IncidentStatus statusAfter;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "actor_id", nullable = true, length = 80)
    private String actorId;

    @Column(name = "actor_name_snapshot", nullable = true, length = 255)
    private String actorNameSnapshot;

    @Column(name = "organization_unit_id", nullable = true, length = 80)
    private String organizationUnitId;

    @Column(name = "organization_unit_name_snapshot", nullable = true, length = 500)
    private String organizationUnitNameSnapshot;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

        protected IncidentTimelineEntryJpaEntity() {
            // Required by JPA.
        }

        public IncidentTimelineEntryJpaEntity(
                String id,
            String incidentId,
            String entryTypeId,
            IncidentStatus statusBefore,
            IncidentStatus statusAfter,
            String title,
            String description,
            String actorId,
            String actorNameSnapshot,
            String organizationUnitId,
            String organizationUnitNameSnapshot,
            Instant occurredAt,
            Instant recordedAt,
            String correlationId
        ) {
            this.id = id;
        this.incidentId = incidentId;
        this.entryTypeId = entryTypeId;
        this.statusBefore = statusBefore;
        this.statusAfter = statusAfter;
        this.title = title;
        this.description = description;
        this.actorId = actorId;
        this.actorNameSnapshot = actorNameSnapshot;
        this.organizationUnitId = organizationUnitId;
        this.organizationUnitNameSnapshot = organizationUnitNameSnapshot;
        this.occurredAt = occurredAt;
        this.recordedAt = recordedAt;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String incidentId() {
        return incidentId;
    }


    public String entryTypeId() {
        return entryTypeId;
    }


    public IncidentStatus statusBefore() {
        return statusBefore;
    }


    public IncidentStatus statusAfter() {
        return statusAfter;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String actorId() {
        return actorId;
    }


    public String actorNameSnapshot() {
        return actorNameSnapshot;
    }


    public String organizationUnitId() {
        return organizationUnitId;
    }


    public String organizationUnitNameSnapshot() {
        return organizationUnitNameSnapshot;
    }


    public Instant occurredAt() {
        return occurredAt;
    }


    public Instant recordedAt() {
        return recordedAt;
    }


    public String correlationId() {
        return correlationId;
    }

    }
