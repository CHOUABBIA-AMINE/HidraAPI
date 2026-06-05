/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSegmentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : JPA representation of a pipeline segment.
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
 * JPA representation of a pipeline segment.
 *
 * <p>Business role:
 * Stores a physical pipe section between two topology nodes.
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
@Table(name = "hidra_topology_pipeline_segment")
public class PipelineSegmentJpaEntity {

    /** Stable pipeline segment identifier. */
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 80)
    private String id;

    /** Parent pipeline identifier. */
    @Column(name = "pipeline_id", nullable = false, length = 80)
    private String pipelineId;

    /** Unique pipeline segment business code. */
    @Column(name = "code", nullable = false, length = 80)
    private String code;

    /** Pipeline segment display name. */
    @Column(name = "name", nullable = false, length = 160)
    private String name;

    /** From-node identifier. */
    @Column(name = "from_node_id", nullable = false, length = 80)
    private String fromNodeId;

    /** To-node identifier. */
    @Column(name = "to_node_id", nullable = false, length = 80)
    private String toNodeId;

    /** Segment length in kilometers. */
    @Column(name = "length_km", nullable = false, precision = 19, scale = 3)
    private BigDecimal lengthKm;

    /** Segment diameter in inches. */
    @Column(name = "diameter_inches", nullable = false, precision = 19, scale = 3)
    private BigDecimal diameterInches;

    /** Pipeline segment lifecycle status. */
    @Column(name = "status", nullable = false, length = 40)
    private String status;

    /** Creation instant. */
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /** Last update instant. */
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public PipelineSegmentJpaEntity() {
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

    public String getFromNodeId() {
        return fromNodeId;
    }

    public void setFromNodeId(String fromNodeId) {
        this.fromNodeId = fromNodeId;
    }

    public String getToNodeId() {
        return toNodeId;
    }

    public void setToNodeId(String toNodeId) {
        this.toNodeId = toNodeId;
    }

    public BigDecimal getLengthKm() {
        return lengthKm;
    }

    public void setLengthKm(BigDecimal lengthKm) {
        this.lengthKm = lengthKm;
    }

    public BigDecimal getDiameterInches() {
        return diameterInches;
    }

    public void setDiameterInches(BigDecimal diameterInches) {
        this.diameterInches = diameterInches;
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
