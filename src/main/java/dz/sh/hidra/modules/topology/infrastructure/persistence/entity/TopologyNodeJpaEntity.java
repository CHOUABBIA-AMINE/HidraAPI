/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyNodeJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : JPA representation of a topology node.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA representation of a topology node.
 *
 * <p>Business role:
 * Stores a physical graph vertex such as a facility inlet/outlet, junction, valve point, injection point, extraction point, purge point, vent, drain, scraper point, receipt point, or delivery point.
 *
 * <p>Architecture role:
 * This class belongs to the topology infrastructure persistence layer and is used only by topology
 * Spring Data repositories, persistence adapters, and persistence mappers.
 *
 * <p>Validation:
 * Domain validation is performed before mapping. Database constraints protect required fields,
 * uniqueness of business codes, topology references, and lifecycle status values.
 *
 * <p>Usage:
 * Use only inside topology persistence infrastructure. Do not expose this class through application,
 * domain, or API layers.
 */
@Entity
@Table(name = "hidra_topology_node")
public class TopologyNodeJpaEntity {

    /** Stable topology node identifier. */
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 80)
    private String id;

    /** Unique topology node business code. */
    @Column(name = "code", nullable = false, length = 80)
    private String code;

    /** Topology node display name. */
    @Column(name = "name", nullable = false, length = 160)
    private String name;

    /** Topology node type. */
    @Column(name = "node_type", nullable = false, length = 80)
    private String nodeType;

    /** Optional owning facility identifier. */
    @Column(name = "facility_id", nullable = true, length = 80)
    private String facilityId;

    /** Optional owning pipeline appurtenance identifier. */
    @Column(name = "pipeline_appurtenance_id", nullable = true, length = 80)
    private String pipelineAppurtenanceId;

    /** Optional latitude in decimal degrees. */
    @Column(name = "latitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal latitude;

    /** Optional longitude in decimal degrees. */
    @Column(name = "longitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal longitude;

    /** Optional elevation in meters. */
    @Column(name = "elevation_meters", nullable = true, precision = 19, scale = 3)
    private BigDecimal elevationMeters;

    /** Topology node lifecycle status. */
    @Column(name = "status", nullable = false, length = 40)
    private String status;

    /** Creation instant. */
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /** Last update instant. */
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public TopologyNodeJpaEntity() {
        // Required by JPA.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getPipelineAppurtenanceId() {
        return pipelineAppurtenanceId;
    }

    public void setPipelineAppurtenanceId(String pipelineAppurtenanceId) {
        this.pipelineAppurtenanceId = pipelineAppurtenanceId;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getElevationMeters() {
        return elevationMeters;
    }

    public void setElevationMeters(BigDecimal elevationMeters) {
        this.elevationMeters = elevationMeters;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
