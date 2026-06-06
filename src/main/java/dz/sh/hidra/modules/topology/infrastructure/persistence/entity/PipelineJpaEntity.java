/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : JPA representation of a topology pipeline.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA representation of a topology pipeline.
 *
 * <p>Business role:
 * Stores a physical pipeline belonging to a pipeline system.
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
@Table(name = "hidra_topology_pipeline")
public class PipelineJpaEntity {

    /** Stable pipeline identifier. */
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 80)
    private String id;

    /** Parent pipeline system identifier. */
    @Column(name = "pipeline_system_id", nullable = false, length = 80)
    private String pipelineSystemId;

    /** Unique pipeline business code. */
    @Column(name = "code", nullable = false, length = 80)
    private String code;

    /** Pipeline display name. */
    @Column(name = "name", nullable = false, length = 160)
    private String name;

    /** Optional pipeline description. */
    @Column(name = "description", nullable = true, length = 500)
    private String description;

    /** Legacy language-neutral hydrocarbon product type code retained until COR-013. */
    @Column(name = "product_type", nullable = false, length = 60)
    private String productType;

    /** Catalog foreign key to hidra_topology_product_type. */
    @Column(name = "product_type_id", nullable = false, length = 80)
    private String productTypeId;

    /** Nominal diameter in inches. */
    @Column(name = "nominal_diameter_inches", nullable = false, precision = 19, scale = 3)
    private BigDecimal nominalDiameterInches;

    /** Design length in kilometers. */
    @Column(name = "design_length_km", nullable = false, precision = 19, scale = 3)
    private BigDecimal designLengthKm;

    /** Pipeline lifecycle status. */
    @Column(name = "status", nullable = false, length = 40)
    private String status;

    /** Creation instant. */
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /** Last update instant. */
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public PipelineJpaEntity() {
        // Required by JPA.
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getPipelineSystemId() { return pipelineSystemId; }

    public void setPipelineSystemId(String pipelineSystemId) { this.pipelineSystemId = pipelineSystemId; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getProductType() { return productType; }

    public void setProductType(String productType) { this.productType = productType; }

    public String getProductTypeId() { return productTypeId; }

    public void setProductTypeId(String productTypeId) { this.productTypeId = productTypeId; }

    public BigDecimal getNominalDiameterInches() { return nominalDiameterInches; }

    public void setNominalDiameterInches(BigDecimal nominalDiameterInches) { this.nominalDiameterInches = nominalDiameterInches; }

    public BigDecimal getDesignLengthKm() { return designLengthKm; }

    public void setDesignLengthKm(BigDecimal designLengthKm) { this.designLengthKm = designLengthKm; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
