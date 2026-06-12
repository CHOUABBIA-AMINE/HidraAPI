/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InspectionRunJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for InspectionRun.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integrity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for InspectionRun.
     */
    @Entity
    @Table(name = "hidra_integrity_inspection_run")
    public class InspectionRunJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "campaign_id", nullable = false, length = 80)
    private String campaignId;

    @Column(name = "run_number", nullable = false, length = 80)
    private String runNumber;

    @Column(name = "topology_asset_type_code", nullable = false, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code_snapshot", nullable = true, length = 160)
    private String topologyAssetCodeSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private InspectionRunStatus status;

    @Column(name = "started_at", nullable = true)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "tool_reference", nullable = true, length = 160)
    private String toolReference;

    @Column(name = "operator_actor_id", nullable = true, length = 80)
    private String operatorActorId;

    @Column(name = "summary", nullable = true, columnDefinition = "text")
    private String summary;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected InspectionRunJpaEntity() {
            // Required by JPA.
        }

        public InspectionRunJpaEntity(
                String id,
            String campaignId,
            String runNumber,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCodeSnapshot,
            InspectionRunStatus status,
            Instant startedAt,
            Instant completedAt,
            String toolReference,
            String operatorActorId,
            String summary,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.campaignId = campaignId;
        this.runNumber = runNumber;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCodeSnapshot = topologyAssetCodeSnapshot;
        this.status = status;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.toolReference = toolReference;
        this.operatorActorId = operatorActorId;
        this.summary = summary;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String campaignId() {
        return campaignId;
    }


    public String runNumber() {
        return runNumber;
    }


    public String topologyAssetTypeCode() {
        return topologyAssetTypeCode;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCodeSnapshot() {
        return topologyAssetCodeSnapshot;
    }


    public InspectionRunStatus status() {
        return status;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String toolReference() {
        return toolReference;
    }


    public String operatorActorId() {
        return operatorActorId;
    }


    public String summary() {
        return summary;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
