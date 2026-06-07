/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointBindingJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : JPA entity for telemetry point topology bindings.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * JPA entity for telemetry point topology bindings.
 *
 * <p>Architecture role:
 * Persistence-only representation of telemetry data. It must not be exposed to domain, application,
 * or REST layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_telemetry_point_binding")
public class TelemetryPointBindingJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "point_id", nullable = false)
    private String pointId;

    @Column(name = "topology_asset_type_code", nullable = false)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = false)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = false)
    private String topologyAssetCode;

    @Column(name = "topology_asset_name_snapshot", nullable = true)
    private String topologyAssetNameSnapshot;

    @Column(name = "binding_role_id", nullable = false)
    private String bindingRoleId;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected TelemetryPointBindingJpaEntity() {
        // Required by JPA.
    }

    public TelemetryPointBindingJpaEntity(
            String id,            String pointId,            String topologyAssetTypeCode,            String topologyAssetId,            String topologyAssetCode,            String topologyAssetNameSnapshot,            String bindingRoleId,            Boolean active,            Instant validFrom,            Instant validTo,            Instant createdAt,            Instant updatedAt) {
        this.id = id;
        this.pointId = pointId;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.topologyAssetNameSnapshot = topologyAssetNameSnapshot;
        this.bindingRoleId = bindingRoleId;
        this.active = active;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getTopologyAssetTypeCode() {
        return topologyAssetTypeCode;
    }

    public void setTopologyAssetTypeCode(String topologyAssetTypeCode) {
        this.topologyAssetTypeCode = topologyAssetTypeCode;
    }

    public String getTopologyAssetId() {
        return topologyAssetId;
    }

    public void setTopologyAssetId(String topologyAssetId) {
        this.topologyAssetId = topologyAssetId;
    }

    public String getTopologyAssetCode() {
        return topologyAssetCode;
    }

    public void setTopologyAssetCode(String topologyAssetCode) {
        this.topologyAssetCode = topologyAssetCode;
    }

    public String getTopologyAssetNameSnapshot() {
        return topologyAssetNameSnapshot;
    }

    public void setTopologyAssetNameSnapshot(String topologyAssetNameSnapshot) {
        this.topologyAssetNameSnapshot = topologyAssetNameSnapshot;
    }

    public String getBindingRoleId() {
        return bindingRoleId;
    }

    public void setBindingRoleId(String bindingRoleId) {
        this.bindingRoleId = bindingRoleId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
