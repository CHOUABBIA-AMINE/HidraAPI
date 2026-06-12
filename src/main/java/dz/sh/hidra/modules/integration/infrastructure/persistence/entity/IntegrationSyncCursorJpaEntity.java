/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationSyncCursorJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationSyncCursor.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integration.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IntegrationSyncCursor.
     */
    @Entity
    @Table(name = "hidra_integration_sync_cursor")
    public class IntegrationSyncCursorJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "job_definition_id", nullable = false, length = 80)
    private String jobDefinitionId;

    @Column(name = "external_system_id", nullable = false, length = 80)
    private String externalSystemId;

    @Column(name = "cursor_name", nullable = false, length = 120)
    private String cursorName;

    @Column(name = "cursor_value", nullable = true, length = 1000)
    private String cursorValue;

    @Column(name = "cursor_payload", nullable = true, columnDefinition = "jsonb")
    private String cursorPayload;

    @Column(name = "last_successful_run_id", nullable = true, length = 80)
    private String lastSuccessfulRunId;

    @Column(name = "last_successful_at", nullable = true)
    private Instant lastSuccessfulAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private SyncCursorStatus status;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IntegrationSyncCursorJpaEntity() {
            // Required by JPA.
        }

        public IntegrationSyncCursorJpaEntity(
                String id,
            String jobDefinitionId,
            String externalSystemId,
            String cursorName,
            String cursorValue,
            String cursorPayload,
            String lastSuccessfulRunId,
            Instant lastSuccessfulAt,
            SyncCursorStatus status,
            Instant updatedAt
        ) {
            this.id = id;
        this.jobDefinitionId = jobDefinitionId;
        this.externalSystemId = externalSystemId;
        this.cursorName = cursorName;
        this.cursorValue = cursorValue;
        this.cursorPayload = cursorPayload;
        this.lastSuccessfulRunId = lastSuccessfulRunId;
        this.lastSuccessfulAt = lastSuccessfulAt;
        this.status = status;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String jobDefinitionId() {
        return jobDefinitionId;
    }


    public String externalSystemId() {
        return externalSystemId;
    }


    public String cursorName() {
        return cursorName;
    }


    public String cursorValue() {
        return cursorValue;
    }


    public String cursorPayload() {
        return cursorPayload;
    }


    public String lastSuccessfulRunId() {
        return lastSuccessfulRunId;
    }


    public Instant lastSuccessfulAt() {
        return lastSuccessfulAt;
    }


    public SyncCursorStatus status() {
        return status;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
