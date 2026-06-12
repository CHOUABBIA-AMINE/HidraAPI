/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MeasurementLocationJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MeasurementLocation.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import dz.sh.hidra.modules.topology.domain.value.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
@Entity
@Table(name = "hidra_topology_measurement_location")
public class MeasurementLocationJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "code", nullable = false, length = 120)
    private String code;
    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_location_type", nullable = false, length = 80)
    private MeasurementLocationType measurementLocationType;
    @Column(name = "pipeline_id", nullable = true, length = 80)
    private String pipelineId;
    @Column(name = "pipeline_segment_id", nullable = true, length = 80)
    private String pipelineSegmentId;
    @Column(name = "facility_id", nullable = true, length = 80)
    private String facilityId;
    @Column(name = "node_id", nullable = true, length = 80)
    private String nodeId;
    @Column(name = "equipment_id", nullable = true, length = 80)
    private String equipmentId;
    @Column(name = "kilometer_point", nullable = true, precision = 12, scale = 4)
    private BigDecimal kilometerPoint;
    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private TopologyStatus status;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    protected MeasurementLocationJpaEntity() { }
    public MeasurementLocationJpaEntity(
            String id,
            String code,
            MeasurementLocationType measurementLocationType,
            String pipelineId,
            String pipelineSegmentId,
            String facilityId,
            String nodeId,
            String equipmentId,
            BigDecimal kilometerPoint,
            String description,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.code = code;
        this.measurementLocationType = measurementLocationType;
        this.pipelineId = pipelineId;
        this.pipelineSegmentId = pipelineSegmentId;
        this.facilityId = facilityId;
        this.nodeId = nodeId;
        this.equipmentId = equipmentId;
        this.kilometerPoint = kilometerPoint;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String id() { return id; }
    public String code() { return code; }
    public MeasurementLocationType measurementLocationType() { return measurementLocationType; }
    public String pipelineId() { return pipelineId; }
    public String pipelineSegmentId() { return pipelineSegmentId; }
    public String facilityId() { return facilityId; }
    public String nodeId() { return nodeId; }
    public String equipmentId() { return equipmentId; }
    public BigDecimal kilometerPoint() { return kilometerPoint; }
    public String description() { return description; }
    public TopologyStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
