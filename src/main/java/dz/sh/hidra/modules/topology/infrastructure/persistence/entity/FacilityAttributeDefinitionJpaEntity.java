/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityAttributeDefinitionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for FacilityAttributeDefinition.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import dz.sh.hidra.modules.topology.domain.value.*;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name = "hidra_topology_facility_attribute_definition")
public class FacilityAttributeDefinitionJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "facility_type_version_id", nullable = false, length = 80)
    private String facilityTypeVersionId;
    @Column(name = "attribute_code", nullable = false, length = 120)
    private String attributeCode;
    @Column(name = "label", nullable = true, length = 255)
    private String label;
    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false, length = 80)
    private AttributeDataType dataType;
    @Column(name = "unit_code", nullable = true, length = 40)
    private String unitCode;
    @Column(name = "required", nullable = false)
    private boolean required;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private TopologyStatus status;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    protected FacilityAttributeDefinitionJpaEntity() { }
    public FacilityAttributeDefinitionJpaEntity(
            String id,
            String facilityTypeVersionId,
            String attributeCode,
            String label,
            AttributeDataType dataType,
            String unitCode,
            boolean required,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.facilityTypeVersionId = facilityTypeVersionId;
        this.attributeCode = attributeCode;
        this.label = label;
        this.dataType = dataType;
        this.unitCode = unitCode;
        this.required = required;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String id() { return id; }
    public String facilityTypeVersionId() { return facilityTypeVersionId; }
    public String attributeCode() { return attributeCode; }
    public String label() { return label; }
    public AttributeDataType dataType() { return dataType; }
    public String unitCode() { return unitCode; }
    public boolean required() { return required; }
    public TopologyStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
