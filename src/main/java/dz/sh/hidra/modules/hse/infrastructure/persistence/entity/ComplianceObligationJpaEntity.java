/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ComplianceObligationJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ComplianceObligation.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.entity;

import dz.sh.hidra.modules.hse.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ComplianceObligation.
     */
    @Entity
    @Table(name = "hidra_hse_compliance_obligation")
    public class ComplianceObligationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "obligation_number", nullable = false, length = 80)
    private String obligationNumber;

    @Column(name = "obligation_type_id", nullable = false, length = 80)
    private String obligationTypeId;

    @Column(name = "regulatory_reference", nullable = true, length = 255)
    private String regulatoryReference;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "jurisdiction_id", nullable = true, length = 80)
    private String jurisdictionId;

    @Column(name = "responsible_organization_unit_id", nullable = true, length = 80)
    private String responsibleOrganizationUnitId;

    @Column(name = "effective_from", nullable = true)
    private Instant effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private Instant effectiveTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ComplianceStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ComplianceObligationJpaEntity() {
            // Required by JPA.
        }

        public ComplianceObligationJpaEntity(
                String id,
            String obligationNumber,
            String obligationTypeId,
            String regulatoryReference,
            String title,
            String description,
            String jurisdictionId,
            String responsibleOrganizationUnitId,
            Instant effectiveFrom,
            Instant effectiveTo,
            ComplianceStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.obligationNumber = obligationNumber;
        this.obligationTypeId = obligationTypeId;
        this.regulatoryReference = regulatoryReference;
        this.title = title;
        this.description = description;
        this.jurisdictionId = jurisdictionId;
        this.responsibleOrganizationUnitId = responsibleOrganizationUnitId;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String obligationNumber() {
        return obligationNumber;
    }


    public String obligationTypeId() {
        return obligationTypeId;
    }


    public String regulatoryReference() {
        return regulatoryReference;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String jurisdictionId() {
        return jurisdictionId;
    }


    public String responsibleOrganizationUnitId() {
        return responsibleOrganizationUnitId;
    }


    public Instant effectiveFrom() {
        return effectiveFrom;
    }


    public Instant effectiveTo() {
        return effectiveTo;
    }


    public ComplianceStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
