/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for Equipment.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import dz.sh.hidra.modules.topology.domain.value.*;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name = "hidra_topology_equipment")
public class EquipmentJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "code", nullable = false, length = 120)
    private String code;
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Column(name = "facility_id", nullable = true, length = 80)
    private String facilityId;
    @Column(name = "node_id", nullable = true, length = 80)
    private String nodeId;
    @Column(name = "pipeline_segment_id", nullable = true, length = 80)
    private String pipelineSegmentId;
    @Column(name = "equipment_type_id", nullable = false, length = 80)
    private String equipmentTypeId;
    @Enumerated(EnumType.STRING)
    @Column(name = "equipment_kind", nullable = false, length = 80)
    private EquipmentKind equipmentKind;
    @Column(name = "manufacturer_party_id", nullable = true, length = 80)
    private String manufacturerPartyId;
    @Column(name = "manufacturer_party_code_snapshot", nullable = true, length = 120)
    private String manufacturerPartyCodeSnapshot;
    @Column(name = "manufacturer_party_name_snapshot", nullable = true, length = 255)
    private String manufacturerPartyNameSnapshot;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private EquipmentStatus status;
    @Column(name = "installed_at", nullable = true)
    private Instant installedAt;
    @Column(name = "retired_at", nullable = true)
    private Instant retiredAt;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    protected EquipmentJpaEntity() { }
    public EquipmentJpaEntity(
            String id,
            String code,
            String name,
            String facilityId,
            String nodeId,
            String pipelineSegmentId,
            String equipmentTypeId,
            EquipmentKind equipmentKind,
            String manufacturerPartyId,
            String manufacturerPartyCodeSnapshot,
            String manufacturerPartyNameSnapshot,
            EquipmentStatus status,
            Instant installedAt,
            Instant retiredAt,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.facilityId = facilityId;
        this.nodeId = nodeId;
        this.pipelineSegmentId = pipelineSegmentId;
        this.equipmentTypeId = equipmentTypeId;
        this.equipmentKind = equipmentKind;
        this.manufacturerPartyId = manufacturerPartyId;
        this.manufacturerPartyCodeSnapshot = manufacturerPartyCodeSnapshot;
        this.manufacturerPartyNameSnapshot = manufacturerPartyNameSnapshot;
        this.status = status;
        this.installedAt = installedAt;
        this.retiredAt = retiredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String id() { return id; }
    public String code() { return code; }
    public String name() { return name; }
    public String facilityId() { return facilityId; }
    public String nodeId() { return nodeId; }
    public String pipelineSegmentId() { return pipelineSegmentId; }
    public String equipmentTypeId() { return equipmentTypeId; }
    public EquipmentKind equipmentKind() { return equipmentKind; }
    public String manufacturerPartyId() { return manufacturerPartyId; }
    public String manufacturerPartyCodeSnapshot() { return manufacturerPartyCodeSnapshot; }
    public String manufacturerPartyNameSnapshot() { return manufacturerPartyNameSnapshot; }
    public EquipmentStatus status() { return status; }
    public Instant installedAt() { return installedAt; }
    public Instant retiredAt() { return retiredAt; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
