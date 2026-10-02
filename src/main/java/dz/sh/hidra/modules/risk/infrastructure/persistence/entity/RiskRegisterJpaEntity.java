/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskRegisterJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskRegister.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.entity;

import dz.sh.hidra.modules.risk.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for RiskRegister.
     */
    @Entity
    @Table(name = "hidra_risk_register")
    public class RiskRegisterJpaEntity {

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

    @Column(name = "register_type_id", nullable = false, length = 80)
    private String registerTypeId;

    @Column(name = "owner_organization_unit_id", nullable = true, length = 80)
    private String ownerOrganizationUnitId;

    @Column(name = "owner_organization_unit_name_snapshot", nullable = true, length = 500)
    private String ownerOrganizationUnitNameSnapshot;

    @Column(name = "scope_type", nullable = false, length = 160)
    private String scopeType;

    @Column(name = "scope_id", nullable = false, length = 80)
    private String scopeId;

    @Column(name = "scope_code_snapshot", nullable = true, length = 160)
    private String scopeCodeSnapshot;

    @Column(name = "scope_label_snapshot", nullable = true, length = 500)
    private String scopeLabelSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private RiskRegisterStatus status;

    @Column(name = "review_frequency_id", nullable = true, length = 80)
    private String reviewFrequencyId;

    @Column(name = "effective_from", nullable = true)
    private Instant effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private Instant effectiveTo;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_by_display_name_snapshot", nullable = true, length = 255)
    private String createdByDisplayNameSnapshot;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected RiskRegisterJpaEntity() {
            // Required by JPA.
        }

        public RiskRegisterJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String description,
            String registerTypeId,
            String ownerOrganizationUnitId,
            String ownerOrganizationUnitNameSnapshot,
            String scopeType,
            String scopeId,
            String scopeCodeSnapshot,
            String scopeLabelSnapshot,
            RiskRegisterStatus status,
            String reviewFrequencyId,
            Instant effectiveFrom,
            Instant effectiveTo,
            String createdByActorId,
            String createdByDisplayNameSnapshot,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.description = description;
        this.registerTypeId = registerTypeId;
        this.ownerOrganizationUnitId = ownerOrganizationUnitId;
        this.ownerOrganizationUnitNameSnapshot = ownerOrganizationUnitNameSnapshot;
        this.scopeType = scopeType;
        this.scopeId = scopeId;
        this.scopeCodeSnapshot = scopeCodeSnapshot;
        this.scopeLabelSnapshot = scopeLabelSnapshot;
        this.status = status;
        this.reviewFrequencyId = reviewFrequencyId;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.createdByActorId = createdByActorId;
        this.createdByDisplayNameSnapshot = createdByDisplayNameSnapshot;
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


    public String registerTypeId() {
        return registerTypeId;
    }


    public String ownerOrganizationUnitId() {
        return ownerOrganizationUnitId;
    }


    public String ownerOrganizationUnitNameSnapshot() {
        return ownerOrganizationUnitNameSnapshot;
    }


    public String scopeType() {
        return scopeType;
    }


    public String scopeId() {
        return scopeId;
    }


    public String scopeCodeSnapshot() {
        return scopeCodeSnapshot;
    }


    public String scopeLabelSnapshot() {
        return scopeLabelSnapshot;
    }


    public RiskRegisterStatus status() {
        return status;
    }


    public String reviewFrequencyId() {
        return reviewFrequencyId;
    }


    public Instant effectiveFrom() {
        return effectiveFrom;
    }


    public Instant effectiveTo() {
        return effectiveTo;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public String createdByDisplayNameSnapshot() {
        return createdByDisplayNameSnapshot;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
