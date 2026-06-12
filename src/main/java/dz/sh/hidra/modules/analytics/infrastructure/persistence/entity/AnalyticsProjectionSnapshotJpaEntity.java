/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionSnapshotJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AnalyticsProjectionSnapshot.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.entity;

import dz.sh.hidra.modules.analytics.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AnalyticsProjectionSnapshot.
     */
    @Entity
    @Table(name = "hidra_analytics_projection_snapshot")
    public class AnalyticsProjectionSnapshotJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "projection_definition_id", nullable = false, length = 80)
    private String projectionDefinitionId;

    @Column(name = "projection_run_id", nullable = false, length = 80)
    private String projectionRunId;

    @Column(name = "snapshot_code", nullable = false, length = 120)
    private String snapshotCode;

    @Column(name = "period_start", nullable = true)
    private Instant periodStart;

    @Column(name = "period_end", nullable = true)
    private Instant periodEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "snapshot_status", nullable = false, length = 40)
    private AnalyticsSnapshotStatus snapshotStatus;

    @Column(name = "published_at", nullable = true)
    private Instant publishedAt;

    @Column(name = "published_by_actor_id", nullable = true, length = 80)
    private String publishedByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected AnalyticsProjectionSnapshotJpaEntity() {
            // Required by JPA.
        }

        public AnalyticsProjectionSnapshotJpaEntity(
                String id,
            String projectionDefinitionId,
            String projectionRunId,
            String snapshotCode,
            Instant periodStart,
            Instant periodEnd,
            AnalyticsSnapshotStatus snapshotStatus,
            Instant publishedAt,
            String publishedByActorId,
            Instant createdAt
        ) {
            this.id = id;
        this.projectionDefinitionId = projectionDefinitionId;
        this.projectionRunId = projectionRunId;
        this.snapshotCode = snapshotCode;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.snapshotStatus = snapshotStatus;
        this.publishedAt = publishedAt;
        this.publishedByActorId = publishedByActorId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String projectionDefinitionId() {
        return projectionDefinitionId;
    }


    public String projectionRunId() {
        return projectionRunId;
    }


    public String snapshotCode() {
        return snapshotCode;
    }


    public Instant periodStart() {
        return periodStart;
    }


    public Instant periodEnd() {
        return periodEnd;
    }


    public AnalyticsSnapshotStatus snapshotStatus() {
        return snapshotStatus;
    }


    public Instant publishedAt() {
        return publishedAt;
    }


    public String publishedByActorId() {
        return publishedByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
