/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentAttributeValueJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for EquipmentAttributeValue.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "hidra_topology_equipment_attribute_value")
public class EquipmentAttributeValueJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "equipment_id", nullable = false, length = 80)
    private String equipmentId;
    @Column(name = "attribute_definition_id", nullable = false, length = 80)
    private String attributeDefinitionId;
    @Column(name = "value_text", nullable = true, columnDefinition = "text")
    private String valueText;
    @Column(name = "value_number", nullable = true, precision = 14, scale = 4)
    private BigDecimal valueNumber;
    @Column(name = "value_json", nullable = true, columnDefinition = "jsonb")
    private String valueJson;
    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;
    @Column(name = "valid_to", nullable = true)
    private Instant validTo;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    protected EquipmentAttributeValueJpaEntity() { }
    public EquipmentAttributeValueJpaEntity(
            String id,
            String equipmentId,
            String attributeDefinitionId,
            String valueText,
            BigDecimal valueNumber,
            String valueJson,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.attributeDefinitionId = attributeDefinitionId;
        this.valueText = valueText;
        this.valueNumber = valueNumber;
        this.valueJson = valueJson;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String id() { return id; }
    public String equipmentId() { return equipmentId; }
    public String attributeDefinitionId() { return attributeDefinitionId; }
    public String valueText() { return valueText; }
    public BigDecimal valueNumber() { return valueNumber; }
    public String valueJson() { return valueJson; }
    public Instant validFrom() { return validFrom; }
    public Instant validTo() { return validTo; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
