/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : JPA representation of topology equipment.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA representation of topology equipment.
 *
 * <p>Business role:
 * Stores optional physical equipment/component references attached to topology assets.
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
@Table(name = "hidra_topology_equipment")
public class EquipmentJpaEntity {

    /** Stable equipment identifier. */
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 80)
    private String id;

    /** Unique equipment business code. */
    @Column(name = "code", nullable = false, length = 80)
    private String code;

    /** Equipment display name. */
    @Column(name = "name", nullable = false, length = 160)
    private String name;

    /** Legacy language-neutral equipment type code retained until COR-013. */
    @Column(name = "equipment_type", nullable = false, length = 80)
    private String equipmentType;

    /** Catalog foreign key to hidra_topology_equipment_type. */
    @Column(name = "equipment_type_id", nullable = false, length = 80)
    private String equipmentTypeId;

    /** Parent topology asset type. */
    @Column(name = "parent_asset_type", nullable = false, length = 80)
    private String parentAssetType;

    /** Parent topology asset identifier. */
    @Column(name = "parent_asset_id", nullable = false, length = 120)
    private String parentAssetId;

    /** Equipment lifecycle status. */
    @Column(name = "status", nullable = false, length = 40)
    private String status;

    /** Creation instant. */
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /** Last update instant. */
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public EquipmentJpaEntity() {
        // Required by JPA.
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEquipmentType() { return equipmentType; }

    public void setEquipmentType(String equipmentType) { this.equipmentType = equipmentType; }

    public String getEquipmentTypeId() { return equipmentTypeId; }

    public void setEquipmentTypeId(String equipmentTypeId) { this.equipmentTypeId = equipmentTypeId; }

    public String getParentAssetType() { return parentAssetType; }

    public void setParentAssetType(String parentAssetType) { this.parentAssetType = parentAssetType; }

    public String getParentAssetId() { return parentAssetId; }

    public void setParentAssetId(String parentAssetId) { this.parentAssetId = parentAssetId; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
