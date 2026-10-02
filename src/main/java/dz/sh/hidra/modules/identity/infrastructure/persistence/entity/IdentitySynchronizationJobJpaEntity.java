/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentitySynchronizationJobJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IdentitySynchronizationJob.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.identity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IdentitySynchronizationJob.
     */
    @Entity
    @Table(name = "hidra_identity_synchronization_job")
    public class IdentitySynchronizationJobJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "identity_provider_id", nullable = false, length = 80)
    private String identityProviderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "sync_type", nullable = false, length = 80)
    private SyncType syncType;

    @Enumerated(EnumType.STRING)
    @Column(name = "trigger_type", nullable = false, length = 80)
    private SyncTriggerType triggerType;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private SyncJobStatus status;

    @Column(name = "users_created", nullable = false)
    private int usersCreated;

    @Column(name = "users_updated", nullable = false)
    private int usersUpdated;

    @Column(name = "users_disabled", nullable = false)
    private int usersDisabled;

    @Column(name = "groups_created", nullable = false)
    private int groupsCreated;

    @Column(name = "groups_updated", nullable = false)
    private int groupsUpdated;

    @Column(name = "memberships_updated", nullable = false)
    private int membershipsUpdated;

    @Column(name = "error_message", nullable = true, columnDefinition = "text")
    private String errorMessage;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

        protected IdentitySynchronizationJobJpaEntity() {
            // Required by JPA.
        }

        public IdentitySynchronizationJobJpaEntity(
                String id,
            String identityProviderId,
            SyncType syncType,
            SyncTriggerType triggerType,
            Instant startedAt,
            Instant completedAt,
            SyncJobStatus status,
            int usersCreated,
            int usersUpdated,
            int usersDisabled,
            int groupsCreated,
            int groupsUpdated,
            int membershipsUpdated,
            String errorMessage,
            String correlationId
        ) {
            this.id = id;
        this.identityProviderId = identityProviderId;
        this.syncType = syncType;
        this.triggerType = triggerType;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.status = status;
        this.usersCreated = usersCreated;
        this.usersUpdated = usersUpdated;
        this.usersDisabled = usersDisabled;
        this.groupsCreated = groupsCreated;
        this.groupsUpdated = groupsUpdated;
        this.membershipsUpdated = membershipsUpdated;
        this.errorMessage = errorMessage;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String identityProviderId() {
        return identityProviderId;
    }


    public SyncType syncType() {
        return syncType;
    }


    public SyncTriggerType triggerType() {
        return triggerType;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public SyncJobStatus status() {
        return status;
    }


    public int usersCreated() {
        return usersCreated;
    }


    public int usersUpdated() {
        return usersUpdated;
    }


    public int usersDisabled() {
        return usersDisabled;
    }


    public int groupsCreated() {
        return groupsCreated;
    }


    public int groupsUpdated() {
        return groupsUpdated;
    }


    public int membershipsUpdated() {
        return membershipsUpdated;
    }


    public String errorMessage() {
        return errorMessage;
    }


    public String correlationId() {
        return correlationId;
    }

    }
