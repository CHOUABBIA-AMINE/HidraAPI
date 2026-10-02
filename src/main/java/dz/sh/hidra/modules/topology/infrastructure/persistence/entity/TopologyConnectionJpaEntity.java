/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnectionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TopologyConnection.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import dz.sh.hidra.modules.topology.domain.value.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
@Entity
@Table(name = "hidra_topology_connection")
public class TopologyConnectionJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "code", nullable = false, length = 120)
    private String code;
    @Column(name = "from_node_id", nullable = false, length = 80)
    private String fromNodeId;
    @Column(name = "to_node_id", nullable = false, length = 80)
    private String toNodeId;
    @Enumerated(EnumType.STRING)
    @Column(name = "connection_type", nullable = false, length = 80)
    private ConnectionType connectionType;
    @Enumerated(EnumType.STRING)
    @Column(name = "flow_direction", nullable = false, length = 80)
    private FlowDirection flowDirection;
    @Column(name = "pipeline_segment_id", nullable = true, length = 80)
    private String pipelineSegmentId;
    @Column(name = "nominal_capacity", nullable = true, precision = 14, scale = 4)
    private BigDecimal nominalCapacity;
    @Column(name = "capacity_unit_code", nullable = true, length = 40)
    private String capacityUnitCode;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private TopologyStatus status;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    protected TopologyConnectionJpaEntity() { }
    public TopologyConnectionJpaEntity(
            String id,
            String code,
            String fromNodeId,
            String toNodeId,
            ConnectionType connectionType,
            FlowDirection flowDirection,
            String pipelineSegmentId,
            BigDecimal nominalCapacity,
            String capacityUnitCode,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.code = code;
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.connectionType = connectionType;
        this.flowDirection = flowDirection;
        this.pipelineSegmentId = pipelineSegmentId;
        this.nominalCapacity = nominalCapacity;
        this.capacityUnitCode = capacityUnitCode;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String id() { return id; }
    public String code() { return code; }
    public String fromNodeId() { return fromNodeId; }
    public String toNodeId() { return toNodeId; }
    public ConnectionType connectionType() { return connectionType; }
    public FlowDirection flowDirection() { return flowDirection; }
    public String pipelineSegmentId() { return pipelineSegmentId; }
    public BigDecimal nominalCapacity() { return nominalCapacity; }
    public String capacityUnitCode() { return capacityUnitCode; }
    public TopologyStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
