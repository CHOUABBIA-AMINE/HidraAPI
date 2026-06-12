/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManufacturerProfileJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ManufacturerProfile.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.entity;

import dz.sh.hidra.modules.party.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ManufacturerProfile.
     */
    @Entity
    @Table(name = "hidra_party_manufacturer_profile")
    public class ManufacturerProfileJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "capability_type", nullable = false, length = 80)
    private ManufacturerCapabilityType capabilityType;

    @Column(name = "brand_name", nullable = true, length = 160)
    private String brandName;

    @Column(name = "manufacturer_code", nullable = true, length = 120)
    private String manufacturerCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "qualification_status", nullable = false, length = 80)
    private QualificationStatus qualificationStatus;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ManufacturerProfileJpaEntity() {
            // Required by JPA.
        }

        public ManufacturerProfileJpaEntity(
                String id,
            String partyId,
            ManufacturerCapabilityType capabilityType,
            String brandName,
            String manufacturerCode,
            QualificationStatus qualificationStatus,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.capabilityType = capabilityType;
        this.brandName = brandName;
        this.manufacturerCode = manufacturerCode;
        this.qualificationStatus = qualificationStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String partyId() {
        return partyId;
    }


    public ManufacturerCapabilityType capabilityType() {
        return capabilityType;
    }


    public String brandName() {
        return brandName;
    }


    public String manufacturerCode() {
        return manufacturerCode;
    }


    public QualificationStatus qualificationStatus() {
        return qualificationStatus;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
