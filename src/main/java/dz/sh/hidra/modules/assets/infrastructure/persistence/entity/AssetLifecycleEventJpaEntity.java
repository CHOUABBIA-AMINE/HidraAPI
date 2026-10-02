/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetLifecycleEventJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AssetLifecycleEvent.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.entity;

import dz.sh.hidra.modules.assets.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AssetLifecycleEvent.
     */
    @Entity
    @Table(name = "hidra_asset_lifecycle_event")
    public class AssetLifecycleEventJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "maintainable_asset_id", nullable = false, length = 80)
    private String maintainableAssetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 80)
    private AssetLifecycleEventType eventType;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status", nullable = true, length = 40)
    private AssetLifecycleStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 40)
    private AssetLifecycleStatus newStatus;

    @Column(name = "event_reason_id", nullable = true, length = 80)
    private String eventReasonId;

    @Column(name = "event_comment", nullable = true, columnDefinition = "text")
    private String eventComment;

    @Column(name = "actor_id", nullable = true, length = 80)
    private String actorId;

    @Column(name = "event_at", nullable = false)
    private Instant eventAt;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected AssetLifecycleEventJpaEntity() {
            // Required by JPA.
        }

        public AssetLifecycleEventJpaEntity(
                String id,
            String maintainableAssetId,
            AssetLifecycleEventType eventType,
            AssetLifecycleStatus oldStatus,
            AssetLifecycleStatus newStatus,
            String eventReasonId,
            String eventComment,
            String actorId,
            Instant eventAt,
            String correlationId,
            Instant createdAt
        ) {
            this.id = id;
        this.maintainableAssetId = maintainableAssetId;
        this.eventType = eventType;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.eventReasonId = eventReasonId;
        this.eventComment = eventComment;
        this.actorId = actorId;
        this.eventAt = eventAt;
        this.correlationId = correlationId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String maintainableAssetId() {
        return maintainableAssetId;
    }


    public AssetLifecycleEventType eventType() {
        return eventType;
    }


    public AssetLifecycleStatus oldStatus() {
        return oldStatus;
    }


    public AssetLifecycleStatus newStatus() {
        return newStatus;
    }


    public String eventReasonId() {
        return eventReasonId;
    }


    public String eventComment() {
        return eventComment;
    }


    public String actorId() {
        return actorId;
    }


    public Instant eventAt() {
        return eventAt;
    }


    public String correlationId() {
        return correlationId;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
