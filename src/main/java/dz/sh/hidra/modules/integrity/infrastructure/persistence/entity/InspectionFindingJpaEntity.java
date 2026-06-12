/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InspectionFindingJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for InspectionFinding.
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
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for InspectionFinding.
     */
    @Entity
    @Table(name = "hidra_integrity_inspection_finding")
    public class InspectionFindingJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "inspection_run_id", nullable = false, length = 80)
    private String inspectionRunId;

    @Column(name = "finding_number", nullable = false, length = 80)
    private String findingNumber;

    @Column(name = "finding_type_id", nullable = false, length = 80)
    private String findingTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 40)
    private FindingSeverity severity;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "kilometer_point", nullable = true, precision = 14, scale = 4)
    private BigDecimal kilometerPoint;

    @Column(name = "topology_asset_type_code", nullable = true, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = true, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code_snapshot", nullable = true, length = 160)
    private String topologyAssetCodeSnapshot;

    @Column(name = "linked_defect_id", nullable = true, length = 80)
    private String linkedDefectId;

    @Column(name = "observed_at", nullable = false)
    private Instant observedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected InspectionFindingJpaEntity() {
            // Required by JPA.
        }

        public InspectionFindingJpaEntity(
                String id,
            String inspectionRunId,
            String findingNumber,
            String findingTypeId,
            FindingSeverity severity,
            String description,
            BigDecimal kilometerPoint,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCodeSnapshot,
            String linkedDefectId,
            Instant observedAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.inspectionRunId = inspectionRunId;
        this.findingNumber = findingNumber;
        this.findingTypeId = findingTypeId;
        this.severity = severity;
        this.description = description;
        this.kilometerPoint = kilometerPoint;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCodeSnapshot = topologyAssetCodeSnapshot;
        this.linkedDefectId = linkedDefectId;
        this.observedAt = observedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String inspectionRunId() {
        return inspectionRunId;
    }


    public String findingNumber() {
        return findingNumber;
    }


    public String findingTypeId() {
        return findingTypeId;
    }


    public FindingSeverity severity() {
        return severity;
    }


    public String description() {
        return description;
    }


    public BigDecimal kilometerPoint() {
        return kilometerPoint;
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


    public String linkedDefectId() {
        return linkedDefectId;
    }


    public Instant observedAt() {
        return observedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
