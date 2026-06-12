/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyNodeJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TopologyNode.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import dz.sh.hidra.modules.topology.domain.value.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
@Entity
@Table(name = "hidra_topology_node")
public class TopologyNodeJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "code", nullable = false, length = 120)
    private String code;
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "node_type", nullable = false, length = 80)
    private NodeType nodeType;
    @Column(name = "facility_id", nullable = true, length = 80)
    private String facilityId;
    @Column(name = "latitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal latitude;
    @Column(name = "longitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal longitude;
    @Column(name = "elevation_meters", nullable = true, precision = 12, scale = 4)
    private BigDecimal elevationMeters;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private TopologyStatus status;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    protected TopologyNodeJpaEntity() { }
    public TopologyNodeJpaEntity(
            String id,
            String code,
            String name,
            NodeType nodeType,
            String facilityId,
            BigDecimal latitude,
            BigDecimal longitude,
            BigDecimal elevationMeters,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.nodeType = nodeType;
        this.facilityId = facilityId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevationMeters = elevationMeters;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String id() { return id; }
    public String code() { return code; }
    public String name() { return name; }
    public NodeType nodeType() { return nodeType; }
    public String facilityId() { return facilityId; }
    public BigDecimal latitude() { return latitude; }
    public BigDecimal longitude() { return longitude; }
    public BigDecimal elevationMeters() { return elevationMeters; }
    public TopologyStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
