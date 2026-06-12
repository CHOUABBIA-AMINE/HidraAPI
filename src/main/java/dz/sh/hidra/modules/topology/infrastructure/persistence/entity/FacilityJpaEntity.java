/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for Facility.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import dz.sh.hidra.modules.topology.domain.value.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
@Entity
@Table(name = "hidra_topology_facility")
public class FacilityJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "code", nullable = false, length = 120)
    private String code;
    @Column(name = "name_ar", nullable = true, length = 255)
    private String nameAr;
    @Column(name = "name_fr", nullable = true, length = 255)
    private String nameFr;
    @Column(name = "name_en", nullable = true, length = 255)
    private String nameEn;
    @Column(name = "facility_type_id", nullable = false, length = 80)
    private String facilityTypeId;
    @Enumerated(EnumType.STRING)
    @Column(name = "facility_kind", nullable = false, length = 80)
    private FacilityKind facilityKind;
    @Column(name = "owner_party_id", nullable = true, length = 80)
    private String ownerPartyId;
    @Column(name = "owner_party_code_snapshot", nullable = true, length = 120)
    private String ownerPartyCodeSnapshot;
    @Column(name = "owner_party_name_snapshot", nullable = true, length = 255)
    private String ownerPartyNameSnapshot;
    @Column(name = "latitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal latitude;
    @Column(name = "longitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal longitude;
    @Column(name = "elevation_meters", nullable = true, precision = 12, scale = 4)
    private BigDecimal elevationMeters;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private FacilityStatus status;
    @Column(name = "commissioned_at", nullable = true)
    private Instant commissionedAt;
    @Column(name = "retired_at", nullable = true)
    private Instant retiredAt;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    protected FacilityJpaEntity() { }
    public FacilityJpaEntity(
            String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String facilityTypeId,
            FacilityKind facilityKind,
            String ownerPartyId,
            String ownerPartyCodeSnapshot,
            String ownerPartyNameSnapshot,
            BigDecimal latitude,
            BigDecimal longitude,
            BigDecimal elevationMeters,
            FacilityStatus status,
            Instant commissionedAt,
            Instant retiredAt,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.facilityTypeId = facilityTypeId;
        this.facilityKind = facilityKind;
        this.ownerPartyId = ownerPartyId;
        this.ownerPartyCodeSnapshot = ownerPartyCodeSnapshot;
        this.ownerPartyNameSnapshot = ownerPartyNameSnapshot;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevationMeters = elevationMeters;
        this.status = status;
        this.commissionedAt = commissionedAt;
        this.retiredAt = retiredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String id() { return id; }
    public String code() { return code; }
    public String nameAr() { return nameAr; }
    public String nameFr() { return nameFr; }
    public String nameEn() { return nameEn; }
    public String facilityTypeId() { return facilityTypeId; }
    public FacilityKind facilityKind() { return facilityKind; }
    public String ownerPartyId() { return ownerPartyId; }
    public String ownerPartyCodeSnapshot() { return ownerPartyCodeSnapshot; }
    public String ownerPartyNameSnapshot() { return ownerPartyNameSnapshot; }
    public BigDecimal latitude() { return latitude; }
    public BigDecimal longitude() { return longitude; }
    public BigDecimal elevationMeters() { return elevationMeters; }
    public FacilityStatus status() { return status; }
    public Instant commissionedAt() { return commissionedAt; }
    public Instant retiredAt() { return retiredAt; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
