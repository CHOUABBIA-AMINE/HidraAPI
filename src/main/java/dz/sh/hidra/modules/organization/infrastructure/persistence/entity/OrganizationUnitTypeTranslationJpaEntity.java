/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitTypeTranslationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for OrganizationUnitTypeTranslation.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for OrganizationUnitTypeTranslation.
     */
    @Entity
    @Table(name = "hidra_org_unit_type_translation")
    public class OrganizationUnitTypeTranslationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "unit_type_id", nullable = false, length = 80)
    private String unitTypeId;

    @Column(name = "language_code", nullable = false, length = 10)
    private String languageCode;

    @Column(name = "label", nullable = false, length = 255)
    private String label;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected OrganizationUnitTypeTranslationJpaEntity() {
            // Required by JPA.
        }

        public OrganizationUnitTypeTranslationJpaEntity(
                String id,
            String unitTypeId,
            String languageCode,
            String label,
            String description,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.unitTypeId = unitTypeId;
        this.languageCode = languageCode;
        this.label = label;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String unitTypeId() {
        return unitTypeId;
    }


    public String languageCode() {
        return languageCode;
    }


    public String label() {
        return label;
    }


    public String description() {
        return description;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
