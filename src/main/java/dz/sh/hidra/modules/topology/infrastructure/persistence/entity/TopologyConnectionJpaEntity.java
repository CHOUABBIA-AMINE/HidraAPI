/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnectionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : JPA representation of a topology connection.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA representation of a topology connection.
 *
 * <p>Business role:
 * Stores explicit physical graph connectivity between two topology nodes.
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
@Table(name = "hidra_topology_connection")
public class TopologyConnectionJpaEntity {

    /** Stable topology connection identifier. */
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 80)
    private String id;

    /** Unique topology connection business code. */
    @Column(name = "code", nullable = false, length = 80)
    private String code;

    /** Topology connection display name. */
    @Column(name = "name", nullable = false, length = 160)
    private String name;

    /** From-node identifier. */
    @Column(name = "from_node_id", nullable = false, length = 80)
    private String fromNodeId;

    /** To-node identifier. */
    @Column(name = "to_node_id", nullable = false, length = 80)
    private String toNodeId;

    /** Legacy language-neutral topology connection type code retained until COR-013. */
    @Column(name = "connection_type", nullable = false, length = 80)
    private String connectionType;

    /** Catalog foreign key to hidra_topology_connection_type. */
    @Column(name = "connection_type_id", nullable = false, length = 80)
    private String connectionTypeId;

    /** Linked topology asset type. */
    @Column(name = "linked_asset_type", nullable = false, length = 80)
    private String linkedAssetType;

    /** Linked topology asset identifier. */
    @Column(name = "linked_asset_id", nullable = false, length = 120)
    private String linkedAssetId;

    /** Topology connection lifecycle status. */
    @Column(name = "status", nullable = false, length = 40)
    private String status;

    /** Creation instant. */
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /** Last update instant. */
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public TopologyConnectionJpaEntity() {
        // Required by JPA.
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getFromNodeId() { return fromNodeId; }

    public void setFromNodeId(String fromNodeId) { this.fromNodeId = fromNodeId; }

    public String getToNodeId() { return toNodeId; }

    public void setToNodeId(String toNodeId) { this.toNodeId = toNodeId; }

    public String getConnectionType() { return connectionType; }

    public void setConnectionType(String connectionType) { this.connectionType = connectionType; }

    public String getConnectionTypeId() { return connectionTypeId; }

    public void setConnectionTypeId(String connectionTypeId) { this.connectionTypeId = connectionTypeId; }

    public String getLinkedAssetType() { return linkedAssetType; }

    public void setLinkedAssetType(String linkedAssetType) { this.linkedAssetType = linkedAssetType; }

    public String getLinkedAssetId() { return linkedAssetId; }

    public void setLinkedAssetId(String linkedAssetId) { this.linkedAssetId = linkedAssetId; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
