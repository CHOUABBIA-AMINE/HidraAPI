/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
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
 */
@Entity
@Table(name = "hidra_topology_pipeline")
public class PipelineJpaEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true, length = 80)
    private String id;

    @Column(name = "pipeline_system_id", nullable = false, length = 80)
    private String pipelineSystemId;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "name_ar", nullable = false, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = false, length = 160)
    private String nameEn;

    @Column(name = "description_ar", nullable = true, length = 500)
    private String descriptionAr;

    @Column(name = "description_fr", nullable = true, length = 500)
    private String descriptionFr;

    @Column(name = "description_en", nullable = true, length = 500)
    private String descriptionEn;

    @Column(name = "product_type", nullable = false, length = 60)
    private String productType;

    @Column(name = "product_type_id", nullable = false, length = 80)
    private String productTypeId;

    @Column(name = "nominal_diameter_inches", nullable = false, precision = 19, scale = 3)
    private BigDecimal nominalDiameterInches;

    @Column(name = "design_length_km", nullable = false, precision = 19, scale = 3)
    private BigDecimal designLengthKm;

    @Column(name = "status", nullable = false, length = 40)
    private String status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

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
    public String getNameAr() { return nameAr; }
    public void setNameAr(String nameAr) { this.nameAr = nameAr; }
    public String getNameFr() { return nameFr; }
    public void setNameFr(String nameFr) { this.nameFr = nameFr; }
    public String getNameEn() { return nameEn; }
    public void setNameEn(String nameEn) { this.nameEn = nameEn; }
    public String getDescriptionAr() { return descriptionAr; }
    public void setDescriptionAr(String descriptionAr) { this.descriptionAr = descriptionAr; }
    public String getDescriptionFr() { return descriptionFr; }
    public void setDescriptionFr(String descriptionFr) { this.descriptionFr = descriptionFr; }
    public String getDescriptionEn() { return descriptionEn; }
    public void setDescriptionEn(String descriptionEn) { this.descriptionEn = descriptionEn; }
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
