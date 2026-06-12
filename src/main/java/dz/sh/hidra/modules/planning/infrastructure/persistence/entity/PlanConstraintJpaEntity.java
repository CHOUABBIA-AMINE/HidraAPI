/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanConstraintJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PlanConstraint.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.entity;

import dz.sh.hidra.modules.planning.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for PlanConstraint.
     */
    @Entity
    @Table(name = "hidra_planning_plan_constraint")
    public class PlanConstraintJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "revision_id", nullable = false, length = 80)
    private String revisionId;

    @Column(name = "scenario_id", nullable = true, length = 80)
    private String scenarioId;

    @Column(name = "constraint_type_id", nullable = false, length = 80)
    private String constraintTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 40)
    private ConstraintSeverity severity;

    @Column(name = "topology_asset_type", nullable = true, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = true, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = true, length = 160)
    private String topologyAssetCode;

    @Column(name = "constraint_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal constraintValue;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "source_module", nullable = true, length = 160)
    private String sourceModule;

    @Column(name = "source_reference_id", nullable = true, length = 80)
    private String sourceReferenceId;

    @Column(name = "description", nullable = true, length = 500)
    private String description;

    @Column(name = "blocking", nullable = false)
    private boolean blocking;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ConstraintStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PlanConstraintJpaEntity() {
            // Required by JPA.
        }

        public PlanConstraintJpaEntity(
                String id,
            String revisionId,
            String scenarioId,
            String constraintTypeId,
            ConstraintSeverity severity,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            BigDecimal constraintValue,
            String unitId,
            Instant validFrom,
            Instant validTo,
            String sourceModule,
            String sourceReferenceId,
            String description,
            boolean blocking,
            ConstraintStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.revisionId = revisionId;
        this.scenarioId = scenarioId;
        this.constraintTypeId = constraintTypeId;
        this.severity = severity;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.constraintValue = constraintValue;
        this.unitId = unitId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.sourceModule = sourceModule;
        this.sourceReferenceId = sourceReferenceId;
        this.description = description;
        this.blocking = blocking;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String revisionId() {
        return revisionId;
    }


    public String scenarioId() {
        return scenarioId;
    }


    public String constraintTypeId() {
        return constraintTypeId;
    }


    public ConstraintSeverity severity() {
        return severity;
    }


    public String topologyAssetType() {
        return topologyAssetType;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCode() {
        return topologyAssetCode;
    }


    public BigDecimal constraintValue() {
        return constraintValue;
    }


    public String unitId() {
        return unitId;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public String sourceModule() {
        return sourceModule;
    }


    public String sourceReferenceId() {
        return sourceReferenceId;
    }


    public String description() {
        return description;
    }


    public boolean blocking() {
        return blocking;
    }


    public ConstraintStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
