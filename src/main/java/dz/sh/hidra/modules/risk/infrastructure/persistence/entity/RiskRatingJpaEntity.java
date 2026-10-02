/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskRatingJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskRating.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for RiskRating.
     */
    @Entity
    @Table(name = "hidra_risk_rating")
    public class RiskRatingJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "severity_order", nullable = false)
    private int severityOrder;

    @Column(name = "requires_treatment", nullable = false)
    private boolean requiresTreatment;

    @Column(name = "requires_approval", nullable = false)
    private boolean requiresApproval;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected RiskRatingJpaEntity() {
            // Required by JPA.
        }

        public RiskRatingJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String description,
            int severityOrder,
            boolean requiresTreatment,
            boolean requiresApproval,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.description = description;
        this.severityOrder = severityOrder;
        this.requiresTreatment = requiresTreatment;
        this.requiresApproval = requiresApproval;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String nameAr() {
        return nameAr;
    }


    public String nameFr() {
        return nameFr;
    }


    public String nameEn() {
        return nameEn;
    }


    public String description() {
        return description;
    }


    public int severityOrder() {
        return severityOrder;
    }


    public boolean requiresTreatment() {
        return requiresTreatment;
    }


    public boolean requiresApproval() {
        return requiresApproval;
    }


    public boolean active() {
        return active;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
