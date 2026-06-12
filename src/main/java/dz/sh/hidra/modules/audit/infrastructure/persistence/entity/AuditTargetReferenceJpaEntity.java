/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditTargetReferenceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuditTargetReference.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AuditTargetReference.
     */
    @Entity
    @Table(name = "hidra_audit_target_reference")
    public class AuditTargetReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "audit_event_id", nullable = false, length = 80)
    private String auditEventId;

    @Column(name = "target_module", nullable = false, length = 80)
    private String targetModule;

    @Column(name = "target_type", nullable = false, length = 120)
    private String targetType;

    @Column(name = "target_id", nullable = false, length = 120)
    private String targetId;

    @Column(name = "target_code_snapshot", nullable = true, length = 120)
    private String targetCodeSnapshot;

    @Column(name = "target_label_snapshot", nullable = true, length = 240)
    private String targetLabelSnapshot;

    @Column(name = "target_version", nullable = true, length = 80)
    private String targetVersion;

    @Column(name = "topology_asset_type_code", nullable = true, length = 120)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = true, length = 120)
    private String topologyAssetId;

    @Column(name = "topology_asset_code_snapshot", nullable = true, length = 120)
    private String topologyAssetCodeSnapshot;

    @Column(name = "captured_at", nullable = false)
    private Instant capturedAt;

        protected AuditTargetReferenceJpaEntity() {
            // Required by JPA.
        }

        public AuditTargetReferenceJpaEntity(
                String id,
            String auditEventId,
            String targetModule,
            String targetType,
            String targetId,
            String targetCodeSnapshot,
            String targetLabelSnapshot,
            String targetVersion,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCodeSnapshot,
            Instant capturedAt
        ) {
            this.id = id;
        this.auditEventId = auditEventId;
        this.targetModule = targetModule;
        this.targetType = targetType;
        this.targetId = targetId;
        this.targetCodeSnapshot = targetCodeSnapshot;
        this.targetLabelSnapshot = targetLabelSnapshot;
        this.targetVersion = targetVersion;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCodeSnapshot = topologyAssetCodeSnapshot;
        this.capturedAt = capturedAt;
        }


    public String id() {
        return id;
    }


    public String auditEventId() {
        return auditEventId;
    }


    public String targetModule() {
        return targetModule;
    }


    public String targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public String targetCodeSnapshot() {
        return targetCodeSnapshot;
    }


    public String targetLabelSnapshot() {
        return targetLabelSnapshot;
    }


    public String targetVersion() {
        return targetVersion;
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


    public Instant capturedAt() {
        return capturedAt;
    }

    }
