/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentTypeJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for EquipmentType.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import dz.sh.hidra.modules.topology.domain.value.*;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name = "hidra_topology_equipment_type")
public class EquipmentTypeJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "code", nullable = false, length = 120)
    private String code;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "equipment_kind", nullable = false, length = 80)
    private EquipmentKind equipmentKind;
    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private TopologyStatus status;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    protected EquipmentTypeJpaEntity() { }
    public EquipmentTypeJpaEntity(
            String id,
            String code,
            String name,
            EquipmentKind equipmentKind,
            String description,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.equipmentKind = equipmentKind;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String id() { return id; }
    public String code() { return code; }
    public String name() { return name; }
    public EquipmentKind equipmentKind() { return equipmentKind; }
    public String description() { return description; }
    public TopologyStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
