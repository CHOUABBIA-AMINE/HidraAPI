/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessmentScopeJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrityAssessmentScope.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for IntegrityAssessmentScope.
     */
    @Entity
    @Table(name = "hidra_integrity_assessment_scope")
    public class IntegrityAssessmentScopeJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "assessment_id", nullable = false, length = 80)
    private String assessmentId;

    @Column(name = "topology_asset_type_code", nullable = false, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code_snapshot", nullable = true, length = 160)
    private String topologyAssetCodeSnapshot;

    @Column(name = "topology_asset_name_snapshot", nullable = true, length = 500)
    private String topologyAssetNameSnapshot;

    @Column(name = "scope_role_id", nullable = true, length = 80)
    private String scopeRoleId;

    @Column(name = "topology_snapshot_id", nullable = true, length = 80)
    private String topologySnapshotId;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected IntegrityAssessmentScopeJpaEntity() {
            // Required by JPA.
        }

        public IntegrityAssessmentScopeJpaEntity(
                String id,
            String assessmentId,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCodeSnapshot,
            String topologyAssetNameSnapshot,
            String scopeRoleId,
            String topologySnapshotId,
            Instant validFrom,
            Instant validTo,
            Instant createdAt
        ) {
            this.id = id;
        this.assessmentId = assessmentId;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCodeSnapshot = topologyAssetCodeSnapshot;
        this.topologyAssetNameSnapshot = topologyAssetNameSnapshot;
        this.scopeRoleId = scopeRoleId;
        this.topologySnapshotId = topologySnapshotId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String assessmentId() {
        return assessmentId;
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


    public String topologyAssetNameSnapshot() {
        return topologyAssetNameSnapshot;
    }


    public String scopeRoleId() {
        return scopeRoleId;
    }


    public String topologySnapshotId() {
        return topologySnapshotId;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
