/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for OrganizationUnit.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import dz.sh.hidra.modules.organization.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for OrganizationUnit.
     */
    @Entity
    @Table(name = "hidra_org_unit")
    public class OrganizationUnitJpaEntity {

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

    @Column(name = "unit_type_id", nullable = false, length = 80)
    private String unitTypeId;

    @Column(name = "parent_unit_id", nullable = true, length = 80)
    private String parentUnitId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private OrganizationUnitStatus status;

    @Column(name = "operational_scope_type", nullable = true, length = 80)
    private String operationalScopeType;

    @Column(name = "operational_scope_id", nullable = true, length = 120)
    private String operationalScopeId;

    @Column(name = "operational_scope_code", nullable = true, length = 120)
    private String operationalScopeCode;

    @Column(name = "operational_scope_name", nullable = true, length = 255)
    private String operationalScopeName;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected OrganizationUnitJpaEntity() {
            // Required by JPA.
        }

        public OrganizationUnitJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String unitTypeId,
            String parentUnitId,
            OrganizationUnitStatus status,
            String operationalScopeType,
            String operationalScopeId,
            String operationalScopeCode,
            String operationalScopeName,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.unitTypeId = unitTypeId;
        this.parentUnitId = parentUnitId;
        this.status = status;
        this.operationalScopeType = operationalScopeType;
        this.operationalScopeId = operationalScopeId;
        this.operationalScopeCode = operationalScopeCode;
        this.operationalScopeName = operationalScopeName;
        this.validFrom = validFrom;
        this.validTo = validTo;
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


    public String unitTypeId() {
        return unitTypeId;
    }


    public String parentUnitId() {
        return parentUnitId;
    }


    public OrganizationUnitStatus status() {
        return status;
    }


    public String operationalScopeType() {
        return operationalScopeType;
    }


    public String operationalScopeId() {
        return operationalScopeId;
    }


    public String operationalScopeCode() {
        return operationalScopeCode;
    }


    public String operationalScopeName() {
        return operationalScopeName;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
