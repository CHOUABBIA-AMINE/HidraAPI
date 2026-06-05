/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : JPA representation of a topology pipeline system.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA representation of a topology pipeline system.
 *
 * <p>Business role:
 * Stores a physical hydrocarbon transportation system that groups pipelines.
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
@Table(name = "hidra_topology_pipeline_system")
public class PipelineSystemJpaEntity {

    /** Stable pipeline system identifier. */
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 80)
    private String id;

    /** Unique pipeline system business code. */
    @Column(name = "code", nullable = false, length = 80)
    private String code;

    /** Pipeline system display name. */
    @Column(name = "name", nullable = false, length = 160)
    private String name;

    /** Optional pipeline system description. */
    @Column(name = "description", nullable = true, length = 500)
    private String description;

    /** Hydrocarbon product type. */
    @Column(name = "product_type", nullable = false, length = 60)
    private String productType;

    /** Pipeline system lifecycle status. */
    @Column(name = "status", nullable = false, length = 40)
    private String status;

    /** Optional neutral operational owner reference type. */
    @Column(name = "operational_owner_reference_type", nullable = true, length = 80)
    private String operationalOwnerReferenceType;

    /** Optional neutral operational owner reference identifier. */
    @Column(name = "operational_owner_reference_id", nullable = true, length = 120)
    private String operationalOwnerReferenceId;

    /** Optional neutral operational owner reference code. */
    @Column(name = "operational_owner_reference_code", nullable = true, length = 120)
    private String operationalOwnerReferenceCode;

    /** Optional neutral operational owner reference name. */
    @Column(name = "operational_owner_reference_name", nullable = true, length = 160)
    private String operationalOwnerReferenceName;

    /** Creation instant. */
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /** Last update instant. */
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public PipelineSystemJpaEntity() {
        // Required by JPA.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperationalOwnerReferenceType() {
        return operationalOwnerReferenceType;
    }

    public void setOperationalOwnerReferenceType(String operationalOwnerReferenceType) {
        this.operationalOwnerReferenceType = operationalOwnerReferenceType;
    }

    public String getOperationalOwnerReferenceId() {
        return operationalOwnerReferenceId;
    }

    public void setOperationalOwnerReferenceId(String operationalOwnerReferenceId) {
        this.operationalOwnerReferenceId = operationalOwnerReferenceId;
    }

    public String getOperationalOwnerReferenceCode() {
        return operationalOwnerReferenceCode;
    }

    public void setOperationalOwnerReferenceCode(String operationalOwnerReferenceCode) {
        this.operationalOwnerReferenceCode = operationalOwnerReferenceCode;
    }

    public String getOperationalOwnerReferenceName() {
        return operationalOwnerReferenceName;
    }

    public void setOperationalOwnerReferenceName(String operationalOwnerReferenceName) {
        this.operationalOwnerReferenceName = operationalOwnerReferenceName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
