/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : JPA representation of a physical topology facility.
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
 * JPA representation of a physical topology facility.
 *
 * <p>Business role:
 * Stores a physical facility such as a station, terminal, processing plant, production field interface, gathering center, storage facility, receipt facility, or delivery facility.
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
@Table(name = "hidra_topology_facility")
public class FacilityJpaEntity {

    /** Stable facility identifier. */
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 80)
    private String id;

    /** Unique facility business code. */
    @Column(name = "code", nullable = false, length = 80)
    private String code;

    /** Facility display name. */
    @Column(name = "name", nullable = false, length = 160)
    private String name;

    /** Legacy language-neutral physical facility type code retained until COR-013. */
    @Column(name = "facility_type", nullable = false, length = 80)
    private String facilityType;

    /** Catalog foreign key to hidra_topology_facility_type. */
    @Column(name = "facility_type_id", nullable = false, length = 80)
    private String facilityTypeId;

    /** Legacy language-neutral hydrocarbon product type code retained until COR-013. */
    @Column(name = "product_type", nullable = false, length = 60)
    private String productType;

    /** Catalog foreign key to hidra_topology_product_type. */
    @Column(name = "product_type_id", nullable = false, length = 80)
    private String productTypeId;

    /** Facility lifecycle status. */
    @Column(name = "status", nullable = false, length = 40)
    private String status;

    /** Optional latitude in decimal degrees. */
    @Column(name = "latitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal latitude;

    /** Optional longitude in decimal degrees. */
    @Column(name = "longitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal longitude;

    /** Optional neutral organization unit reference type. */
    @Column(name = "organization_unit_reference_type", nullable = true, length = 80)
    private String organizationUnitReferenceType;

    /** Optional neutral organization unit reference identifier. */
    @Column(name = "organization_unit_reference_id", nullable = true, length = 120)
    private String organizationUnitReferenceId;

    /** Optional neutral organization unit reference code. */
    @Column(name = "organization_unit_reference_code", nullable = true, length = 120)
    private String organizationUnitReferenceCode;

    /** Optional neutral organization unit reference name. */
    @Column(name = "organization_unit_reference_name", nullable = true, length = 160)
    private String organizationUnitReferenceName;

    /** Creation instant. */
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /** Last update instant. */
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public FacilityJpaEntity() {
        // Required by JPA.
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getFacilityType() { return facilityType; }

    public void setFacilityType(String facilityType) { this.facilityType = facilityType; }

    public String getFacilityTypeId() { return facilityTypeId; }

    public void setFacilityTypeId(String facilityTypeId) { this.facilityTypeId = facilityTypeId; }

    public String getProductType() { return productType; }

    public void setProductType(String productType) { this.productType = productType; }

    public String getProductTypeId() { return productTypeId; }

    public void setProductTypeId(String productTypeId) { this.productTypeId = productTypeId; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public BigDecimal getLatitude() { return latitude; }

    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }

    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }

    public String getOrganizationUnitReferenceType() { return organizationUnitReferenceType; }

    public void setOrganizationUnitReferenceType(String organizationUnitReferenceType) { this.organizationUnitReferenceType = organizationUnitReferenceType; }

    public String getOrganizationUnitReferenceId() { return organizationUnitReferenceId; }

    public void setOrganizationUnitReferenceId(String organizationUnitReferenceId) { this.organizationUnitReferenceId = organizationUnitReferenceId; }

    public String getOrganizationUnitReferenceCode() { return organizationUnitReferenceCode; }

    public void setOrganizationUnitReferenceCode(String organizationUnitReferenceCode) { this.organizationUnitReferenceCode = organizationUnitReferenceCode; }

    public String getOrganizationUnitReferenceName() { return organizationUnitReferenceName; }

    public void setOrganizationUnitReferenceName(String organizationUnitReferenceName) { this.organizationUnitReferenceName = organizationUnitReferenceName; }

    public Instant getCreatedAt() { return createdAt; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
