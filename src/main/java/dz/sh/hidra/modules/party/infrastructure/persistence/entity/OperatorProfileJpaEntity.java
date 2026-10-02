/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperatorProfileJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for OperatorProfile.
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
     * Database-backed JPA entity for OperatorProfile.
     */
    @Entity
    @Table(name = "hidra_party_operator_profile")
    public class OperatorProfileJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "capability_type", nullable = false, length = 80)
    private OperatorCapabilityType capabilityType;

    @Column(name = "operator_code", nullable = true, length = 120)
    private String operatorCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "qualification_status", nullable = false, length = 80)
    private QualificationStatus qualificationStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PartyCatalogStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected OperatorProfileJpaEntity() {
            // Required by JPA.
        }

        public OperatorProfileJpaEntity(
                String id,
            String partyId,
            OperatorCapabilityType capabilityType,
            String operatorCode,
            QualificationStatus qualificationStatus,
            PartyCatalogStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.capabilityType = capabilityType;
        this.operatorCode = operatorCode;
        this.qualificationStatus = qualificationStatus;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String partyId() {
        return partyId;
    }


    public OperatorCapabilityType capabilityType() {
        return capabilityType;
    }


    public String operatorCode() {
        return operatorCode;
    }


    public QualificationStatus qualificationStatus() {
        return qualificationStatus;
    }


    public PartyCatalogStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
