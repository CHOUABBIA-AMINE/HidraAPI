/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenanceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : JPA representation of a pipeline appurtenance.
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
 * JPA representation of a pipeline appurtenance.
 *
 * <p>Business role:
 * Stores a point asset installed along a pipeline such as a valve, injection point, extraction point, purge point, vent, drain, scraper point, hot tap, bypass, metering point, sampling point, or connection point.
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
@Table(name = "hidra_topology_pipeline_appurtenance")
public class PipelineAppurtenanceJpaEntity {

    /** Stable pipeline appurtenance identifier. */
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 80)
    private String id;

    /** Parent pipeline identifier. */
    @Column(name = "pipeline_id", nullable = false, length = 80)
    private String pipelineId;

    /** Representing topology node identifier. */
    @Column(name = "node_id", nullable = false, length = 80)
    private String nodeId;

    /** Unique pipeline appurtenance business code. */
    @Column(name = "code", nullable = false, length = 80)
    private String code;

    /** Pipeline appurtenance display name. */
    @Column(name = "name", nullable = false, length = 160)
    private String name;

    /** Pipeline appurtenance type. */
    @Column(name = "appurtenance_type", nullable = false, length = 80)
    private String appurtenanceType;

    /** Optional valve type, set only for valve appurtenances. */
    @Column(name = "valve_type", nullable = true, length = 80)
    private String valveType;

    /** KP/PK/chainage value in kilometers. */
    @Column(name = "pipeline_kilometer_point", nullable = false, precision = 19, scale = 3)
    private BigDecimal pipelineKilometerPoint;

    /** Pipeline appurtenance lifecycle status. */
    @Column(name = "status", nullable = false, length = 40)
    private String status;

    /** Optional latitude in decimal degrees. */
    @Column(name = "latitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal latitude;

    /** Optional longitude in decimal degrees. */
    @Column(name = "longitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal longitude;

    /** Optional pipeline appurtenance description. */
    @Column(name = "description", nullable = true, length = 500)
    private String description;

    /** Creation instant. */
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /** Last update instant. */
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public PipelineAppurtenanceJpaEntity() {
        // Required by JPA.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
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

    public String getAppurtenanceType() {
        return appurtenanceType;
    }

    public void setAppurtenanceType(String appurtenanceType) {
        this.appurtenanceType = appurtenanceType;
    }

    public String getValveType() {
        return valveType;
    }

    public void setValveType(String valveType) {
        this.valveType = valveType;
    }

    public BigDecimal getPipelineKilometerPoint() {
        return pipelineKilometerPoint;
    }

    public void setPipelineKilometerPoint(BigDecimal pipelineKilometerPoint) {
        this.pipelineKilometerPoint = pipelineKilometerPoint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
