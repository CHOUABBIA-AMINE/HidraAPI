/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSegmentJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PipelineSegment.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import dz.sh.hidra.modules.topology.domain.value.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
@Entity
@Table(name = "hidra_topology_pipeline_segment")
public class PipelineSegmentJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "pipeline_id", nullable = false, length = 80)
    private String pipelineId;
    @Column(name = "code", nullable = false, length = 120)
    private String code;
    @Enumerated(EnumType.STRING)
    @Column(name = "segment_type", nullable = false, length = 80)
    private PipelineSegmentType segmentType;
    @Column(name = "from_node_id", nullable = false, length = 80)
    private String fromNodeId;
    @Column(name = "to_node_id", nullable = false, length = 80)
    private String toNodeId;
    @Column(name = "start_kilometer_point", nullable = true, precision = 12, scale = 4)
    private BigDecimal startKilometerPoint;
    @Column(name = "end_kilometer_point", nullable = true, precision = 12, scale = 4)
    private BigDecimal endKilometerPoint;
    @Column(name = "length_km", nullable = true, precision = 12, scale = 4)
    private BigDecimal lengthKm;
    @Enumerated(EnumType.STRING)
    @Column(name = "flow_direction", nullable = false, length = 80)
    private FlowDirection flowDirection;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private TopologyStatus status;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    protected PipelineSegmentJpaEntity() { }
    public PipelineSegmentJpaEntity(
            String id,
            String pipelineId,
            String code,
            PipelineSegmentType segmentType,
            String fromNodeId,
            String toNodeId,
            BigDecimal startKilometerPoint,
            BigDecimal endKilometerPoint,
            BigDecimal lengthKm,
            FlowDirection flowDirection,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.pipelineId = pipelineId;
        this.code = code;
        this.segmentType = segmentType;
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.startKilometerPoint = startKilometerPoint;
        this.endKilometerPoint = endKilometerPoint;
        this.lengthKm = lengthKm;
        this.flowDirection = flowDirection;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String id() { return id; }
    public String pipelineId() { return pipelineId; }
    public String code() { return code; }
    public PipelineSegmentType segmentType() { return segmentType; }
    public String fromNodeId() { return fromNodeId; }
    public String toNodeId() { return toNodeId; }
    public BigDecimal startKilometerPoint() { return startKilometerPoint; }
    public BigDecimal endKilometerPoint() { return endKilometerPoint; }
    public BigDecimal lengthKm() { return lengthKm; }
    public FlowDirection flowDirection() { return flowDirection; }
    public TopologyStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
