/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologySnapshotJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TopologySnapshot.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import dz.sh.hidra.modules.topology.domain.value.*;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name = "hidra_topology_snapshot")
public class TopologySnapshotJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "snapshot_code", nullable = false, length = 120)
    private String snapshotCode;
    @Column(name = "version_number", nullable = false)
    private int versionNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private SnapshotStatus status;
    @Column(name = "snapshot_payload", nullable = false, columnDefinition = "jsonb")
    private String snapshotPayload;
    @Column(name = "approved_by_workflow_id", nullable = true, length = 120)
    private String approvedByWorkflowId;
    @Column(name = "captured_at", nullable = false)
    private Instant capturedAt;
    @Column(name = "approved_at", nullable = true)
    private Instant approvedAt;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    protected TopologySnapshotJpaEntity() { }
    public TopologySnapshotJpaEntity(
            String id,
            String snapshotCode,
            int versionNumber,
            SnapshotStatus status,
            String snapshotPayload,
            String approvedByWorkflowId,
            Instant capturedAt,
            Instant approvedAt,
            Instant createdAt
    ) {
        this.id = id;
        this.snapshotCode = snapshotCode;
        this.versionNumber = versionNumber;
        this.status = status;
        this.snapshotPayload = snapshotPayload;
        this.approvedByWorkflowId = approvedByWorkflowId;
        this.capturedAt = capturedAt;
        this.approvedAt = approvedAt;
        this.createdAt = createdAt;
    }
    public String id() { return id; }
    public String snapshotCode() { return snapshotCode; }
    public int versionNumber() { return versionNumber; }
    public SnapshotStatus status() { return status; }
    public String snapshotPayload() { return snapshotPayload; }
    public String approvedByWorkflowId() { return approvedByWorkflowId; }
    public Instant capturedAt() { return capturedAt; }
    public Instant approvedAt() { return approvedAt; }
    public Instant createdAt() { return createdAt; }
}
